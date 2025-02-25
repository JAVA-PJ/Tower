package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HowToPlay extends JPanel {
    private Image backgroundImage;
    private JFrame frame;
    private JLabel backButton;
    private ImageIcon hoverIcon;
    public HowToPlay(JFrame frame) {
        this.frame = frame;
        setLayout(null); 
        backgroundImage = new ImageIcon(getClass().getResource("Asset/HowToPlay.png")).getImage(); //พื้นหลัง
        ImageIcon Icon = new ImageIcon(getClass().getResource("Asset/ButtonBack.png"));  //ภาพปุ่ม Back
        Image scaledImage = Icon.getImage().getScaledInstance(150, 70, Image.SCALE_SMOOTH);
        ImageIcon backIcon = new ImageIcon(scaledImage);
        Image hoverImage = Icon.getImage().getScaledInstance(170, 80, Image.SCALE_SMOOTH); //Hover effect
        hoverIcon = new ImageIcon(hoverImage);
        backButton = new JLabel(backIcon);
        //ลูกเล่นเกี่ยวกับเมาส์
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Button.ClickSound();
                frame.dispose();
                new TowerGameMenu();
            }
            @Override
            public void mouseEntered(MouseEvent e) { 
                backButton.setIcon(hoverIcon); 
            }
            @Override
            public void mouseExited(MouseEvent e) { 
                backButton.setIcon(backIcon); 
            }
        });
        add(backButton); 

        frame.addComponentListener(new ComponentAdapter() {    //ปรับขนาดปุ่มตามขนาดหน้าจอ
            @Override
            public void componentResized(ComponentEvent e) {
                updateBackButtonPosition(); 
            }
        });
        updateBackButtonPosition();
    }
    
    //คำนวณขนาดปุ่มให้เหมาะสมกับหน้าจอ
    private void updateBackButtonPosition() {
        int fWidth = frame.getWidth();
        int fHeight = frame.getHeight();
        int Width = Math.max(100, fWidth / 8);  
        int Height = Math.max(50, fHeight / 10); 
        int x = fWidth - Width - 50;
        int y = 30; 
    backButton.setBounds(x, y, Width, Height);
    }

    //พื้นหลัง
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); 
    }
}
