package Entity;

import Game.GamePanel;
import Game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    GamePanel gp;

    // Hitbox and collision
    public int worldX, worldY;
    public Rectangle hitBox = new Rectangle(0,0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public boolean collisionDetected = false;
    public boolean invincible = false;
    public int invincibleCounter;
    public boolean onPath = false;


    // Entities
    public int actionLockCounter;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int speed;
    public int score = 0;

    public void setAction() {}

    public void checkCollision(){
        collisionDetected = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkSpaceshipPart(this, false);
        gp.collisionChecker.checkEntity(this, gp.alien);
        gp.collisionChecker.checkPlayer(this);

        if (!collisionDetected) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
    }

    public void update() {
        setAction();

        checkCollision();

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
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
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

    public String name;
    public boolean collision = false;

    public void draw(Graphics2D g2) {

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


    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + hitBox.x)/gp.tileSize;
        int startRow = (worldY + hitBox.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()) {
            // Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's hitbox position
            int enLeftX  = worldX + hitBox.x;
            int enRightX = worldX + hitBox.x + hitBox.width;
            int enTopY = worldY + hitBox.y;
            int enBottomY = worldY + hitBox.y + hitBox.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if(collisionDetected) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if(collisionDetected) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if(collisionDetected) {
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if(collisionDetected) {
                    direction = "right";
                }
            }

            // If reaches goal, stop
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }
        }
    }
}