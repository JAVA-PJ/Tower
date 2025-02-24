package MainMenu;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	private TowerGameMenu frame;
	private Image backgroundImage;
	private Image bgHowToPlayImage;
	private JLabel backButton;
	private ImageIcon hoverIcon;
	protected boolean isHowToPlayScreen = false;

	public BackgroundPanel(String imagePath, TowerGameMenu frame) {
		backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
		setLayout(null);
	}

	public void howToPlay() {
		isHowToPlayScreen = true;
		removeAll(); // Remove all components from the panel
		
		bgHowToPlayImage = new ImageIcon(getClass().getResource("../Assets/HowToPlay.png")).getImage(); //พื้นหลัง
		ImageIcon Icon = new ImageIcon(getClass().getResource("../Assets/ButtonBack.png"));  //ภาพปุ่ม Back
		Image scaledImage = Icon.getImage().getScaledInstance(150, 70, Image.SCALE_SMOOTH);
		ImageIcon backIcon = new ImageIcon(scaledImage);
		Image hoverImage = Icon.getImage().getScaledInstance(170, 80, Image.SCALE_SMOOTH); //Hover effect
		hoverIcon = new ImageIcon(hoverImage);
		backButton = new JLabel(backIcon);

		//ลูกเล่นเกี่ยวกับเมาส์
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.returnToMainMenu();
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

		add(backButton); // เพิ่มปุ่มลง
		addComponentListener(new ComponentAdapter() {    //ปรับขนาดปุ่มตามขนาดหน้าจอ
			@Override
			public void componentResized(ComponentEvent e) {
				updateBackButtonPosition();
			}
		});
		updateBackButtonPosition();
		revalidate();
		repaint();
	}

	//คำนวณขนาดปุ่มให้เหมาะสมกับหน้าจอ กับ ตำแหน่งของปุ่ม
	private void updateBackButtonPosition() {
		if (backButton != null) {
			int fWidth = getWidth();
			int fHeight = getHeight();
			int Width = Math.max(100, fWidth / 8);
			int Height = Math.max(50, fHeight / 10);
			int x = fWidth - Width - 50;
			int y = 30;
			backButton.setBounds(x, y, Width, Height);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isHowToPlayScreen && bgHowToPlayImage != null) {
			g.drawImage(bgHowToPlayImage, 0, 0, getWidth(), getHeight(), this);
		} else {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}