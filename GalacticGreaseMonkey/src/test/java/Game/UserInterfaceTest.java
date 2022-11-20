package Game;

import Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    GamePanel gp;
    KeyHandler keyHandler;
    Player player;

    @BeforeEach
    public void setUpGamePanel() {
        gp = new GamePanel();
        gp.setupGame();

        keyHandler = new KeyHandler(gp);
        player = new Player(gp, keyHandler);
    }

    @Test
    void showPauseScreenWhenPauseIsPressed() {
        //simulating key press
        KeyEvent key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_P,'P');
        gp.getKeyListeners()[0].keyPressed(key);

        // 2 indicates paused state
        assertEquals(2, gp.currentGameState);
    }

    @Test
    void showLostScreenWhenPlayerScoreIsNegative() {
        //player moves while score is less than 0 makes them lose
        player.score = -1;
        keyHandler.upPressed = true;
        player.update();

        // 3 indicates losing state
        assertEquals(3, gp.currentGameState);
    }
    @Test
    void continueInPlayStateWhenPlayerScoreIsNotNegative() {
        //player moves while score is less than 0 makes them lose
        player.score = 0;
        keyHandler.upPressed = true;
        player.update();

        // 3 indicates losing state
        assertEquals(1, gp.currentGameState);
    }

    @Test
    void showWinScreenWhenPlayerHasAllPartsAndCollidesWithDoor() {
        //player has all spaceship parts and collides with door
        player.partsCollected = 2;
        player.collideOpenedDoor(1);

        // 4 indicates win state
        assertEquals(4, gp.currentGameState);
    }
    @Test
    void continueInPlayStateWhenPlayerDoesntHaveAllPartsAndCollidesWithDoor() {
        //player has all spaceship parts and collides with door
        player.partsCollected = 1;
        player.collideOpenedDoor(1);

        // 4 indicates win state
        assertEquals(1, gp.currentGameState);
    }
}