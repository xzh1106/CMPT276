package Object;

import Game.GamePanel;

public class OBJ_Winning extends GameObject {

    public OBJ_Winning(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setup("/WinChest/door");
        collision = false;
    }
}
