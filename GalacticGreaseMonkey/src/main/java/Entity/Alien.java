package Entity;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
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

    }

    public void getPlayerImage() {
        up1 = setup("/Monster/greenslime_down_1.png");
        up2 = setup("/Monster/greenslime_down_2.png");
        down1 = setup("/Monster/greenslime_down_1.png");
        down2 = setup("/Monster/greenslime_down_2.png");
        right1 = setup("/Monster/greenslime_down_1.png");
        right2 = setup("/Monster/greenslime_down_2.png");
        left1 = setup("/Monster/greenslime_down_1.png");
        left2 = setup("/Monster/greenslime_down_2.png");
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick a number from 1-100

            if (i <= 25) {
                direction = "up";
            }
        }
    }
}
