package Entity;

import Game.GamePanel;

import java.awt.*;
import java.util.Random;

public class Alien extends Entity {
    boolean aggressive = false;

    public Alien(GamePanel gp) {
        super(gp);
        score = 1;
        speed = 2;
        type = 1;

        hitBox.x = 3;
        hitBox.y = 18;
        hitBox.width = 42;
        hitBox.height = 30;
        solidAreaDefaultX = hitBox.x;
        solidAreaDefaultY = hitBox.y;
        getImage();

    }

    public void getImage() {
        if (!onPath) {
            up1 = setup("/monster/greenslime_down_1");
            up2 = setup("/monster/greenslime_down_2");
            down1 = setup("/monster/greenslime_down_1");
            down2 = setup("/monster/greenslime_down_2");
            right1 = setup("/monster/greenslime_down_1");
            right2 = setup("/monster/greenslime_down_2");
            left1 = setup("/monster/greenslime_down_1");
            left2 = setup("/monster/greenslime_down_2");
        } else {
            up1 = setup("/monster/redslime_down_1");
            up2 = setup("/monster/redslime_down_2");
            down1 = setup("/monster/redslime_down_1");
            down2 = setup("/monster/redslime_down_2");
            right1 = setup("/monster/redslime_down_1");
            right2 = setup("/monster/redslime_down_2");
            left1 = setup("/monster/redslime_down_1");
            left2 = setup("/monster/redslime_down_2");
        }
    }

    public void update() {
        super.update();

        boolean collidedPlayer = gp.collisionChecker.checkPlayer(this);
        if (collidedPlayer) {
            if (!gp.player.invincible) {
                gp.player.score = -1;
            }
        }

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < 5) {
            onPath = true;
        }

        if (invincible) { // Invincibility for alien
            invincibleCounter++;
            if (invincibleCounter > 40) { // Removes invincibility after one second
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void setAction() {

        if (onPath) {
            if (!aggressive) {
                aggressive = true;
                getImage();
            }
            int goalCol = (gp.player.worldX + gp.player.hitBox.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.hitBox.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick a number from 1-100

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
        if (direction == "up"){
            worldY -= 5;
        }
        else if (direction == "down"){
            worldY += 5;
        }
        else if (direction == "left"){
            worldX -= 5;
        }
        else if (direction == "right"){
            worldX += 5;
        }
    }
}
