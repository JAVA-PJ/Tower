package MainMenu;
import Game.App;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class TowerGameMenu extends JFrame {
    private JButton StartButton, HowToPlayButton, ExitButton;
    private JLabel SoundButton, SungifLabel;
    private BackgroundMusic Music;
    private boolean isMuted = false;
    private BackgroundPanel mainBackground;

	public TowerGameMenu() {
        setTitle("Tower Game");
        setSize(1080, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Music = new BackgroundMusic("../Assets/BgMusic.wav");
        Music.play();

        // Initialize main background
        mainBackground = new BackgroundPanel("../Assets/Sunsetbg.png", this);
        
        // Set up the main menu
        setUpMainMenu();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
                if (mainBackground.getComponentCount() > 0) {
                    Component wind = mainBackground.getComponent(mainBackground.getComponentCount() - 1);
                    if (wind instanceof WindEffect) {
                        wind.setBounds(0, 0, getWidth(), getHeight());
                    }
                }
            }
        });
        
        setVisible(true);
    }

    // Method to return to main menu
    public void returnToMainMenu() {
        setUpMainMenu();
        revalidate();
        repaint();
    }

    // Set up the main menu components
    private void setUpMainMenu() {
        // Clear current content and set main background
        setContentPane(mainBackground);
        mainBackground.isHowToPlayScreen = false;
        mainBackground.removeAll();

        // Add sun gif
        ImageIcon sungif = new ImageIcon(getClass().getResource("../Assets/Sun4.gif"));
        SungifLabel = new JLabel(sungif);
        mainBackground.add(SungifLabel, 0);

        // Add sound button
        ImageIcon soundOn = new ImageIcon(new ImageIcon(getClass().getResource("../Assets/sound_On.png")).getImage()
                .getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        ImageIcon soundOff = new ImageIcon(new ImageIcon(getClass().getResource("../Assets/sound_Off.png")).getImage()
                .getScaledInstance(70, 70, Image.SCALE_SMOOTH));

        SoundButton = new JLabel(isMuted ? soundOff : soundOn);
        SoundButton.setBounds(30, 35, 70, 50);
        SoundButton.setOpaque(false);

        SoundButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        mainBackground.add(SoundButton);

        // Add wind effect
        WindEffect wind = new WindEffect(getWidth(), getHeight());
        wind.setBounds(0, 0, getWidth(), getHeight());
        mainBackground.add(wind);

        // Create and add main buttons
        StartButton = createStyledButton("Start");
        HowToPlayButton = createStyledButton("How to play");
        ExitButton = createStyledButton("Exit");

		StartButton.addActionListener(e -> {
			Music.stop();
			new App();
			dispose();
		});

        ExitButton.addActionListener(e -> System.exit(0));
        
        HowToPlayButton.addActionListener(e -> {
            showHowToPlayScreen();
        });

        mainBackground.add(StartButton);
        mainBackground.add(HowToPlayButton);
        mainBackground.add(ExitButton);
        mainBackground.add(wind);

        resizeComponents();
    }

    // Method to show how to play screen
    private void showHowToPlayScreen() {
        mainBackground.howToPlay();
    }

    private JButton createStyledButton(String text) {
        return new Button(text);
    }

    private void updateSunGifSize() {
        if (SungifLabel != null) {
            int width = getWidth();
            int height = getHeight();
            //ปรับขนาด gif
            int sunSize = Math.max(50, width / 5);
            int sunX = Math.max(100, width / 60);
            int sunY = Math.max(1, height / 80);
            //โหลดgifใหม่
            ImageIcon Sungif = new ImageIcon(getClass().getResource("../Assets/Sun4.gif"));
            Image scalegif = Sungif.getImage().getScaledInstance(sunSize, sunSize, Image.SCALE_DEFAULT);
            //อัปเดตgifใหม่
            SungifLabel.setIcon(new ImageIcon(scalegif));
            //เซ็ทตำแหน่งของgif
            SungifLabel.setBounds(sunX, sunY, sunSize, sunSize);
        }
    }

    //ตำแหน่งปุ่ม GUI
    private void resizeComponents() {
        if (StartButton != null && HowToPlayButton != null && ExitButton != null) {
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
            //ขนาดตัวหนังสือ
            int fontSize = Math.max(20, buttonW / 10);
            Font buttonFont = new Font("Arial", Font.BOLD, fontSize);
            StartButton.setFont(buttonFont);
            HowToPlayButton.setFont(buttonFont);
            ExitButton.setFont(buttonFont);

            updateSunGifSize();
        }
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