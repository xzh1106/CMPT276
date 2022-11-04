package Object;

import Entity.Entity;
import Game.GamePanel;

public class OBJ_SpaceshipPart extends Entity {

    public OBJ_SpaceshipPart(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup("/object/key");
        collision = false;
    }
}