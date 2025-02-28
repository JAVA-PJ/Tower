package MainMenu;
import Enum.ImageType;
import Enum.SoundType;
import Sound.SoundEffect;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class HowToPlayScreen extends JPanel {
    private Image backgroundImage;
    private TowerGameMenu gameMenu;
    private JLabel backButton;
    private ImageIcon hoverIcon;
    private ImageIcon backIcon;
    
    public HowToPlayScreen(TowerGameMenu gameMenu) {
        this.gameMenu = gameMenu;
        setLayout(null);
        
        // Set background image
        backgroundImage = new ImageIcon(getClass().getResource(ImageType.BG_HOWTOPLAY.getPath())).getImage();
        
        // Configure back button
        ImageIcon Icon = new ImageIcon(getClass().getResource(ImageType.BACK_BUTTON.getPath()));
        Image scaledImage = Icon.getImage().getScaledInstance(150, 70, Image.SCALE_SMOOTH);
        backIcon = new ImageIcon(scaledImage);
        
        // Configure hover effect
        Image hoverImage = Icon.getImage().getScaledInstance(170, 80, Image.SCALE_SMOOTH); //Hover effect
        hoverIcon = new ImageIcon(hoverImage);
        
        // Create back button
        backButton = new JLabel(backIcon);
        
        // Add mouse listeners for button
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundEffect.playSoundEffect(SoundType.CLICK);
                // Return to main menu
                gameMenu.showMainMenu();
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

        // Add component listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBackButtonPosition();
            }
        });
        
        updateBackButtonPosition();
    }
    
    // Set back button position and size
    private void updateBackButtonPosition() {
        int fWidth = gameMenu.getWidth();
        int fHeight = gameMenu.getHeight();
        int Width = Math.max(100, fWidth / 8);
        int Height = Math.max(50, fHeight / 10);
        int x = fWidth - Width - 50;
        int y = 30;
        
        // Update button icon with new size
        ImageIcon Icon = new ImageIcon(getClass().getResource(ImageType.BACK_BUTTON.getPath()));
        Image scaledImage = Icon.getImage().getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        backIcon = new ImageIcon(scaledImage);
        
        // Update hover icon with new size
        Image hoverImage = Icon.getImage().getScaledInstance((int)(Width * 1.1), (int)(Height * 1.1), Image.SCALE_SMOOTH);
        hoverIcon = new ImageIcon(hoverImage);
        
        // Set button position and size
        backButton.setIcon(backIcon);
        backButton.setBounds(x, y, Width, Height);
    }

    // Draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
