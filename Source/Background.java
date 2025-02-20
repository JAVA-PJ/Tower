package Source;
import java.awt.*;
import javax.swing.*;

public class Background extends JPanel {
    private int boardWidth = 750;
    private int boardHeight = 1000;
    private int heightLevel = 0;

	//Image
    private Image backgroundImg;

    public Background() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.cyan);

        backgroundImg = new ImageIcon(getClass().getResource("../Assets/background.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (backgroundImg != null) {
        g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
    }
}
}
