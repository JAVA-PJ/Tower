package Game;
import Enum.ImageType;
import Enum.SoundType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements KeyListener{
     // Game Panel
    private Timer gameLoop;
    private boolean dieLock = false;
    private boolean isPressed = false;
    private boolean tutorial = true;
    private final int animationSpeed = 5;

    // Game Over Screen
    private GameOverScreen gameOver;

    // Block
    private Block newBlock;
    private ArrayList<Block> blocks;

    // Health
    private ArrayList<Health> health;
    private int healthIdx = Health.maxHealth - 1;

    // Start Moving
    private final int startMovingAt = 2;

    // Control background and block
    private int yOffset = 0;
    private int curOffset = 0;
    private int numBlocks = 0;
    private double startPosition = App.HEIGHT * (560f / 1000f);

	// Image
    private Image bgImg;
    private Image spaceBar;

    // Score
    protected Score score;

    // Constructor
    public Display() {
        bgImg = new ImageIcon(getClass().getResource(ImageType.BG_GAME.getPath())).getImage();
        spaceBar = new ImageIcon(getClass().getResource(ImageType.SPACEBAR.getPath())).getImage();
        
        score = new Score();
        gameOver = new GameOverScreen(this);
        blocks = new ArrayList<>();
        health  = new ArrayList<>();
        
        for (int i = 1; i <= Health.maxHealth; i++)
            health.add(new Health(i * -50, 0));

        spawnBlock();
        setFocusable(true);
        setPreferredSize(new Dimension(App.WIDTH, App.HEIGHT));
        addKeyListener(this);

        gameLoop = new Timer(16, e -> {
                if (curOffset >= -yOffset) {
                    gameLoop();
                } else {
                    repaint();
                    for (Block block : blocks)
                        block.posY += animationSpeed;
                    curOffset += animationSpeed;
                    if (curOffset >= -yOffset) {
                        newBlock.posY = 50;
                        newBlock.animation = true;
                    }
                }
        });
        gameLoop.start();
    }

    // Game loop to update and repaint the game
    private void gameLoop() {
        update();
        repaint();
    }

    // Spawn a new block
    private void spawnBlock() {
        if (numBlocks >= startMovingAt && !dieLock) {
            int oldFirstBlockY = blocks.get(0).posY;
            int shiftAmount = oldFirstBlockY - blocks.get(1).posY;

            while (blocks.size() > 4)
                blocks.remove(0);
            yOffset -= shiftAmount;
        }

        newBlock = blocks.isEmpty() ? new Block() : new Block(new Random().nextInt(App.WIDTH - newBlock.Width));
        blocks.add(newBlock);
    }
    
    // Update the game
    private void update() {
        if (blocks.size() != 1)
            newBlock.swing(App.WIDTH);

        if (newBlock.falling) {
            newBlock.fall();

            if (blocks.size() == 1 && newBlock.posY + newBlock.Height >= startPosition) {
                numBlocks++;
                dieLock = false;
                yOffset -= 280;
                newBlock.falling = false;
                score.updateSocre();
                spawnBlock();
                newBlock.animation = false;
                Sound.playSound(SoundType.DROP);
                return ;
            }

            if (newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                if (collideWithPreviousBlock()) {
                    numBlocks++;
                    dieLock = false;
                    newBlock.falling = false;
                    score.updateSocre();
                    spawnBlock();
                    newBlock.animation = false;
                    Sound.playSound(SoundType.DROP);
                } else if (blocks.size() > 1 && newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                    health.get(healthIdx).setIsDie(true);
                    Health.updateCurHealth();
                    dieLock = true;
                    healthIdx--;
                    blocks.remove(newBlock);
                    spawnBlock();
                }
            }
        }
    }

    // Get the last block position
    private int getLastBlockPosY() {
        return (blocks.size() == 1 ? App.HEIGHT : blocks.get(blocks.size() - 2).posY);
    }

    // Check if the block is colliding with the previous block
    private boolean collideWithPreviousBlock() {
        if (blocks.size() < 2)
            return (false);

        Block prevBlock = blocks.get(blocks.size() - 2);
        int overlapWidth = Math.min(newBlock.posX + newBlock.Width, prevBlock.posX + prevBlock.Width) -
                           Math.max(newBlock.posX, prevBlock.posX);
        boolean isColliding = (newBlock.posY + newBlock.Height >= prevBlock.posY) &&
                               (newBlock.posX + newBlock.Width > prevBlock.posX) &&
                               (newBlock.posX < prevBlock.posX + prevBlock.Width);
        boolean isTooWide = overlapWidth < (prevBlock.Width / 2);
        return (isColliding && !isTooWide);
    }

    private void mappingBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // ไล่สีฟ้าจากฟ้าอ่อน → ฟ้าเข้ม
        Color skyTop = new Color(135, 206, 250);  // ฟ้าอ่อน
        Color skyBottom = new Color(70, 130, 180); // ฟ้าเข้ม

        // คำนวณระดับสีตามความสูง
        float ratio = Math.min(1.0f, numBlocks * 1.5f / 100.0f);
        int red = (int) (skyTop.getRed() * (1 - ratio) + skyBottom.getRed() * ratio);
        int green = (int) (skyTop.getGreen() * (1 - ratio) + skyBottom.getGreen() * ratio);
        int blue = (int) (skyTop.getBlue() * (1 - ratio) + skyBottom.getBlue() * ratio);
        Color dynamicSky = new Color(red, green, blue);

        // ใช้ Gradient ไล่สีจากด้านบนลงล่าง
        GradientPaint gradient = new GradientPaint(0, 0, dynamicSky, 0, getHeight(), skyBottom);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setNewGame() {
        blocks.clear();
        score = new Score();

        yOffset = 0;
        curOffset = 0;
        numBlocks = 0;
        dieLock = false;
        tutorial = true;
        healthIdx = Health.maxHealth - 1;
        Health.curHealth = Health.maxHealth;

        for (Health h : health)
            h.setIsDie(false);
        spawnBlock();
    }

    // Key Listener
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !newBlock.falling) {
            if (Health.curHealth > 0 && !isPressed)
                newBlock.fall();
            isPressed = true;
        }
    }
    
    // Key Listener
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            isPressed = false;
            tutorial = false;
    }

    // Key Listener
    @Override
    public void keyTyped(KeyEvent e) {}

    // Paint the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if health less than 0 then stop the game
        if (Health.curHealth <= 0) {
            gameOver.gameStop(g, gameLoop);
            return ;
        }

        // Mapping the background color
        mappingBackground(g);
        
        // Draw Background, Block, Health, Score
        if (bgImg != null)
            g.drawImage(bgImg, 0, curOffset, getWidth(), getHeight(), this);
        if (blocks.size() == 1 && tutorial)
            g.drawImage(spaceBar, 225, 250, 250, 100, this);
        for (Block block : blocks)
            block.drawBlock(g);
        for (Health h : health)
            h.updateHealth(g);
        score.drawScore(g);
    }
}