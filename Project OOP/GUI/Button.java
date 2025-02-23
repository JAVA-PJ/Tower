package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {

    private Color normalColor = new Color(255, 140, 0); 
    private Color hoverColor = new Color(255, 165, 0); 
    private Color borderColor = Color.white;
    private Color highlightColor = new Color(255, 200, 120, 80);
    private Color pressedColor = new Color(180, 90, 0); 

    public Button(String text) {
        super(text);
        setFont(new Font("Arial Black", Font.BOLD, 24));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        // เอฟเฟกต์ไฮไลท์เมื่อเอาเมาส์ชี้ค้างไว้
        addMouseListener(new MouseAdapter() {
            // เมาส์ชี้เข้า
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            // เอาเมาส์ออก
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }

            // เพิ่มขึ้นมา
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor); // เปลี่ยนสีเข้มขึ้น
                setLocation(getX(), getY() + 3); // เลื่อนลงเบาๆ ให้ดูเหมือนกดลงไป
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor); // กลับไปเป็นสี hover
                setLocation(getX(), getY() - 3); // เด้งกลับขึ้น
            }
        });
        setBackground(normalColor);

    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // พื้นหลังปุ่ม
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 120, 120);

        // ขอบปุ่ม
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(12)); // กำหนดความหนาของเส้นขอบ
        g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 120, 120);

        // วงรีข้างใน
        g2.setColor(highlightColor);
        g2.fillRoundRect(13, 5, getWidth() - 30, getHeight() / 2 - 5, 170, 120);

        // ข้อความ
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 5;

        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);
        g2.dispose();
        GradientPaint goldGradient = new GradientPaint(
                0, 0, new Color(255, 223, 0), // สีทองอ่อน
                getWidth(), getHeight(), new Color(218, 165, 32) // สีทองเข้ม
        );
        g2.setPaint(goldGradient);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 120, 120);
    }
}
