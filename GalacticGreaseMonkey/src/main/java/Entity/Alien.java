package Entity;

import Game.GamePanel;

import java.util.Random;

public class Alien extends Entity {

    public Alien(GamePanel gp) {
        super(gp);
        score = 400;
        speed = 1;

        hitBox.x = 3;
        hitBox.y = 18;
        hitBox.width = 42;
        hitBox.height = 30;
        solidAreaDefaultX = hitBox.x;
        solidAreaDefaultY = hitBox.y;
        getImage();

    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1");
        up2 = setup("/monster/greenslime_down_2");
        down1 = setup("/monster/greenslime_down_1");
        down2 = setup("/monster/greenslime_down_2");
        right1 = setup("/monster/greenslime_down_1");
        right2 = setup("/monster/greenslime_down_2");
        left1 = setup("/monster/greenslime_down_1");
        left2 = setup("/monster/greenslime_down_2");
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick a number from 1-100

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
