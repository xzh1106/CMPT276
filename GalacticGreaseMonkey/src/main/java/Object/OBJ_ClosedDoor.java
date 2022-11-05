package Object;

import Game.GamePanel;

public class OBJ_ClosedDoor extends GameObject {

    public OBJ_ClosedDoor(GamePanel gp) {
        super(gp);
        name = "Closed Door";
        down1 = setup("/WinChest/door");
        collision = false;
    }
}
