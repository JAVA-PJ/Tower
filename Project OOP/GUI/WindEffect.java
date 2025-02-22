package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
public class WindEffect extends JPanel {
    private class WindStreak {
        int x, y, length, speed;
        float alpha;
        WindStreak(int x, int y, int length, int speed, float alpha) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.speed = speed;
            this.alpha = alpha;
        }
    }
    private ArrayList<WindStreak> wind = new ArrayList<>();
    private Random rand = new Random();
    public WindEffect(int width, int height) {
        setOpaque(false);
        generateWind(width, height);

        Timer timer = new Timer(30, e -> {
            moveWind();
            repaint();
        });
        timer.start();
    }
    private void generateWind(int width, int height) {
        wind.clear(); 
        for (int i = 0; i < 20; i++) {
            wind.add(new WindStreak(
                rand.nextInt(width),         
                rand.nextInt(height),        
                50 + rand.nextInt(100),      
                2 + rand.nextInt(4),         
                0.3f + rand.nextFloat() * 0.5f 
            ));
        }
    }
    private void moveWind() {
        int width = getWidth();
        for (WindStreak wind : wind) {
            wind.x += wind.speed;
            if (wind.x > width) {
                wind.x = -wind.length;
                wind.y = rand.nextInt(getHeight()); 
                wind.speed = 2 + rand.nextInt(4);
                wind.alpha = 0.3f + rand.nextFloat() * 0.5f;
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (WindStreak wind : wind) {
            g2.setColor(new Color(255, 255, 255, (int) (wind.alpha * 255)));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(wind.x, wind.y, wind.x + wind.length, wind.y);
        }
    }
    public void updateWind(int width, int height) {
        generateWind(width, height);
        repaint();
    }
}
