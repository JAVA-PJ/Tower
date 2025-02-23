package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class TowerGameMenu extends JFrame {
    private JButton StartButton, HowToPlayButton, ExitButton;
    private JLabel SoundButton,SunGifLabel;
    private BackgroundMusic Music;
    private boolean isMuted = false;
    
    class Background extends JPanel {
        private Image backgroundImage;

        public Background(String imagePath) {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    public TowerGameMenu() {
        setTitle("Tower Game");
        setSize(1080, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Music = new BackgroundMusic("Asset/Sound_background.wav");
        Music.play();

        // BackgroundPanel
        Background background = new Background("Asset/Sunsetbg.png");
        background.setLayout(null);
        setContentPane(background);

        ImageIcon sunGif = new ImageIcon(getClass().getResource("Asset/Sun4.gif"));
        SunGifLabel = new JLabel(sunGif);
        background.add(SunGifLabel,0); // เพิ่ม GIF ลงพื้นหลัง

        //อัพไอคอนเสียง
        ImageIcon soundOn = new ImageIcon(new ImageIcon(getClass().getResource("Asset/sound_On.png")).getImage()
                .getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        ImageIcon soundOff = new ImageIcon(new ImageIcon(getClass().getResource("Asset/sound_Off.png")).getImage()
                .getScaledInstance(70, 70, Image.SCALE_SMOOTH));

        //ปุ่มไอคอนเสียง
        SoundButton = new JLabel(soundOn);
        SoundButton.setBounds(30, 35, 70, 50);
        SoundButton.setOpaque(false);

        // คลิก เปิด-ปิดเสียง
        SoundButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSound(soundOn, soundOff);
            }
        
            //เมื่อชี้เมาส์เข้าไป
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2));
            }

            //เมื่อเอาเมาส์ออกมา
            @Override
            public void mouseExited(MouseEvent e) {
                SoundButton.setBorder(null);
            }

        });

        background.add(SoundButton);

        // ลมม
        WindEffect wind = new WindEffect(getWidth(), getHeight());
        wind.setBounds(0, 0, getWidth(), getHeight());
        background.add(wind);
  

        // ปุ่มหลักสามปุ่ม
        StartButton = createStyledButton("Start");
        HowToPlayButton = createStyledButton("How to play");
        ExitButton = createStyledButton("Exit");

        // ปิดโปรแกรม
        ExitButton.addActionListener(e -> System.exit(0));

        // เพิ่มออบเจกต์ลงพื้นหลัง
        background.add(StartButton);
        background.add(HowToPlayButton);
        background.add(ExitButton);
        background.add(wind);
    
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
                wind.setBounds(0, 0, getWidth(), getHeight());
            }
        });
        resizeComponents();
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        return new Button(text);
    }
    
    private void updateSunGifSize() {
        int width = getWidth();
        int height = getHeight();
        // ปรับขนาด GIF 
        int sunSize = Math.max(50, width / 5);  
        int sunX = Math.max(100, width / 60);    
        int sunY = Math.max(1, height / 80);  
    
        //โหลด GIF ใหม่
        ImageIcon sunGif = new ImageIcon(getClass().getResource("Asset/Sun4.gif"));
        Image scaledGif = sunGif.getImage().getScaledInstance(sunSize, sunSize, Image.SCALE_DEFAULT);
        
        // อัปเดต GIF ใหม่
        SunGifLabel.setIcon(new ImageIcon(scaledGif));
        
        // ตั้งค่าตำแหน่งของSunGifLabel
        SunGifLabel.setBounds(sunX, sunY, sunSize, sunSize);
    }

    // ตำแหน่งปุ่ม GUI
    private void resizeComponents() {
        int width = getWidth();
        int height = getHeight();
        int buttonWidth = width / 4;
        int buttonHeight = height / 10;
        int centerX = width / 2 - buttonWidth / 2;
        int spacing = height / 20;
        int offsetY = height / 4; //

        StartButton.setBounds(centerX, offsetY, buttonWidth, buttonHeight);
        HowToPlayButton.setBounds(centerX, offsetY + spacing + buttonHeight, buttonWidth, buttonHeight);
        ExitButton.setBounds(centerX, offsetY + 2 * (spacing + buttonHeight), buttonWidth, buttonHeight);
        // ขนาดตัวหนังสือ
        int fontSize = Math.max(20, buttonWidth / 10);
        Font buttonFont = new Font("Arial", Font.BOLD, fontSize);
        StartButton.setFont(buttonFont);
        HowToPlayButton.setFont(buttonFont);
        ExitButton.setFont(buttonFont);
        // อัปเดตขนาดของ GIF
        updateSunGifSize();
    }

    // สลับเสียง
    private void toggleSound(ImageIcon soundOnIcon, ImageIcon soundOffIcon) {
        if (isMuted) {
            Music.play();
            SoundButton.setIcon(soundOnIcon);
        } else {
            Music.stop();
            SoundButton.setIcon(soundOffIcon);
        }
        isMuted = !isMuted;
    }
}
