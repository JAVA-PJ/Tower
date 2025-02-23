package Source;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel {
    // Game Panel
    private int numBlocks = 0;
    private Timer gameLoop;
    private boolean dieLock = false;

    // Block
    private Block newBlock;
    private ArrayList<Block> blocks;

    // Health
    private int healthIdx = Health.maxHealth - 1;
    private ArrayList<Health> health;

    // Start Moving
    private final int startMovingAt = 2;

    // Control background and block
    private int offsetY = 0;

	// Image
    private Image bgImg;

    // Constructor
    public Display(Timer gameLoop) {
        this.gameLoop = gameLoop;
        setPreferredSize(new Dimension(App.WIDTH, App.HEIGHT));
        health  = new ArrayList<>();
        for (int i = 1; i <= Health.maxHealth; i++)
            health.add(new Health(i * -50, 0));
        blocks = new ArrayList<>();
        spawnBlock();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !newBlock.falling)
                    newBlock.fall();
            }
        });

        bgImg = new ImageIcon(getClass().getResource("../Assets/background.png")).getImage();
        setFocusable(true);
    }

    // Spawn a new block
    private void spawnBlock() {
        if (numBlocks >= startMovingAt && dieLock == false) {
            int oldFirstBlockY = blocks.get(0).posY;
            int shiftAmount = oldFirstBlockY - blocks.get(1).posY;
            while (blocks.size() > 3)
                blocks.remove(0);
            for (Block block : blocks)
                block.posY += shiftAmount;
            offsetY -= shiftAmount;
        }
        newBlock = blocks.size() == 0 ? new Block() : new Block(new Random().nextInt(App.WIDTH - newBlock.Width));
        blocks.add(newBlock);
    }
    
    // Update the game
    private void update() {
        if (blocks.size() != 1)
            newBlock.swing(App.WIDTH);

        if (newBlock.falling) {
            newBlock.fall();
            if (blocks.size() == 1 && newBlock.posY + newBlock.Height >= 560) {
                numBlocks++;
                dieLock = false;
                offsetY -= 280;
                newBlock.posY += 280;
                newBlock.falling = false;
                spawnBlock();
                return;
            }
            if (newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                if (collideWithPreviousBlock()) {
                    numBlocks++;
                    dieLock = false;
                    newBlock.falling = false;
                    spawnBlock();
                } else if (blocks.size() > 1 && newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                    health.get(healthIdx).setIsDie(true);
                    Health.curHealth--;
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
        if (blocks.size() == 1)
            return (App.HEIGHT);
        return (blocks.get(blocks.size() - 2).posY);
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

    // Game loop to update and repaint the game
    public void gameLoop() {
        update();
        repaint();
    }

    // Stop the game
    public void gameStop() {
        blocks.remove(newBlock);
        gameLoop.stop();
       // System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if health less than 0 then stop the game
        if (Health.curHealth <= 0)
            gameStop();

        // Draw background, Block, Health
        int yOffset = offsetY;
        float ratio = Math.min(1.0f, numBlocks * 2.5f / 100.0f);
        int red = (int)(135 * (1 - ratio));
        int green = (int)(206 * (1 - ratio));
        int blue = (int)(235 * (1 - ratio));
        setBackground(new Color(red, green, blue));
        if (bgImg != null)
            g.drawImage(bgImg, 0, -yOffset, getWidth(), bgImg.getHeight(null), this);
        for (Block block : blocks)
            block.draw(g);
        for (Health h : health)
            h.updateHealth(g);
    }
}