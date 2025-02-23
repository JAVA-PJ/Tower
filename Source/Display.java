package Source;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Display extends JPanel {
    // Game Panel
    private int numBlocks = 0;

    // Block
    private Block newBlock;
    private ArrayList<Block> blocks;

    // Health
    private int healthIdx = Health.maxHealth - 1;
    private ArrayList<Health> health;

    // Start Moving
    private final int startMovingAt = 4;

    // Control background and block
    private int offsetY = 0;

	// Image
    private Image bgImg;

    public Display() {
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
        if (numBlocks >= startMovingAt) {
            int oldFirstBlockY = blocks.get(0).posY;
            while (blocks.size() > 2)
                blocks.remove(0);

            int shiftAmount = oldFirstBlockY - blocks.get(0).posY;
            for (Block block : blocks)
                block.posY += shiftAmount;
            offsetY -= shiftAmount;
        }
        newBlock = new Block();
        blocks.add(newBlock);
    }
    
    // Update the game
    private void update() {
        if (blocks.size() != 1)
            newBlock.swing(App.WIDTH);

        if (newBlock.falling) {
            newBlock.fall();
            if (blocks.size() == 1 && newBlock.posY + newBlock.Height >= App.HEIGHT) {
                numBlocks++;
                newBlock.falling = false;
                spawnBlock();
                return;
            }
            if (newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                if (collideWithPreviousBlock()) {
                    numBlocks++;
                    newBlock.falling = false;
                    spawnBlock();
                } else if (blocks.size() > 1 && newBlock.posY + newBlock.Height >= getLastBlockPosY()) {
                    health.get(healthIdx).setIsDie(true);
                    health.get(healthIdx).curHealth--;
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

    // Game loop
    public void gameLoop() {
        update();
        repaint();
    }

    // Stop the game
    public void gameStop() {
        System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if health less than 0 then stop the game
        if (Health.curHealth <= 0)
            gameStop();

        // Draw background, Block, Health
        int yOffset = offsetY;
        float ratio = Math.min(1.0f, numBlocks / 100.0f);
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