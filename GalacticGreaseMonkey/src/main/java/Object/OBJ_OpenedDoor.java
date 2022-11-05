package Object;

import Game.GamePanel;

public class OBJ_OpenedDoor extends GameObject{
    public OBJ_OpenedDoor(GamePanel gp) {
        super(gp);
        name = "Opened Door";
        down1 = setup("/WinChest/door_open");
        collision = false;
    }

}
