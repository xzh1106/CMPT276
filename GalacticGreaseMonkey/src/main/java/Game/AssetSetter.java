package Game;
import Object.*;
import Entity.*;
public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key(gp); // Make a key object and save into obj array
        gp.obj[0].worldX = 7 * gp.tileSize; // Set location for obj on map
        gp.obj[0].worldY = 10 * gp.tileSize;

    }

    public void setBlackhole() {

        gp.blackhole[0] = new OBJ_Blackhole(gp); // Make a key object and save into obj array
        gp.blackhole[0].worldX = 20 * gp.tileSize; // Set location for obj on map
        gp.blackhole[0].worldY = 5 * gp.tileSize;

    }

    public void setAlien() {
        gp.alien[0] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[0].worldX = 20 * gp.tileSize; // Set location for obj on map
        gp.alien[0].worldY = 14 * gp.tileSize;

        gp.alien[1] = new Alien(gp);
        gp.alien[1].worldX = 12 * gp.tileSize;
        gp.alien[1].worldY = 12 * gp.tileSize;
    }
}
