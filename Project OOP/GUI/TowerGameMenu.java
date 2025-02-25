package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class TowerGameMenu extends JFrame  {
    private Button StartButton, HowToPlayButton,ExitButton;
    private JLabel SoundButton,SungifLabel;
    private BackgroundMusic Music;
    private boolean isMuted = false;
    private Image backgroundImage;

    class Background extends JPanel {
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
        Music = new BackgroundMusic("Asset/BgMusic.wav");
        Music.play();

        // BackgroundPanel
        Background background = new Background("Asset/Sunsetbg.png");
        background.setLayout(null);
        setContentPane(background);

        ImageIcon sungif = new ImageIcon(getClass().getResource("Asset/Sun4.gif"));
        SungifLabel = new JLabel(sungif);
        background.add(SungifLabel,0); // เพิ่ม GIF ลงพื้นหลัง

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
                Button.ClickSound();
                Sound(soundOn, soundOff);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                SoundButton.setBorder(null);
            }
        });
        background.add(SoundButton);

        //ลม
        WindEffect wind = new WindEffect(getWidth(), getHeight());
        wind.setBounds(0, 0, getWidth(), getHeight());
        background.add(wind);
  
        //ปุ่ม3ปุ่ม
        ImageIcon startIcon = new ImageIcon(getClass().getResource("Asset/StartButton.png"));
        ImageIcon howToPlayIcon = new ImageIcon(getClass().getResource("Asset/HowToPlayButton.png"));
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("Asset/ExitButton.png"));

        StartButton = new Button(startIcon);
        HowToPlayButton = new Button(howToPlayIcon);
        ExitButton = new Button(exitIcon);

        StartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


                
            }
        });

        HowToPlayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Music.stop();
                setContentPane(new HowToPlay(TowerGameMenu.this));
                revalidate();
            }
        });

        ExitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //เพิ่มออบเจกต์ลงพื้นหลัง
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
        setVisible(true);
    }

    private void updateSunGifSize() {
        int width = getWidth();
        int height = getHeight();
        //ปรับขนาด gif
        int sunSize = Math.max(50, width / 5);  
        int sunX = Math.max(100, width / 60);    
        int sunY = Math.max(1, height / 80);  
        //โหลดgifใหม่
        ImageIcon Sungif = new ImageIcon(getClass().getResource("Asset/Sun4.gif"));
        Image scalegif = Sungif.getImage().getScaledInstance(sunSize, sunSize, Image.SCALE_DEFAULT);
        //อัปเดตgifใหม่
        SungifLabel.setIcon(new ImageIcon(scalegif));
        //เซ็ทตำแหน่งของgif
        SungifLabel.setBounds(sunX, sunY, sunSize, sunSize);
    }
    
    //ตำแหน่งปุ่ม GUI
    private void resizeComponents() {
        int width = getWidth();
        int height = getHeight();
        int buttonW = width / 4;
        int buttonH = height / 10;
        int X = width / 2 - buttonW / 2;
        int space = height / 20;
        int Y = height / 4; 
        StartButton.setBounds(X, Y, buttonW, buttonH);
        HowToPlayButton.setBounds(X, Y + space + buttonH, buttonW, buttonH);
        ExitButton.setBounds(X, Y + 2 * (space + buttonH), buttonW, buttonH);
        
        //ขนาดรูปปุ่ม
        ImageIcon startIcon = new ImageIcon(getClass().getResource("Asset/StartButton.png"));
        Image scaleStart = startIcon.getImage().getScaledInstance(buttonW, buttonH, Image.SCALE_SMOOTH);
        StartButton.setIcon(new ImageIcon(scaleStart));
        
        ImageIcon howToPlayIcon = new ImageIcon(getClass().getResource("Asset/HowToPlayButton.png"));
        Image scalehowtoplay= howToPlayIcon.getImage().getScaledInstance(buttonW, buttonH, Image.SCALE_SMOOTH);
        HowToPlayButton.setIcon(new ImageIcon(scalehowtoplay));
        
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("Asset/ExitButton.png"));
        Image scaleExist = exitIcon.getImage().getScaledInstance(buttonW, buttonH, Image.SCALE_SMOOTH);
        ExitButton.setIcon(new ImageIcon(scaleExist));
        
        updateSunGifSize();
    }

    // สลับเสียง
    private void Sound(ImageIcon soundOnIcon, ImageIcon soundOffIcon) {
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


