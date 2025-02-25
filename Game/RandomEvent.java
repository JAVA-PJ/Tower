package Game;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class RandomEvent {
    private Image cloud1Img, cloud2Img, cloud3Img, moonImg;
    private Image[] starImgs; 
    private final int eventWidth = 240;  
    private final int eventHeight = 120;
    
    private final long eventInterval = 20000;      
    private final long eventDisplayDuration = 10000; 
    
    private final long fadeInDuration = 2000;
    private long lastSpawnTime = 0;
    
    private Random rand;
    
    // Flags สำหรับตรวจสอบว่าพระจันทร์หรือดาวถูก spawn แล้วหรือไม่
    private boolean moonSpawned = false;
    private boolean starSpawned = false;
    
    // inner class สำหรับเก็บข้อมูลของ event (ก้อนเมฆ, พระจันทร์, หรือดาว)
    private class Cloud {
        int x, y;
        long spawnTime;
        boolean visible;
        int direction; // 1: เคลื่อนที่จากซ้ายไปขวา, -1: เคลื่อนที่จากขวาไปซ้าย, 0: คงที่ (สำหรับพระจันทร์หรือดาว)
        int baseY;     // ตำแหน่งแกน Y คงที่
        Image img;
        float alpha = 1.0f;  // สำหรับเอฟเฟค fade-in (0.0 - 1.0)
    }
    
    // สร้างอาร์เรย์สำหรับ event 5 อัน (สำหรับก้อนเมฆหรือพระจันทร์/ดาว)
    private Cloud[] clouds = new Cloud[5];
    
    public RandomEvent() {
        rand = new Random();
        try {
            cloud1Img = ImageIO.read(new File("Assets/c1.png"));
            cloud2Img = ImageIO.read(new File("Assets/c2.png"));
            cloud3Img = ImageIO.read(new File("Assets/c3.png"));
            moonImg    = ImageIO.read(new File("Assets/dsa (3).png"));
            starImgs = new Image[3];
            starImgs[0] = ImageIO.read(new File("Assets/dsa (1).png"));
            starImgs[1] = ImageIO.read(new File("Assets/dsa (2).png"));
            starImgs[2] = ImageIO.read(new File("Assets/dsa (4).png"));
        } catch (IOException e) {
            System.out.println("Error loading event images: " + e.getMessage());
        }
        // สร้าง object สำหรับแต่ละ event
        for (int i = 0; i < 5; i++) {
            clouds[i] = new Cloud();
            clouds[i].visible = false;
        }
    }
    
	public void checkSpawn(int score) {
		long now = System.currentTimeMillis();
		
		// ตรวจสอบว่าก้อนเมฆทั้งหมดทำงานเสร็จหรือยัง
		boolean allCloudsDone = true;
		for (Cloud c : clouds) {
			if (c.visible) { // ถ้ามีก้อนเมฆที่ยังมองเห็นอยู่ ให้รอ
				allCloudsDone = false;
				break;
			}
		}
		
		// กรณีคะแนน >= 25 ให้ spawn ดาว (หากยังไม่ถูก spawn)
		if (score >= 25) {
			if (!starSpawned) {
				spawnStars();
				starSpawned = true;
			}
		} 
		// กรณีคะแนน >= 20 ให้ spawn พระจันทร์ แต่ต้องรอให้ก้อนเมฆเคลื่อนที่เสร็จก่อน
		else if (score >= 20) {
			if (!moonSpawned && allCloudsDone) { // รอให้ก้อนเมฆออกจากจอก่อน
				spawnMoon();
				moonSpawned = true;
			}
		} 
		// สำหรับคะแนนต่ำกว่า 20 ให้ spawn ก้อนเมฆตามปกติ
		else {
			// Reset flags ถ้าคะแนนลดลงต่ำกว่า 20
			moonSpawned = false;
			starSpawned = false;
	
			boolean anyVisible = false;
			for (Cloud c : clouds) {
				if (c.visible) {
					anyVisible = true;
					break;
				}
			}
	
			// ถ้าไม่มีเมฆปรากฏ และเวลาถึงกำหนด ให้ spawn ใหม่
			if (!anyVisible && (now - lastSpawnTime >= eventInterval)) {
				spawnClouds();
			}
		}
	}
    
    // Spawn ก้อนเมฆ 5 อัน โดยกำหนดตำแหน่งและทิศทางตาม index
    private void spawnClouds() {
        Image[] cloudImages = { cloud1Img, cloud2Img, cloud3Img };
        for (int i = 0; i < 5; i++) {
            Cloud c = clouds[i];
            c.visible = true;
            c.direction = (i % 2 == 0) ? 1 : -1;
            if (c.direction == 1) {
                c.x = -eventWidth;
            } else {
                c.x = App.WIDTH + eventWidth;  // App.WIDTH = 700
            }
            c.baseY = 200 + i * 100 + rand.nextInt(50);
            c.y = c.baseY;
            c.spawnTime = System.currentTimeMillis();
            c.alpha = 1.0f;
            c.img = cloudImages[rand.nextInt(cloudImages.length)];
        }
        lastSpawnTime = System.currentTimeMillis();
    }
    
    // Spawn พระจันทร์ เมื่อ score >= 20 (และ score < 25)
    private void spawnMoon() {
        Cloud c = clouds[0];
        c.visible = true;
        c.direction = 0; 
        c.x = 450;         
        c.baseY = 100;
        c.y = c.baseY;
        c.spawnTime = System.currentTimeMillis();
        c.alpha = 0.0f;  
        c.img = moonImg;
        for (int i = 1; i < clouds.length; i++) {
            clouds[i].visible = false;
        }
        lastSpawnTime = System.currentTimeMillis();
    }
    
private void spawnStars() {
    // กำหนดตำแหน่ง X สำหรับดาว 3 รูปตามที่คุณต้องการ
    int[] starPositionsX = {50, 300, 500};
    // กำหนดตำแหน่ง Y สำหรับดาว 3 รูป (ค่า Y มีสามค่า)
    int[] starPositionsY = {500, 100, 500};
    
    for (int i = 0; i < 3; i++) {
        Cloud c = clouds[i];
        c.visible = true;
        c.direction = 0; // ไม่เคลื่อนที่
        c.x = starPositionsX[i];
        c.baseY = starPositionsY[i];
        c.y = starPositionsY[i];
        c.spawnTime = System.currentTimeMillis();
        c.alpha = 0.0f; // เริ่ม fade-in
        c.img = starImgs[rand.nextInt(starImgs.length)];
    }
    // ซ่อน event ที่เหลือ
    for (int i = 3; i < clouds.length; i++) {
        clouds[i].visible = false;
    }
    lastSpawnTime = System.currentTimeMillis();
}
    
    // อัปเดตตำแหน่งของ event แต่ละอัน
    public void update() {
		double totalDistance = App.WIDTH + 2 * eventWidth; // 700 + 480 = 1180
		double speedPerMs = totalDistance / (double) eventDisplayDuration;
		long currentTime = System.currentTimeMillis();
		
		for (int i = 0; i < 5; i++) {
			Cloud c = clouds[i];
			if (c.visible) {
				long elapsed = currentTime - c.spawnTime;
				if (c.direction == 1) {
					c.x = (int)(-eventWidth + speedPerMs * elapsed);
				} else if (c.direction == -1) {
					c.x = (int)((App.WIDTH + eventWidth) - speedPerMs * elapsed);
				} else {
					// สำหรับ event แบบคงที่ (พระจันทร์หรือดาว)
					float newAlpha = Math.min(1.0f, (float) elapsed / (float) fadeInDuration);
					c.alpha = newAlpha;
					// ให้คงตำแหน่ง y ไว้ตามค่า baseY (ซึ่งตั้งไว้ตอน spawn)
					c.y = c.baseY;
				}
				if (c.direction != 0 && elapsed >= eventDisplayDuration) {
					c.visible = false;
				}
			}
		}
	}
	
    
    // วาด event ที่แสดงอยู่ โดยใช้ alpha สำหรับ fade-in
    public void drawEvent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < 5; i++) {
            Cloud c = clouds[i];
            if (c.visible && c.img != null) {
                if (c.direction == 0) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, c.alpha));
                } else {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                }
                g2d.drawImage(c.img, c.x, c.y, eventWidth, eventHeight, null);
            }
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
