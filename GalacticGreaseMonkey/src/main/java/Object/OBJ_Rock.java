package Object;

import Entity.Entity;
import Game.GamePanel;

public class OBJ_Rock extends Entity {

    public OBJ_Rock(GamePanel gp) {
        super(gp);

        name = "Rock";
        down1 = setup("/object/rock");
        collision = true;
    }
}
