package Source;
import java.awt.*;
import javax.swing.*;

public class Background extends JPanel {
    private int boardWidth = 750;
    private int boardHeight = 1000;
    private int blockHeight = 50;
    private int numBlocks = 0;
	//Image
    private Image backgroundImg;

    public Background() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

        backgroundImg = new ImageIcon(getClass().getResource("../Assets/background.png")).getImage();
    }

    public void addBlock() {
        numBlocks++;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int yOffset = (numBlocks * blockHeight);
    float ratio = Math.min(1.0f, numBlocks / 20.0f);
        int red = (int)(135 * (1 - ratio));
        int green = (int)(206 * (1 - ratio));
        int blue = (int)(235 * (1 - ratio));
        setBackground(new Color(red, green, blue));
    if (backgroundImg != null) {
        g.drawImage(backgroundImg, 0, yOffset, getWidth(), backgroundImg.getHeight(null), this);
    }
}
}