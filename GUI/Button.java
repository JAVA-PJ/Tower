package GUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
public class Button extends JButton {
    private Color normalColor = new Color(30, 144, 255);
    private Color hoverColor = new Color(50, 180, 255);
    private Color borderColor = new Color(0, 102, 204);
    private Color highlightColor = new Color(255, 255, 255, 80);
    
    public Button(String text) {
        super(text);
        setFont(new Font("Arial Black", Font.BOLD, 24));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        //เอฟเฟกต์ไฮไลท์เมื่อเอาเมาส์ชี้ค้างไว้
        addMouseListener(new MouseAdapter() {
            //เมาส์ชี้เข้า
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }
            //เอาเมาส์ออก
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }
        });
        setBackground(normalColor);
        
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //พื้นหลังปุ่ม
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

        //ขอบปุ่ม 
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(5)); //กำหนดความหนาของเส้นขอบ
        g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 40, 40);

        //สีฟ้าอ่อนข้างใน
        g2.setColor(highlightColor);
        g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() / 2 - 5, 40, 40);

        //ข้อความ
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 5;
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
}

