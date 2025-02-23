import javax.swing.*;

class MainFrame extends JFrame {
    public MainFrame() {
        setSize(700, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String imagePath = "C:/Users/Asus/Documents/GitHub/Tower/Endgame/src/resources/gameover.png";
        add(new ImagePanel(imagePath, this));
    }
}
