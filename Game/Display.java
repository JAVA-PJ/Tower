package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements KeyListener {
    // Game Panel
    private Timer gameLoop;
    private boolean dieLock = false;
    private boolean isPressed = false;
    private final int animationSpeed = 5;

    // Block
    private Block newBlock;
    private ArrayList<Block> blocks;
    private Block failingBlock; // เก็บบล็อกที่ล้มเหลว

    // คลาสใหม่สำหรับจัดการฟิสิกส์การตก
    private FallingBlockPhysics fallingPhysics;

    // Health
    private ArrayList<Health> health;
    private int healthIdx = Health.maxHealth - 1;

    // Start Moving
    private final int startMovingAt = 2;

    // Control background and block
    private int yOffset = 0;
    private int curOffset = 0;
    private int numBlocks = 0;

    // Image
    private Image bgImg;
    private Image exitButton;
    private Image gameOverImg;
    private Image replayButton;
    private Image blockImg;
    // Score
    private Score score;

    // Constructor
    public Display() {
        exitButton = new ImageIcon(getClass().getResource("../Assets/exit.png")).getImage();
        bgImg = new ImageIcon(getClass().getResource("../Assets/background.png")).getImage();
        replayButton = new ImageIcon(getClass().getResource("../Assets/replay.png")).getImage();
        gameOverImg = new ImageIcon(getClass().getResource("../Assets/gameover.png")).getImage();
        blockImg = new ImageIcon(getClass().getResource("../Assets/block.png")).getImage();
        score = new Score();
        blocks = new ArrayList<>();
        health = new ArrayList<>();

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

            while (blocks.size() > 3)
                blocks.remove(0);
            yOffset -= shiftAmount;
        }

        newBlock = blocks.isEmpty() ? new Block() : new Block(new Random().nextInt(App.WIDTH - newBlock.Width));
        newBlock.setImage(blockImg); // กำหนดภาพให้กับบล็อกใหม่
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
                
                if (!blocks.contains(newBlock)) {
                    spawnBlock();
                }
            }
            return;
        }

        if (blocks.size() != 1)
            newBlock.swing(App.WIDTH);

        if (newBlock.falling) {
            newBlock.fall();

            if (blocks.size() == 1 && newBlock.posY + newBlock.Height >= 560) {
                numBlocks++;
                dieLock = false;
                yOffset -= 280;
                newBlock.falling = false;
                score.updateSocre();
                spawnBlock();
                newBlock.animation = false;
                return;
            }

            if (newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                if (collideWithPreviousBlock()) {
                    numBlocks++;
                    dieLock = false;
                    newBlock.falling = false;
                    score.updateSocre();
                    spawnBlock();
                    newBlock.animation = false;
                } else if (blocks.size() > 1 && newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                    failingBlock = newBlock;
                    Block prevBlock = blocks.get(blocks.size() - 2);

                    // กำหนดทิศทางการตก
                    int fallDirection;
                    if (isCompletelyMissed(newBlock, prevBlock)) {
                        fallDirection = 0; // ตกตรง
                    } else if (newBlock.posX + (newBlock.Width / 2) < prevBlock.posX + (prevBlock.Width / 2)) {
                        fallDirection = -1; // ตกซ้าย
                    } else {
                        fallDirection = 1; // ตกขวา
                    }

                    // สร้าง physics engine สำหรับบล็อกที่กำลังตก
                    fallingPhysics = new FallingBlockPhysics(failingBlock, fallDirection);
                    
                    health.get(healthIdx).setIsDie(true);
                    Health.updateCurHealth();
                    dieLock = true;
                    healthIdx--;
                }
            }
        }
    }

    // ฟังก์ชันตรวจสอบว่าบล็อกไม่ชนกันเลย (ไม่มีส่วนที่ซ้อนทับกัน)
    private boolean isCompletelyMissed(Block currentBlock, Block prevBlock) {
        return (currentBlock.posX >= prevBlock.posX + prevBlock.Width) ||
                (currentBlock.posX + currentBlock.Width <= prevBlock.posX);
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

    // Game Over Control
    private void gameOver() {
        JButton replayButton;
        JButton exitButton;

        // Create Replay Button
        replayButton = new JButton();
        replayButton.setBounds(255, 450, 170, 90);
        replayButton.setBorderPainted(false);
        replayButton.setFocusPainted(false);
        replayButton.setContentAreaFilled(false);
        add(replayButton);

        // Create Exit Button
        exitButton = new JButton();
        exitButton.setBounds(255, 560, 170, 90);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        add(exitButton);

        // Press Replay Button → Start a new game
        replayButton.addActionListener(e -> gameRestart());

        // Press Exit Button → Exit the game
        exitButton.addActionListener(e -> System.exit(0));
    }

    // Stop the game
    private void gameStop(Graphics g) {
        // Stop loop game
        gameLoop.stop();

        // Draw Game Over Frame
        g.drawImage(gameOverImg, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(replayButton, 255, 450, 170, 90, this);
        g.drawImage(exitButton, 255, 560, 170, 90, this);

        gameOver();
    }

    // Restart the game
    private void gameRestart() {
        // Remove all components
        this.removeAll();
        // Revalidate the panel
        this.revalidate();
    
        // Reset the game
        blocks.clear();
        score = new Score();
    
        yOffset = 0;
        curOffset = 0;
        numBlocks = 0;
        dieLock = false;
        healthIdx = Health.maxHealth - 1;
        Health.curHealth = Health.maxHealth;
        
        // Reset physics
        fallingPhysics = null;
        failingBlock = null;
    
        for (Health h : health)
            h.setIsDie(false);
        spawnBlock();
    
        // Add key listener
        this.requestFocus();
        this.setFocusable(true);
        this.addKeyListener(this);
    
        // Start the game loop
        gameLoop.start();
    }

    public void mappingBackground() {
        float ratio = Math.min(1.0f, numBlocks * 2.5f / 100.0f);
        int red = (int) (135 * (1 - ratio));
        int green = (int) (206 * (1 - ratio));
        int blue = (int) (235 * (1 - ratio));

        setBackground(new Color(red, green, blue));
    }

    // Key Listener
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !newBlock.falling && fallingPhysics == null) {
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
    }

    // Key Listener
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Paint the game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Check if health less than 0 then stop the game
        if (Health.curHealth <= 0) {
            gameStop(g);
            return;
        }
        // Mapping the background color
        mappingBackground();
        // Draw Background
        if (bgImg != null)
            g.drawImage(bgImg, 0, curOffset, getWidth(), bgImg.getHeight(null), this);
        
        // วาดบล็อกปกติ
        for (Block block : blocks) {
            if (block == failingBlock && fallingPhysics != null) {
                continue; // ข้ามการวาดบล็อกที่กำลังตกด้วยฟิสิกส์ (จะวาดแยก)
            }
            block.drawBlock(g);
        }
        
        // วาดบล็อกที่กำลังตกด้วยฟิสิกส์
        if (fallingPhysics != null) {
            fallingPhysics.draw(g);
        }
        
        for (Health h : health)
            h.updateHealth(g);
        score.drawScore(g);
    }
}