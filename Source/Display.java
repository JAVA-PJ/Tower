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
    private int blockHeight = 50;

    // Block
    private Block newBlock;
    private ArrayList<Block> blocks;

	// Image
    private Image bgImg;

    public Display() {
        setPreferredSize(new Dimension(App.WIDTH, App.HEIGHT));
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

    private void spawnBlock() {
        newBlock = new Block();
        blocks.add(newBlock);
    }
    
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
                    blocks.remove(newBlock);
                    spawnBlock();
                }
            }
        }
    }
    
    private int getLastBlockPosY() {
        if (blocks.size() == 1)
            return (App.HEIGHT);
        return (blocks.get(blocks.size() - 2).posY);
    }

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

    public void gameLoop() {
        update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int yOffset = (numBlocks * blockHeight);
        float ratio = Math.min(1.0f, numBlocks / 100.0f);
        int red = (int)(135 * (1 - ratio));
        int green = (int)(206 * (1 - ratio));
        int blue = (int)(235 * (1 - ratio));
        setBackground(new Color(red, green, blue));
        if (bgImg != null)
            g.drawImage(bgImg, 0, yOffset, getWidth(), bgImg.getHeight(null), this);
        for (Block block : blocks)
            block.draw(g);
    }
}