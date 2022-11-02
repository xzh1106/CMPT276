package Entity;

import Game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;

    public int worldX, worldY;
    public Rectangle hitBox = new Rectangle(8,16, 32, 32);
    public boolean collisionDetected = false;

    public int actionLockCounter;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int speed;
    public int score = 0;

    public void update() {}

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    //Sprite animations stored in these variables
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;
}