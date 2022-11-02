package Game;

import java.awt.*;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40_Plain, arial_80_Bold;

    //setting fonts
    public UserInterface(GamePanel gp) {
        this.gp = gp;
        arial_40_Plain = new Font("Helvetica", Font.PLAIN, 40);
        arial_80_Bold = new Font("Helvetica", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40_Plain);
        g2.setColor(Color.white);

        //if game is paused show pause screen
        if (gp.currentGameState == gp.pausedState) {
            showPauseScreen();
        }
    }

    public void showPauseScreen() {
        String message = "PAUSED";
        //find centre of the message
        int messageCentre = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();

        //determine where message should go
        int x = gp.screenWidth/2 - messageCentre/2;
        int y = gp.screenHeight/2;

        //draw message
        g2.drawString(message, x, y);
    }
}
