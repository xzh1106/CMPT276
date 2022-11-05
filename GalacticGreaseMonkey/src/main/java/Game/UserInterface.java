package Game;

import java.awt.*;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40_Plain, arial_80_Bold;

    //setting fonts
    public UserInterface(GamePanel gp) {
        this.gp = gp;

        //MacOs bug when "Times" font not installed (first pause message is slightly delayed)
        arial_40_Plain = new Font("Ariel", Font.PLAIN, 60);
        arial_80_Bold = new Font("Times New Roman", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40_Plain);
        g2.setColor(Color.white);

        //if game is paused show pause screen
        if (gp.currentGameState == gp.pausedState) {
            showPauseScreen();
        }

        if (gp.currentGameState == gp.loseState) {
            showLostScreen();
        }

        if (gp.currentGameState == gp.winState) {
            showWinScreen();
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
        g2.setColor(Color.gray);
        g2.drawString(message, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(message, x, y);
    }

    public void showLostScreen() {
        String message = "GAME OVER";
        //find centre of the message
        int messageCentre = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();

        //determine where message should go
        int x = gp.screenWidth/2 - messageCentre/2;
        int y = gp.screenHeight/2;

        //draw message
        g2.setColor(Color.black);
        g2.drawString(message, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(message, x, y);
    }

    public void showWinScreen() {
        String message = "VICTORY";
        //Find centre of the message
        int messageCentre = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();

        //Where message should go
        int x = gp.screenWidth/2 - messageCentre/2;
        int y = gp.screenHeight/2;

        //draw message
        g2.setColor(Color.red);
        g2.drawString(message, x+5, y+5);
        g2.setColor(Color.orange);
        g2.drawString(message, x, y);
    }
}
