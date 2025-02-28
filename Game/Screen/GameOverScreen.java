package Game.Screen;
import Enum.ImageType;
import Enum.SoundType;
import Game.System.Display;
import MainMenu.Button;
import Sound.BackgroundMusic;
import Sound.SoundEffect;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class GameOverScreen {
    // Display
    private Display dp;

    // Sound
    private boolean gameOverSound = false;

    // Score
    private GameOverScore gameOverScore;

    // Image
    private Image gameOverImg;
    private Image exitImg;
    private Image replayImg;

    // Button
    private Button replayButton;
    private Button exitButton;

    public GameOverScreen(Display dp) {
        this.dp = dp;

        gameOverScore = new GameOverScore();

        exitImg = new ImageIcon(getClass().getResource(ImageType.EXIT_BUTTON.getPath())).getImage();
        replayImg = new ImageIcon(getClass().getResource(ImageType.REPLAY_BUTTON.getPath())).getImage();
        gameOverImg = new ImageIcon(getClass().getResource(ImageType.BG_GAMEOVER.getPath())).getImage();
    }

    // Stop the game
    public void gameStop(Graphics g, Timer gameLoop, BackgroundMusic bgSound) {
        // Stop loop game
        gameLoop.stop();

        // Play GameOver Sound 1 time : False -> Play sound : True -> Lock sound
        if (!gameOverSound) {
            SoundEffect.playSoundEffect(SoundType.GAMEOVER);
            gameOverSound = true;
        }

        // Draw GameOver Frame
        if (gameOverImg != null)
            g.drawImage(gameOverImg, 0, 0, dp.getWidth(), dp.getHeight(), dp);
        
        // Init buttons
        ImageIcon replayIcon = new ImageIcon(replayImg);
        replayButton = new Button(replayIcon);
        replayButton.setBounds(255, 450, 170, 90);
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameRestart(gameLoop);
                gameOverSound = false;
                bgSound.play();
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

        gameOverScore.drawScore(g);
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
