import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ImagePanel extends JPanel {
    private Image image;
    private JButton replayButton;
    private JButton gameOverButton;
    private MainFrame frame;

    public ImagePanel(String imagePath, MainFrame frame) {
        this.frame = frame;
        setLayout(null);

        //ภาพพื้นหลัง
        ImageIcon icon = new ImageIcon(imagePath);
        image = icon.getImage();

        //ภาพของปุ่ม Replay
        String replayImagePath = "C:/Users/Asus/Documents/GitHub/Tower/Endgame/src/resources/replay.png";
        ImageIcon replayIcon = new ImageIcon(replayImagePath);
        Image replayImg = replayIcon.getImage().getScaledInstance(150, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledReplayIcon = new ImageIcon(replayImg);

        //ภาพของปุ่ม Game Over
        String gameOverImagePath = "C:/Users/Asus/Documents/GitHub/Tower/Endgame/src/resources/exit.png";
        ImageIcon gameOverIcon = new ImageIcon(gameOverImagePath);
        Image gameOverImg = gameOverIcon.getImage().getScaledInstance(150, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledGameOverIcon = new ImageIcon(gameOverImg);

        //สร้างปุ่ม Replay
        replayButton = new JButton(scaledReplayIcon);
        replayButton.setBounds(255, 450, 170, 90);
        replayButton.setBorderPainted(false);
        replayButton.setFocusPainted(false);
        replayButton.setContentAreaFilled(false);
        add(replayButton);

        //สร้างปุ่ม Exit
        gameOverButton = new JButton(scaledGameOverIcon);
        gameOverButton.setBounds(255, 560, 170, 90);
        gameOverButton.setBorderPainted(false);
        gameOverButton.setFocusPainted(false);
        gameOverButton.setContentAreaFilled(false);
        add(gameOverButton);

        //กดปุ่ม Replay → โหลดหน้าใหม่
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainFrame().setVisible(true);
            }
        });

        //กดปุ่ม Game Over → ปิดโปรแกรม
        gameOverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
