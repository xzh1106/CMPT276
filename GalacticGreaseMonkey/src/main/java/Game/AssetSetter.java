package Game;
import Object.OBJ_Key;
public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key(); // Make a key object and save into obj array
        gp.obj[0].worldX = 7 * gp.tileSize; // Set location for obj on map
        gp.obj[0].worldY = 10 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 14 * gp.tileSize;
        gp.obj[1].worldY = 20 * gp.tileSize;
    }

}
