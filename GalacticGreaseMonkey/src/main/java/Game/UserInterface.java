package Game;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import Objects.*;

/**
 * This class is for setting UserInterface.
 * @author Jason
 * @author Ryan
 */
public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font monospaced_60_Plain;
    Font monospaced_40_Plain;
    public int commandNumTitleScreen = 0;
    public int commandNumPauseScreen = 0;
    public int commandLevel = 0;

    /**
     * This method is constructor of UserInterface class.
     * @param gp GamePanel object.
     */
    public UserInterface(GamePanel gp) {
        this.gp = gp;

        //macOS bug when "Times" font not installed (first pause message is slightly delayed)
        monospaced_60_Plain = new Font("Monospaced", Font.PLAIN, 60);
        monospaced_40_Plain = new Font("Monospaced", Font.PLAIN, 40);
    }

    /**
     * This method is for setting properties of UserInterface.
     * @param g2 GamePanel object.
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(monospaced_60_Plain);
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
        String message = "Galactic Grease Monkey";

        int currentTopScore = 0;

        String fileToDisplay = "topScore.txt";
        if (gp.userInterface.commandLevel == 0) {
            fileToDisplay = "topScore.txt";
        }
        else if (gp.userInterface.commandLevel == 1) {
            fileToDisplay = "topScoreMed.txt";
        }
        else if (gp.userInterface.commandLevel == 2) {
            fileToDisplay = "topScoreHard.txt";
        }

        try{
            File file = new File(fileToDisplay);
            if(file.createNewFile()){
                PrintWriter writer = new PrintWriter(fileToDisplay, StandardCharsets.UTF_8);
                writer.println(0);
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(fileToDisplay));
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

        // Game level
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2 + 75;
        String level = "LEVEL: ";
        g2.drawString(level, x - 300, y + 75);
        g2.setFont(monospaced_40_Plain);
        switch (commandLevel) {
            case 0 -> {
                g2.setColor(Color.green);
                g2.drawString("Baby", x - 60, y + 75);
            }
            case 1 -> {
                g2.setColor(Color.blue);
                g2.drawString("Soldier", x - 60, y + 75);
            }
            case 2 -> {
                g2.setColor(Color.red);
                g2.drawString("Commander", x - 60 , y + 75);
            }
        }

        //Monkey image
        g2.drawImage(gp.player.down1, x + 200, y, gp.tileSize*2, gp.tileSize*2, null);
        g2.setFont(monospaced_60_Plain);
        g2.setColor(Color.orange);


        // Menu

        gp.setFont(g2.getFont().deriveFont(Font.BOLD,40F));

        String menu1 = "New Game";
        x = XCentreText(menu1);
        y += gp.tileSize * 4;
        g2.drawString(menu1, x, y);
        if (commandNumTitleScreen == 0) {
//            g2.drawImage(gp.openedDoor[0].down1, x + gp.tileSize * 6 + 20, y - 40, gp.tileSize, gp.tileSize, null);
            g2.drawString("> ", x - gp.tileSize, y);
        }

        String menu2 = "Exit";
        x = XCentreText(menu2);
        y += gp.tileSize * 2;
        g2.drawString(menu2, x, y);
        if (commandNumTitleScreen == 1) {
//            g2.drawImage(gp.blackhole[0].down1, x + gp.tileSize * 3 + 20, y - 40, gp.tileSize, gp.tileSize, null);
            g2.drawString("> ", x - gp.tileSize, y);
        }
    }

    /**
     * This method is for setting pause in game.
     */
    public void showPauseScreen() {
        String message = "PAUSED";

        //determine where message should go
        int x = XCentreText(message);
        int y = gp.screenHeight/2 - 100;

        //draw message
        drawMainMessage(message, Color.gray, Color.white, x, y);

        String option1 = "Resume";
        String option2 = "Restart";
        String option3 = "Exit";

        // Resume Location and UI
        x = XCentreText(option1);
        y += gp.titleState * 4 + 70;
        g2.drawString(option1, x, y);
        if (commandNumPauseScreen == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Restart Location and UI
        x = XCentreText(option2);
        y += 75;
        g2.drawString(option2, x, y);
        if (commandNumPauseScreen == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Exit Location and UI
        x = XCentreText(option3);
        y += 75;
        g2.drawString(option3, x, y);
        if (commandNumPauseScreen == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

    }

    /**
     * This method is for Show player defeat in game.
     */
    public void showLostScreen() {
        String message = "GAME OVER";
        String tryAgainMessage = "Press Enter to Play Again...";
        //find centre of the message

        //determine where message should go
        int x = XCentreText(message);
        int y = gp.screenHeight/2;

        int x1 = XCentreText(tryAgainMessage);
        int y1 = gp.screenHeight/2 + 75;

        //draw message
//        g2.setColor(Color.black);
//        g2.drawString(message, x+5, y+5);
//        g2.setColor(Color.red);
//        g2.drawString(message, x, y);
        drawMainMessage(message, Color.BLACK, Color.RED, x, y);

        //tryAgain message
//        g2.setColor(Color.black);
//        g2.drawString(tryAgainMessage, x1+5, y1+5);
//        g2.setColor(Color.orange);
//        g2.drawString(tryAgainMessage, x1, y1);
        drawMainMessage(message, Color.BLACK, Color.ORANGE, x, y);
    }

    /**
     * This method is for show player victory  in game.
     */
    public void showWinScreen() {
        String message = "VICTORY";
        String continueMessage = "Press Enter to Continute";
        //Find centre of the message

        //Where message should go
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

    private void drawMainMessage(String message, Color color1, Color color2, int x, int y) {
        g2.setColor(color1);
        g2.drawString(message, x + 5, y + 5);
        g2.setColor(color2);
        g2.drawString(message, x, y);
    }

    private void drawMenuOption(String message, Color color, int x, int y) {

    }
}
