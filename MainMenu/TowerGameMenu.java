package MainMenu;
import Enum.ImageType;
import Enum.SoundType;
import Game.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class TowerGameMenu extends JFrame {
    private Button StartButton, HowToPlayButton, ExitButton;
    private JLabel SoundButton, SungifLabel;
    private HowToPlayScreen howToPlayScreen;
    private BackgroundMusic Music;
    private boolean isMuted = false;
    private Image backgroundImage;
    private Background mainMenuPanel;
    private WindEffect wind;
    
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
        Music = new BackgroundMusic(SoundType.BG_MUSIC.getPath());
        Music.play();

        // Create main menu once
        createMainMenuPanel();
        
        // Display main menu
        showMainMenu();
        
        // Add component resize listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
                if (wind != null) {
                    wind.setBounds(0, 0, getWidth(), getHeight());
                }
            }
        });
        
        setVisible(true);
    }
    
    private void createMainMenuPanel() {
        // Create background panel
        mainMenuPanel = new Background(ImageType.BG_MENU.getPath());
        mainMenuPanel.setLayout(null);
        
        // Create sun gif
        ImageIcon sungif = new ImageIcon(getClass().getResource(ImageType.SUN.getPath()));
        SungifLabel = new JLabel(sungif);
        mainMenuPanel.add(SungifLabel, 0); // เพิ่ม GIF ลงพื้นหลัง

        // Create sound icons
        ImageIcon soundOn = new ImageIcon(new ImageIcon(getClass().getResource(ImageType.SOUND_ON.getPath())).getImage()
                .getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        ImageIcon soundOff = new ImageIcon(new ImageIcon(getClass().getResource(ImageType.SOUND_OFF.getPath())).getImage()
                .getScaledInstance(70, 70, Image.SCALE_SMOOTH));

        // Create sound button
        SoundButton = new JLabel(soundOn);
        SoundButton.setBounds(30, 35, 70, 50);
        SoundButton.setOpaque(false);

        // Add sound button listeners
        SoundButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sound.playSound(SoundType.CLICK);
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
        mainMenuPanel.add(SoundButton);

        // Create wind effect
        wind = new WindEffect(getWidth(), getHeight());
        wind.setBounds(0, 0, getWidth(), getHeight());
        mainMenuPanel.add(wind);
  
        // Create menu buttons
        ImageIcon startIcon = new ImageIcon(getClass().getResource(ImageType.START_BUTTON.getPath()));
        ImageIcon howToPlayIcon = new ImageIcon(getClass().getResource(ImageType.HOWTOPLAY_BUTTON.getPath()));
        ImageIcon exitIcon = new ImageIcon(getClass().getResource(ImageType.EXIT_BUTTON_MENU.getPath()));

        StartButton = new Button(startIcon);
        HowToPlayButton = new Button(howToPlayIcon);
        ExitButton = new Button(exitIcon);

        // Add button listeners
        StartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Music.stop();
                dispose();
                new App();
            }
        });

        HowToPlayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showHowToPlayScreen();
            }
        });

        ExitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // Add buttons to panel
        mainMenuPanel.add(StartButton);
        mainMenuPanel.add(HowToPlayButton);
        mainMenuPanel.add(ExitButton);
        mainMenuPanel.add(wind);
        
        // Initial positioning of components
        resizeComponents();
    }
    
    public void showMainMenu() {
        howToPlayScreen = new HowToPlayScreen(this);
        setContentPane(mainMenuPanel);
        revalidate();
        repaint();
    }
    
    public void showHowToPlayScreen() {
        setContentPane(howToPlayScreen);
        revalidate();
        repaint();
    }

    private void updateSunGifSize() {
        if (SungifLabel == null) return;
        
        int width = getWidth();
        int height = getHeight();
        //ปรับขนาด gif
        int sunSize = Math.max(50, width / 5);
        int sunX = Math.max(100, width / 60);
        int sunY = Math.max(1, height / 80);
        //โหลดgifใหม่
        ImageIcon Sungif = new ImageIcon(getClass().getResource(ImageType.SUN.getPath()));
        Image scalegif = Sungif.getImage().getScaledInstance(sunSize, sunSize, Image.SCALE_DEFAULT);
        //อัปเดตgifใหม่
        SungifLabel.setIcon(new ImageIcon(scalegif));
        //เซ็ทตำแหน่งของgif
        SungifLabel.setBounds(sunX, sunY, sunSize, sunSize);
    }
    
    //ตำแหน่งปุ่ม GUI
    private void resizeComponents() {
        if (StartButton == null || HowToPlayButton == null || ExitButton == null) return ;
        
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
        ImageIcon startIcon = new ImageIcon(getClass().getResource(ImageType.START_BUTTON.getPath()));
        Image scaleStart = startIcon.getImage().getScaledInstance(buttonW, buttonH, Image.SCALE_SMOOTH);
        StartButton.setIcon(new ImageIcon(scaleStart));
        
        ImageIcon howToPlayIcon = new ImageIcon(getClass().getResource(ImageType.HOWTOPLAY_BUTTON.getPath()));
        Image scalehowtoplay= howToPlayIcon.getImage().getScaledInstance(buttonW, buttonH, Image.SCALE_SMOOTH);
        HowToPlayButton.setIcon(new ImageIcon(scalehowtoplay));
        
        ImageIcon exitIcon = new ImageIcon(getClass().getResource(ImageType.EXIT_BUTTON_MENU.getPath()));
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
    
    // Method to get music state (for HowToPlayScreen)
    public boolean isMusicMuted() {
        return isMuted;
    }
}