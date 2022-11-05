package Object;

import Entity.Projectile;
import Game.GamePanel;

public class OBJ_Fireball extends Projectile {

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        score = maxLife;
        attack = 1;
        alive = false;
        getImage();

    }

    public void getImage() {
        up1 = setup("/projectile/fireball_up_1");
        up2 = setup("/projectile/fireball_up_2");
        down1 = setup("/projectile/fireball_down_1");
        down2 = setup("/projectile/fireball_down_2");
        right1 = setup("/projectile/fireball_right_1");
        right2 = setup("/projectile/fireball_right_2");
        left1 = setup("/projectile/fireball_left_1");
        left2 = setup("/projectile/fireball_left_2");
    }
}
