package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import Position.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.position = new Position(100, 100, 5, "down");

        hitBox = new Rectangle(8, 16, 32, 32);

        getPlayerImage();
    }

    //retrieve sprite images
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyUp1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyUp2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyDown1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyDown2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyRight1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyRight2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyLeft1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/monkeyLeft2.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //update player position on key press and animation every 10ms
    public void update() {
        // Handle WASD movement

        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if(keyH.upPressed) {
                this.position.setDirection("up");
//                int updatedY = position.getY() - position.getSpeed();
//                position.setY(updatedY);
            }

            else if(keyH.downPressed) {
                this.position.setDirection("down");
//                int updatedY = position.getY() + position.getSpeed();
//                position.setY(updatedY);
            }

            else if(keyH.leftPressed) {
                this.position.setDirection("left");
//                int updatedX = position.getX() - position.getSpeed();
//                position.setX(updatedX);
            }

            else if(keyH.rightPressed) {
                this.position.setDirection("right");
//                int updatedX = position.getX() + position.getSpeed();
//                position.setX(updatedX);
            }

            collsionDetected = false;
            gp.collisionChecker.checkTile(this);

            if (collsionDetected == false) {
                switch (position.getDirection()) {
                    case "up":
                        int updatedY = position.getY() - position.getSpeed();
                        position.setY(updatedY);
                        break;
                    case "down":
                        int updatedY1 = position.getY() + position.getSpeed();
                        position.setY(updatedY1);
                        break;
                    case "left":
                        int updatedX = position.getX() - position.getSpeed();
                        position.setX(updatedX);
                        break;
                    case "right":
                        int updatedX1 = position.getX() + position.getSpeed();
                        position.setX(updatedX1);
                        break;
                }
            }

            position.getSpriteAnimation().spriteCounter++;
            if (position.getSpriteAnimation().spriteCounter > 10) {
                if (position.getSpriteAnimation().spriteNumber == 1) {
                    position.getSpriteAnimation().spriteNumber = 2;
                }
                else if (position.getSpriteAnimation().spriteNumber == 2) {
                    position.getSpriteAnimation().spriteNumber = 1;
                }
                position.getSpriteAnimation().spriteCounter = 0;
            }
        }
    }

    //Display corresponding image based on key press
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch(position.getDirection()) {
            case "up":
                if (position.getSpriteAnimation().spriteNumber == 1) {
                    image = up1;
                }
                else if (position.getSpriteAnimation().spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (position.getSpriteAnimation().spriteNumber == 1) {
                    image = down1;
                }
                else if (position.getSpriteAnimation().spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (position.getSpriteAnimation().spriteNumber == 1) {
                    image = left1;
                }
                else if (position.getSpriteAnimation().spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (position.getSpriteAnimation().spriteNumber == 1) {
                    image = right1;
                }
                else if (position.getSpriteAnimation().spriteNumber == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, position.getX(), position.getY(), gp.tileSize, gp.tileSize, null);
    }

}
