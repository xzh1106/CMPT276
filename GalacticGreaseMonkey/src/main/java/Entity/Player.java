package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import Game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public int partsCollected = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        speed = 5;
        direction = "down";
        hitBox = new Rectangle(8,14, 30, 30);
        this.gp = gp;
        this.keyH = keyH;
        worldX = 48;
        worldY = 48;
        solidAreaDefaultX = hitBox.x;
        solidAreaDefaultY = hitBox.y;
        getPlayerImage();
    }

    //retrieve sprite images
    public void getPlayerImage() {
        up1 = setup("monkeyUp1");
        up2 = setup("monkeyUp2");
        down1 = setup("monkeyDown1");
        down2 = setup("monkeyDown2");
        right1 = setup("monkeyRight1");
        right2 = setup("monkeyRight2");
        left1 = setup("monkeyLeft1");
        left2 = setup("monkeyLeft2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    //update player position on key press and animation every 10ms
    public void update() {
        // Handle WASD movement

        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if(keyH.upPressed) {
                direction = "up";
            }

            else if(keyH.downPressed) {
                direction = "down";
            }

            else if(keyH.leftPressed) {
                direction = "left";
            }

            else if(keyH.rightPressed) {
                direction = "right";
            }

            // Check tile collision
            collisionDetected = false;
            gp.collisionChecker.checkTile(this);

            // Check spaceship part collision
            int spaceshipPartIndex = gp.collisionChecker.checkSpaceshipPart(this, true);
            pickUpSpaceshipPart(spaceshipPartIndex);

            int diamondIndex = gp.collisionChecker.checkDiamond(this, true);
            pickUpDiamond(diamondIndex);

            int blackholeIndex = gp.collisionChecker.checkBlackhole(this, true);
            collideBlackhole(blackholeIndex);

            // Check alien collision
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.alien);
            if (monsterIndex != 999) {
                score = -1;
            }

            // If false collision, player can move
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

            if (score < 0){
                gp.currentGameState = gp.loseState;
            }
        }

        // Cooldown for player getting hit
        if (invincible){
            invincibleCounter++;
        }
    }

    public void pickUpSpaceshipPart(int index) {

        // If index is 999, character haven't collided with any object
        if (index != 999) {
            score += 300;
            gp.spaceshipPart[index] = null;
        }
    }

    public void pickUpDiamond(int index) {

        // If index is 999, character haven't collided with any object
        if (index != 999) {
            score += 500;
            gp.diamond.remove(index);
        }
    }

    public void collideBlackhole(int index) {
        if (index != 999) {
            if (!invincible){
                invincible = true;
            }
            score -= 300;
            gp.blackhole[index] = null;
        }
    }
    //Display corresponding image based on key press
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

}
