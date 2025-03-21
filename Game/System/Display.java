package Game.System;
import Enum.ImageType;
import Enum.SoundType;
import Game.Component.Block;
import Game.Component.GameScore;
import Game.Component.Health;
import Game.Screen.App;
import Game.Screen.GameOverScreen;
import Sound.BackgroundMusic;
import Sound.SoundEffect;
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
    private Timer gameLoop; // Game loop
    private boolean spawnLock = false; // Lock block spawn when falling
    private boolean isPressed = false; // When space bar is pressed
    private boolean tutorial = true; // Tutorial
    private final int animationSpeed = 5; // Animation speed

    // Game Over Screen
    private GameOverScreen gameOver; // Game Over Screen

    // Block
    private Block newBlock; // New block
    private Block failingBlock; // Failing block
    private ArrayList<Block> blocks; // Array of blocks
    private FallingBlockPhysics fallingPhysics; // Falling physics

    // Health
    private ArrayList<Health> health; // Array of health
    private int healthIdx = Health.maxHealth - 1; // Health index

    // Start Moving
    private final int startMovingAt = 2; // Start moving at 2 blocks

    // Control background and block
    private int yOffset = 0; // Y offset
    private int curOffset = 0; // Current offset
    private int numBlocks = 0; // Number of blocks
    private double startPosition = App.HEIGHT * (560f / 1000f); // Start position

	// Image
    private Image bgImg; // Background image
    private Image spaceBar; // Spacebar tutorial image
    private Image blockImg; // Block image

    // Score
    private GameScore gameScore; // Score

    // Sound
    private BackgroundMusic bgSound; // Background sound

    // Random Event
    private RandomEvent randomEvent; // Random event

    // Constructor
    public Display(BackgroundMusic bgSound) {
        this.bgSound = bgSound;

        bgImg = new ImageIcon(getClass().getResource(ImageType.BG_GAME.getPath())).getImage();
        blockImg = new ImageIcon(getClass().getResource(ImageType.BLOCK.getPath())).getImage();
        spaceBar = new ImageIcon(getClass().getResource(ImageType.SPACEBAR.getPath())).getImage();

        gameScore = new GameScore();
        blocks = new ArrayList<>();
        health  = new ArrayList<>();
        randomEvent = new RandomEvent();
        gameOver = new GameOverScreen(this);
        
        for (int i = 1; i <= Health.maxHealth; i++)
            health.add(new Health(i * -50, 0));

        spawnBlock();
        setFocusable(true);
        setPreferredSize(new Dimension(App.WIDTH, App.HEIGHT));
        addKeyListener(this);

        gameLoop = new Timer(0, e -> {
                if (curOffset >= -yOffset) {
                    gameLoop();
                } else {
                    repaint();
                    for (Block block : blocks)
                        block.setPosY(block.getPosY() + animationSpeed);
                    curOffset += animationSpeed;
                    randomEvent.moving();
                    if (curOffset >= -yOffset) {
                        newBlock.setPosY(40);
                        newBlock.setAnimationPrveBlock(true);
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
        if (numBlocks >= startMovingAt && !spawnLock) {
            int oldFirstBlockY = blocks.get(0).getPosY();
            int shiftAmount = oldFirstBlockY - blocks.get(1).getPosY();

            while (blocks.size() > 4)
                blocks.remove(0);
            yOffset -= shiftAmount;
        }

        newBlock = blocks.isEmpty() ? new Block() : new Block(new Random().nextInt(App.WIDTH - Block.Width));
        newBlock.setImage(blockImg);
        blocks.add(newBlock);
    }
    
    // Update the game
    private void update() {
        // ถ้ากำลังจำลองการตกด้วยฟิสิกส์
        if (fallingPhysics != null) {
            fallingPhysics.update();
            // ถ้าบล็อกออกนอกหน้าจอ
            if (fallingPhysics.isOutOfBounds()) {
                blocks.remove(failingBlock);
                failingBlock = null;
                fallingPhysics = null;
                
                if (!blocks.contains(newBlock))
                    spawnBlock();
            }
            return;
        }

        if (blocks.size() != 1)
            newBlock.swing(App.WIDTH);

        if (newBlock.getFalling()) {
            newBlock.fall();
            boolean blockLanded = false;

            if (blocks.size() == 1 && newBlock.getPosY() + Block.Height >= startPosition) {
                yOffset -= 280;
                blockLanded = true;
            } else if (newBlock.getPosY() + Block.Height >= getLastBlockPosY()) {
                if (collisionWithPreviousBlock())
                    blockLanded = true;
                else if (blocks.size() > 1) {
                    handleFailingBlock();
                    return ;
                }
            }

            if (blockLanded) {
                numBlocks++; // Increment number of blocks
                spawnLock = false; // False -> Unlock block spawn : True -> Lock block spawn
                gameScore.updateSocre(); // Update score by 1
                newBlock.setFalling(false); // False -> Start swing : True -> Stop swing
                randomEvent.spawnNewEvent(gameScore.getScore()); // Spawn new event
                Block.speedUp(); // Speed up the block
                spawnBlock(); // Spawn a new block
                newBlock.setAnimationPrveBlock(false); // False -> Don't draw the block : True -> Draw the block
                SoundEffect.playSoundEffect(SoundType.DROP); // Play drop sound
            }
        }
    }

    private void handleFailingBlock() {
        failingBlock = newBlock;
        Block prevBlock = blocks.get(blocks.size() - 2);

        // Set fall direction
        int fallDirection;
        if (isCompletelyMissed(newBlock, prevBlock))
            fallDirection = 0; // Drop straight down
        else if (newBlock.getPosX() + (Block.Width / 2) < prevBlock.getPosX() + (Block.Width / 2))
            fallDirection = -1; // Drop left
        else
            fallDirection = 1; // Drop right
        
        // Create physics for the falling block
        fallingPhysics = new FallingBlockPhysics(failingBlock, fallDirection);

        if (fallDirection != 0 && Health.getCurHealth() > 1)
            SoundEffect.playSoundEffect(SoundType.FALL);

        health.get(healthIdx).setIsDie(true);
        Health.updateCurHealth();
        spawnLock = true;
        healthIdx--;
    }

    // Check if the block is completely missed
    private boolean isCompletelyMissed(Block currentBlock, Block prevBlock) {
        return (currentBlock.getPosX() >= prevBlock.getPosX() + Block.Width) ||
                (currentBlock.getPosX() + Block.Width <= prevBlock.getPosX());
    }

    // Get the last block position
    private int getLastBlockPosY() {
        return (blocks.size() == 1 ? App.HEIGHT : blocks.get(blocks.size() - 2).getPosY());
    }

    // Check if the block is colliding with the previous block
    private boolean collisionWithPreviousBlock() {
        if (blocks.size() < 2)
            return (false);

        Block prevBlock = blocks.get(blocks.size() - 2);
        int overlapWidth = Math.min(newBlock.getPosX() + Block.Width, prevBlock.getPosX() + Block.Width) -
                           Math.max(newBlock.getPosX(), prevBlock.getPosX());
        boolean isColliding = (newBlock.getPosY() + Block.Height >= prevBlock.getPosY()) &&
                               (newBlock.getPosX() + Block.Width > prevBlock.getPosX()) &&
                               (newBlock.getPosX() < prevBlock.getPosX() + Block.Width);
        boolean isTooWide = overlapWidth < (Block.Width / 2);
        return (isColliding && !isTooWide);
    }

    // Draw Background Gradient
    private void mappingBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    
        // Pastel Purple -> Dark Blue Sky -> Black
        float ratio = Math.min(1.0f, numBlocks * 5.0f / 100.0f);
        Color skyStart = new Color(170, 150, 250);  // Pastel Purple
        Color skyMiddle = new Color(70, 130, 180);  // Dark Blue Sky
        Color skyEnd = new Color(5, 5, 25);         // Black
        Color bottomColor;
        Color topColor;
        
        if (ratio < 0.5f) {
            float adjustedRatio = ratio * 2.0f;
            
            // pastel purple to dark blue
            int topRed = (int)(skyStart.getRed() * (1 - adjustedRatio) + skyMiddle.getRed() * adjustedRatio);
            int topGreen = (int)(skyStart.getGreen() * (1 - adjustedRatio) + skyMiddle.getGreen() * adjustedRatio);
            int topBlue = (int)(skyStart.getBlue() * (1 - adjustedRatio) + skyMiddle.getBlue() * adjustedRatio);
            topColor = new Color(topRed, topGreen, topBlue);
            
            bottomColor = skyMiddle;
        } else {
            // dark blue to black
            float adjustedRatio = (ratio - 0.5f) * 2.0f;
            
            // dark blue to black
            int bottomRed = (int)(skyMiddle.getRed() * (1 - adjustedRatio) + skyEnd.getRed() * adjustedRatio);
            int bottomGreen = (int)(skyMiddle.getGreen() * (1 - adjustedRatio) + skyEnd.getGreen() * adjustedRatio);
            int bottomBlue = (int)(skyMiddle.getBlue() * (1 - adjustedRatio) + skyEnd.getBlue() * adjustedRatio);
            bottomColor = new Color(bottomRed, bottomGreen, bottomBlue);
            
            topColor = skyMiddle;
        }
    
        // Create gradient
        GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Add stars
        if (ratio > 0.7f)
            addStars(g2d, ratio);
    }
    
    // Add stars to the background
    private void addStars(Graphics2D g2d, float ratio) {
        int numStars = (int)(100 * ((ratio - 0.7f) * 5.0f));
        Random random = new Random(1);
        g2d.setColor(Color.WHITE);
        
        for (int i = 0; i < numStars; i++) {
            int x = random.nextInt(App.WIDTH);
            int y = random.nextInt(App.HEIGHT);
            int starSize = random.nextInt(3) + 1;
            
            // Add starts
            g2d.setColor(Color.WHITE);
            if (random.nextFloat() > 0.8f)
                g2d.fillOval(x, y, starSize + 1, starSize + 1);
            else
                g2d.fillOval(x, y, starSize, starSize);
        }
    }

    // Reset game
    public void setNewGame() {
        blocks.clear();
        gameScore.resetScore();
        Health.resetCurHealth();
        randomEvent.clearEvent();
        Block.resetSpeed();

        yOffset = 0;
        curOffset = 0;
        numBlocks = 0;
        spawnLock = false;
        tutorial = true;
        healthIdx = Health.maxHealth - 1;

        fallingPhysics = null;
        failingBlock = null;

        for (Health h : health)
            h.setIsDie(false);
        spawnBlock();
    }

    // Key Listener
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE
            && !newBlock.getFalling() && fallingPhysics == null) {
            if (Health.getCurHealth() > 0 && !isPressed)
                newBlock.fall();
            isPressed = true;
        }
    }
    
    // Key Listener
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            tutorial = false;
            isPressed = false;
    }

    // Key Listener
    @Override
    public void keyTyped(KeyEvent e) {}

    // Paint the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if health less than 0 then stop the game
        if (Health.getCurHealth() <= 0) {
            bgSound.stop();
            gameOver.gameStop(g, gameLoop, bgSound);
            return ;
        }

        // Mapping the background color
        mappingBackground(g);
        
        // Draw Background, Block, Health, Score
        if (bgImg != null)
            g.drawImage(bgImg, 0, curOffset, App.WIDTH, App.HEIGHT, this);
        randomEvent.draw(g);

        // Draw Tutorial
        if (blocks.size() == 1 && tutorial)
            g.drawImage(spaceBar, 225, 250, 250, 100, this);

        // Draw Blocks
        for (Block block : blocks) {
            if (block == failingBlock && fallingPhysics != null)
                continue; // Skip drawing the block that is falling with physics
            block.drawBlock(g);
        }

        // วาดบล็อกที่กำลังตกด้วยฟิสิกส์
        if (fallingPhysics != null)
            fallingPhysics.draw(g);

        for (Health h : health)
            h.updateHealth(g);
        gameScore.drawScore(g);
    }
}
