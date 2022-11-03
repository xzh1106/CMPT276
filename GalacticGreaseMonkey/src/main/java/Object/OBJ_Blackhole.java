package Object;

import Entity.Entity;
import Game.GamePanel;

public class OBJ_Blackhole extends Entity {

    public OBJ_Blackhole(GamePanel gp) {
        super(gp);

        name = "Blackhole";
        down1 = setup("/object/blackhole");
        collision = false;
    }
}
