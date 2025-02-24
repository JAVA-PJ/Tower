package MainMenu;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Button extends JButton {
    private Color maincolor = new Color(255, 140, 0);
    private Color hover = new Color(255, 165, 0);
    private Color border = Color.white;
    private Color highlight = new Color(255, 200, 120, 80);
    private Color pressed = new Color(180, 90, 0);

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
                setBackground(hover);
                repaint();
            }
            // เอาเมาส์ออก
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(maincolor);
                repaint();
            }
            // เพิ่มขึ้นมา
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressed); // เปลี่ยนสีเข้มขึ้น
                setLocation(getX(), getY() + 3); // เลื่อนลงเบาๆ ให้ดูเหมือนกดลงไป
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hover); // กลับไปเป็นสี hover
                setLocation(getX(), getY() - 3); // เด้งกลับขึ้น
            }
        });
        setBackground(maincolor);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //พื้นหลังปุ่ม
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 120, 120);
        //ขอบปุ่ม
        g2d.setColor(border);
        g2d.setStroke(new BasicStroke(12)); // ความหนาborder
        g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 120, 120);
        //วงรีข้างใน
        g2d.setColor(highlight);
        g2d.fillRoundRect(13, 5, getWidth() - 30, getHeight() / 2 - 5, 170, 120);
        //ข้อความ
        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 5;
        g2d.setColor(getForeground());
        g2d.drawString(getText(), x, y);
        g2d.dispose();
    }
}
