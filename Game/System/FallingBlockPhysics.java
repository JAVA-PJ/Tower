package Game.System;
import Game.Component.Block;
import Game.Screen.App;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class FallingBlockPhysics {
    private Block block;
    private double velocityX; // ความเร็วแนวนอน
    private double velocityY; // ความเร็วแนวตั้ง
    private double angularVelocity; // ความเร็วเชิงมุม (การหมุน)
    private double angle; // มุมการหมุน (เป็นเรเดียน)
    private double gravity; // แรงโน้มถ่วง
    private int fallDirection; // -1 ซ้าย, 0 ลงตรง, 1 ขวา
    
    public FallingBlockPhysics(Block block, int fallDirection) {
        this.block = block;
        this.gravity = 0.5;
        this.fallDirection = fallDirection;
        
        // กำหนดค่าเริ่มต้นตามทิศทางการตก
        if (fallDirection == 0) { // ตกตรง
            this.velocityX = 0;
            this.velocityY = 2;
            this.angularVelocity = 0;
        } else if (fallDirection == -1) { // ตกซ้าย
            this.velocityX = -2;
            this.velocityY = 2;
            this.angularVelocity = -0.05;
        } else { // ตกขวา
            this.velocityX = 2;
            this.velocityY = 2;
            this.angularVelocity = 0.05;
        }
        
        this.angle = 0;
    }
    
    public void update() {
        // อัปเดตความเร็วตามแรงโน้มถ่วง
        velocityY += gravity;
        
        // อัปเดตตำแหน่ง
        block.setPosX(block.getPosX() + (int) velocityX);
        block.setPosY(block.getPosY() + (int) velocityY);
        
        // อัปเดตการหมุน
        angle += angularVelocity;
        
        // ปรับค่า angular velocity เล็กน้อยเพื่อให้การหมุนเร็วขึ้น
        angularVelocity *= 1.01;
    }
    
    public void draw(Graphics g) {
        // บันทึกสถานะปัจจุบันของกราฟิกส์
        Graphics2D g2d = (Graphics2D) g.create();
        
        // ย้ายจุดหมุนไปที่จุดกึ่งกลางของบล็อก
        g2d.translate(block.getPosX() + block.Width/2, block.getPosY() + block.Height/2);
        
        // หมุนตามมุม
        g2d.rotate(angle);
        
        // ถ้ามีภาพให้วาดภาพ ไม่มีก็วาดสี่เหลี่ยมสีตามเดิม
        if (block.getImage() != null) {
            g2d.drawImage(block.getImage(),
                         -block.Width/2,
                         -block.Height/2,
                         block.Width,
                         block.Height,
                         null);
        } else {
            // วาดบล็อกด้วยสีตามเดิมถ้าไม่มีภาพ
            g2d.setColor(block.getColor());
            g2d.fillRect(-block.Width/2, -block.Height/2, block.Width, block.Height);
        }
        
        // คืนค่าสถานะกราฟิกส์
        g2d.dispose();
    }
    
    public boolean isOutOfBounds() {
        // ตรวจสอบว่าบล็อกออกจากหน้าจอหรือไม่
        return block.getPosY() > App.HEIGHT + 100 ||
              block.getPosX() > App.WIDTH + 100 ||
              block.getPosX() < -100;
    }
    
    // เพิ่มเมธอดเพื่อเข้าถึงบล็อก
    public Block getBlock() {
        return block;
    }
}
