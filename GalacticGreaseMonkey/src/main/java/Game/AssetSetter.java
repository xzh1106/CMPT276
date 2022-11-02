package Game;
import Object.OBJ_Key;
import Entity.*;
public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
    }

    public void setAlien() {
        gp.alien[0] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[0].worldX = 20 * gp.tileSize; // Set location for obj on map
        gp.alien[0].worldY = 20 * gp.tileSize;

        gp.alien[1] = new Alien(gp);
        gp.alien[1].worldX = 30 * gp.tileSize;
        gp.alien[1].worldY = 30 * gp.tileSize;
    }
}
