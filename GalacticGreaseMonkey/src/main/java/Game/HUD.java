package Game;

import java.awt.*;


public class HUD {
    GamePanel gp;
    Font arial_40;
    public HUD(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2){
        this.gp = gp;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawString("Score" + "=" + gp.player.hasKey, 20, 40);

    }
}
