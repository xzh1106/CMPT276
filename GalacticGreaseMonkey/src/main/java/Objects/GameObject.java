package Objects;

import Game.GamePanel;
import Game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is for setting object of game.
 * @author Jason
 */
public class GameObject {
    GamePanel gp;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle hitBox = new Rectangle(0,0, 50, 50);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public int timeSinceCreated = 0;
    public BufferedImage down1;

    /**
     * This method is constructor of GameObject class.
     * @param gp GamePanel object.
     */
    public GameObject (GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * This method is for drawing image.
     * @param g2 Graphics2D object.
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = down1;

        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}