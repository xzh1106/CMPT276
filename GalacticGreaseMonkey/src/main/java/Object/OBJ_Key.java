package Object;

import Entity.Entity;
import Game.GamePanel;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup("/object/key");
        collision = false;
    }
}
