package Entity;

import Game.GamePanel;
import Game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    public void setAction() {}
    public void update() {
        setAction();

        collisionDetected = false;
        gp.collisionChecker.checkTile(this);

        if (!collisionDetected) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public BufferedImage setup(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    //Sprite animations stored in these variables
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction = "down";

    // Handles objects
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    public void draw(Graphics2D g2) {
        if (worldX + gp.tileSize > gp.player.worldX &&
            worldX - gp.tileSize < gp.player.worldX &&
            worldY + gp.tileSize < gp.player.worldY &&
            worldY - gp.tileSize > gp.player.worldY) {

            BufferedImage image = null;

            switch(direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    else if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    else if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    else if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    else if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
        }
    }
}