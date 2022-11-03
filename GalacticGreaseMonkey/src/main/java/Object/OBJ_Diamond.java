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

//    public void diamondUpdate(int i) {
//
//    }
//
//    @Override
//    public void update() {
//        super.update();
//       this.timeSinceCreated++;
//        if (gp.diamond.get(i).timeSinceCreated > 240) {
//            gp.diamond.get(i).timeSinceCreated = 0;
//            gp.diamond.remove(i);
//        }
//    }
}
