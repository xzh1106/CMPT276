package Object;

import Game.GamePanel;

public class OBJ_SpaceshipPart extends GameObject {

    public OBJ_SpaceshipPart(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/object/gear");
        collision = false;
    }
}
