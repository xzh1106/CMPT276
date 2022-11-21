package Game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is for setting UserInterface.
 * @author Jason
 * @author Ryan
 */
public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font monospaced_40_Plain, arial_80_Bold;
    public int commandNum = 0;

    /**
     * This method is constructor of UserInterface class.
     * @param gp GamePanel object.
     */
    public UserInterface(GamePanel gp) {
        this.gp = gp;

        //MacOs bug when "Times" font not installed (first pause message is slightly delayed)
        monospaced_40_Plain = new Font("Monospaced", Font.PLAIN, 60);
        arial_80_Bold = new Font("Times New Roman", Font.BOLD, 80);
    }

    /**
     * This method is for setting properties of UserInterface.
     * @param g2 GamePanel object.
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(monospaced_40_Plain);
        g2.setColor(Color.white);

        //Title state, when start the game
        if(gp.currentGameState == gp.titleState) {
            showTitleScreen();
        }

        //if game is paused show pause screen
        if (gp.currentGameState == gp.pausedState) {
            showPauseScreen();
        }

        //show lost screen when lose
        if (gp.currentGameState == gp.loseState) {
            showLostScreen();
        }

        //show win screen when have enough condition
        if (gp.currentGameState == gp.winState) {
            showWinScreen();
        }
    }

    /**
     * Show title screen when first started the game
     */
    public void showTitleScreen() {
//        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        g2.setColor(new Color(36,28,51));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        String message = "Alien Monkey";

        int currentTopScore = 0;

        try (BufferedReader buffer = new BufferedReader(new FileReader("src/main/resources/topScore.txt"))) {
            String temp = buffer.readLine();
            currentTopScore = Integer.parseInt(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String topScore = "Top Score: " + currentTopScore;
//        int messageCentre = XCentreText(message);

        //determine where message should go
//        int x = gp.screenWidth/2 - messageCentre/2;
        //for Title
        int x = XCentreText(message);
        int y = gp.screenHeight/2 - 200;

        //for top score
        int x1 = XCentreText(topScore);
        int y1 = gp.screenHeight/2 - 125;

        //Title message
        g2.setColor(Color.YELLOW);
        g2.drawString(message, x+3, y+3);
        g2.setColor(Color.ORANGE);
        g2.drawString(message, x, y);

        //top score message
        g2.setColor(Color.YELLOW);
        g2.drawString(topScore, x1+3, y1+3);
        g2.setColor(Color.ORANGE);
        g2.drawString(topScore, x1, y1);

        //Monkey image
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2 + 75;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        // Menu
        gp.setFont(g2.getFont().deriveFont(Font.BOLD,40F));

        String menu1 = "New Game";
        x = XCentreText(menu1);
        y += gp.tileSize * 4;
        g2.drawString(menu1, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        String menu2 = "Exit";
        x = XCentreText(menu2);
        y += gp.tileSize * 2;
        g2.drawString(menu2, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

    }

    /**
     * This method is for setting pause in game.
     */
    public void showPauseScreen() {
        String message = "PAUSED";
        //find centre of the message
        int messageCentre = XCentreText(message);

        //determine where message should go
//        int x = gp.screenWidth/2 - messageCentre/2;
        int x = XCentreText(message);
        int y = gp.screenHeight/2;

        //draw message
        g2.setColor(Color.gray);
        g2.drawString(message, x+5, y+5);
        g2.setColor(Color.white);
        g2.drawString(message, x, y);
    }

    /**
     * This method is for Show player defeat in game.
     */
    public void showLostScreen() {
        String message = "GAME OVER";
        String tryAgainMessage = "Press Enter to Play Again...";
        //find centre of the message
        int messageCentre = XCentreText(message);

        //determine where message should go
//        int x = gp.screenWidth/2 - messageCentre/2;
        int x = XCentreText(message);
        int y = gp.screenHeight/2;

        int x1 = XCentreText(tryAgainMessage);
        int y1 = gp.screenHeight/2 + 75;

        //draw message
        g2.setColor(Color.black);
        g2.drawString(message, x+5, y+5);
        g2.setColor(Color.red);
        g2.drawString(message, x, y);

        //tryAgain message
        g2.setColor(Color.black);
        g2.drawString(tryAgainMessage, x1+5, y1+5);
        g2.setColor(Color.orange);
        g2.drawString(tryAgainMessage, x1, y1);
    }

    /**
     * This method is for show player victory  in game.
     */
    public void showWinScreen() {
        String message = "VICTORY";
        String continueMessage = "Press Enter to Continute";
        //Find centre of the message
        int messageCentre = XCentreText(message);

        //Where message should go
//        int x = gp.screenWidth/2 - messageCentre/2;
        int x = XCentreText(message);
        int y = gp.screenHeight/2;

        int x1 = XCentreText(continueMessage);
        int y1 = gp.screenHeight/2 + 75;

        //draw message
        g2.setColor(Color.red);
        g2.drawString(message, x+5, y+5);
        g2.setColor(Color.orange);
        g2.drawString(message, x, y);

        //continute message
        g2.setColor(Color.black);
        g2.drawString(continueMessage, x1+5, y1+5);
        g2.setColor(Color.orange);
        g2.drawString(continueMessage, x1, y1);
    }

    private int XCentreText(String text) {
        return gp.screenWidth/2 - (((int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()) / 2);
    }
}
