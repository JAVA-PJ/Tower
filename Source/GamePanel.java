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

public class GamePanel extends JPanel implements Runnable {
    // Screen
    private final int screenWidth = 750;
    private final int screenHeight = 1000;

    // Game Panel
    private int numBlocks = 0;
    private int blockHeight = 50;
    private boolean running = true;

    // Block
    private Block curBlock;
    private ArrayList<Block> blocks;

	// Image
    private Image backgroundImg;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        blocks = new ArrayList<>();
        spawnBlock();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !curBlock.falling)
                    curBlock.fall();
            }
        });

        backgroundImg = new ImageIcon(getClass().getResource("../Assets/background.png")).getImage();
        setFocusable(true);
        new Thread(this).start();
    }

    private void spawnBlock() {
        int width = 230, height = 160;
        int startX = screenWidth / 2 - width / 2;
        curBlock = new Block(startX, 50, width, height);
        blocks.add(curBlock);
    }

    private void update() {
        curBlock.swing(screenWidth);

        if (curBlock.falling) {
            curBlock.fall();
            if (curBlock.y + curBlock.height >= screenHeight || collideWithPreviousBlock()) {
                curBlock.falling = false;
                spawnBlock();
                numBlocks++;
            }
        }
    }

    private boolean collideWithPreviousBlock() {
        if (blocks.size() < 2) { return (false); }
        Block prevBlock = blocks.get(blocks.size() - 2);
        return (curBlock.y + curBlock.height >= prevBlock.y &&
                curBlock.x + curBlock.width > prevBlock.x &&
                curBlock.x < prevBlock.x + prevBlock.width);
    }

    @Override
    public void run() {
        while (running) {
            update();
            repaint();
            try { Thread.sleep(20); }
            catch (InterruptedException e) {}
        }
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
        if (backgroundImg != null)
            g.drawImage(backgroundImg, 0, yOffset, getWidth(), backgroundImg.getHeight(null), this);
        for (Block block : blocks)
            block.draw(g);
    }
}