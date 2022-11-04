package Object;

import Entity.Entity;
import Game.GamePanel;

public class OBJ_Diamond extends Entity {

    GamePanel gp;
    public OBJ_Diamond(GamePanel gp) {
        super(gp);
//        gp = this.gp;
        name = "Diamond";
        down1 = setup("/object/coin_bronze");
        collision = false;
    }
}
