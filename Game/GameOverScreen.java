package Game;
import Enum.ImageType;
import Enum.SoundType;
import MainMenu.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class GameOverScreen {
    // Display
    private Display dp;

    private boolean soundOn = false;

    // Image
    private Image gameOverImg;
    private Image exitImg;
    private Image replayImg;

    // Button
    private Button replayButton;
    private Button exitButton;

    public GameOverScreen(Display dp) {
        this.dp = dp;

        exitImg = new ImageIcon(getClass().getResource(ImageType.EXIT_BUTTON.getPath())).getImage();
        replayImg = new ImageIcon(getClass().getResource(ImageType.REPLAY_BUTTON.getPath())).getImage();
        gameOverImg = new ImageIcon(getClass().getResource(ImageType.BG_GAMEOVER.getPath())).getImage();
    }

    // Stop the game
    public void gameStop(Graphics g, Timer gameLoop) {
        // Stop loop game
        gameLoop.stop();

        // Play sound
        if (!soundOn) {
            Sound.playSound(SoundType.GAMEOVER);
            soundOn = true;
        }
        // Draw Game Over Frame
        if (gameOverImg != null)
            g.drawImage(gameOverImg, 0, 0, dp.getWidth(), dp.getHeight(), dp);
        
        // Initialize buttons if they're null
        ImageIcon replayIcon = new ImageIcon(replayImg);
        replayButton = new Button(replayIcon);
        replayButton.setBounds(255, 450, 170, 90);
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameRestart(gameLoop);
                soundOn = false;

            }
        });
        dp.add(replayButton);
    
        ImageIcon exitIcon = new ImageIcon(exitImg);
        exitButton = new Button(exitIcon);
        exitButton.setBounds(255, 560, 170, 90);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
        });
        dp.add(exitButton);
    
        dp.score.drawGameOverScore(g, 230, 300);
    }

    // Restart the game
    private void gameRestart(Timer gameLoop) {
        // Remove all components
        dp.removeAll();

        // Revalidate the panel
        dp.revalidate();

        // Reset the game
        dp.setNewGame();

        // Add key listener
        dp.requestFocus();
        dp.setFocusable(true);
        dp.addKeyListener(dp);

        // Start the game loop
        gameLoop.start();
    }
}