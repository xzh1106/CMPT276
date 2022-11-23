package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import Game.UtilityTool;

import Object.OBJ_Fireball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * This subclass inherits the attributes and methods from the Entity class
 * is for initializing player properties.
 * @author Ryan
 * @author Jason
 */
public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public int partsCollected = 0;
    public int projectileCounter = 120;

    /**
     * This method is constructor of Player class.
     * @param gp GamePanel object.
     * @param keyH KeyHandler object.
     */
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        speed = 5;
        direction = "down";
        hitBox = new Rectangle(8,12, 30, 30);
        this.gp = gp;
        this.keyH = keyH;
        worldX = 48;
        worldY = 48;
        solidAreaDefaultX = hitBox.x;
        solidAreaDefaultY = hitBox.y;

        attackArea.width = 36;
        attackArea.height = 36;
        projectile = new OBJ_Fireball(gp);

        getPlayerImage();
    }

    /**
     * This method is for retrieving player image that can then be painted on the screen.
     */
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

    /**
     * This method is for updating player position on key press and animation every 10ms.
     */
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

            else {
                direction = "right";
            }

            // Check tile collision
            collisionDetected = false;
            gp.collisionChecker.checkTile(this);

            // Check spaceship part collision
            int spaceshipPartIndex = gp.collisionChecker.checkSpaceshipPart(this, true);
            pickUpSpaceshipPart(spaceshipPartIndex);

            // Check diamond collision
            int diamondIndex = gp.collisionChecker.checkDiamond(this, true);
            pickUpDiamond(diamondIndex);

            // Check blackhole collision
            int blackholeIndex = gp.collisionChecker.checkBlackhole(this, true);
            collideBlackhole(blackholeIndex);

            //Check Door collision
            int doorIndex = gp.collisionChecker.checkWinningDoor(this, true);
            collideOpenedDoor(doorIndex);

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

            if (score < 0){ // Game over if score is less than 0
                gp.currentGameState = gp.loseState;
            }
        }

        if(gp.keyH.spacePressed && !projectile.alive && projectileCounter > 90) {
            projectileCounter = 0;
            // Set default coordinates, direction, and user
            projectile.set(worldX, worldY, direction, true, this);

            // Add it to the list
            gp.projectileList.add(projectile);

            gp.playSE(7);

        }
        projectileCounter++;

        // Cooldown for player getting hit
        if (invincible){
            invincibleCounter++;
            if(invincibleCounter > 60) { // Removes invincibility after one second
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    /**
     * This method is for collecting Spaceship.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void pickUpSpaceshipPart(int index) {

        // If index is 999, character haven't collided with any object
        if (index != 999) {
            gp.playSE(4);
            score += 300;
            partsCollected++;
            gp.spaceshipPart[index] = null;
            if (partsCollected == 2) {
                gp.closedDoor[0] = null;
            }
        }
    }

    /**
     * This method is for collecting Diamond.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void pickUpDiamond(int index) {

        // If index is 999, character haven't collided with any object
        if (index != 999) {
            gp.playSE(1);
            score += 500;
            gp.diamond.remove(index);
        }
    }

    /**
     * This method is for updating scores when player collided with Blackhole.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void collideBlackhole(int index) {
        if (index != 999) {
            if (!invincible){
                invincible = true;
            }
            gp.playSE(6);
            score -= 300;
            gp.blackhole[index] = null;
        }
    }

    /**
     * This method is for setting reaction, when player collided with OpenedDoor.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void collideOpenedDoor(int index) {
        if (index != 999) {
            if (partsCollected == 2) {
                gp.currentGameState = gp.winState;

                int currentTopScore = -1;

                try (BufferedReader buffer = new BufferedReader(new FileReader("topScore.txt"))) {
                    String temp = buffer.readLine();
                    currentTopScore = Integer.parseInt(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (score > currentTopScore) {
                    currentTopScore = score;
                }

                try {
                    PrintWriter writer = new PrintWriter("topScore.txt", StandardCharsets.UTF_8);
                    writer.println(currentTopScore);
                    writer.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            gp.playSE(8);
        }
    }

    /**
     * This method is for setting damage, when player collided with Alien.
     * @param i If index is 999, character haven't collided with any object.
     */
    public void damageAlien(int i) {
        if (i != 999) {
            if(!gp.alien[i].invincible) {
                gp.playSE(5);
                gp.alien[i].score -= 1;
                gp.alien[i].invincible = true;
                gp.alien[i].damageReaction();

                if(gp.alien[i].score < 0) {
                    gp.alien[i].dying = true;
                }
            }
        }
    }

    public void playerReset() {
        worldX = 48;
        worldY = 48;
        direction = "down";
        partsCollected = 0;
        score = 0;
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

        if(invincible) { // Makes player transparent when they are invincible
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);

        g2.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)));
    }

}
