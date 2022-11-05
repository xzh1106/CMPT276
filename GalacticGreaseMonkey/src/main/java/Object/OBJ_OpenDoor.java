package Object;

import Game.GamePanel;

public class OBJ_OpenDoor extends GameObject {

    public OBJ_OpenDoor(GamePanel gp) {
        super(gp);
        name = "Open Door";
        down1 = setup("/WinChest/door_opened");
        collision = false;
    }
}