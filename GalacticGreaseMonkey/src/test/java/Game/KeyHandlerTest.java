package Game;

import Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class KeyHandlerTest {

    GamePanel gp;
    KeyHandler keyHandler;
    Player player;

    @BeforeEach
    public void setUpKeyHandler() {
        gp = new GamePanel();
        gp.setupGame();

        keyHandler = new KeyHandler(gp);
        player = new Player(gp, keyHandler);
    }

    @Test
    void playerMovesDuringKeyPressIfInPlayState() {
        int playerYPosBeforeKeyPress = player.worldY;

        //simulating down key press
        KeyEvent key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_S,'S');
        gp.getKeyListeners()[0].keyPressed(key);

        keyHandler.keyPressed(key);
        player.update();

        //players position after 1 frame should be its old position plus the players move speed
        int playerYPosAfterKeyPress = player.worldY;

        //position should be "player.speed" amount of pixels more than before
        assertEquals(playerYPosBeforeKeyPress+player.speed, playerYPosAfterKeyPress);
    }
    @Test
    void playerDoesNotMoveDuringKeyPressIfNotInPlayState() {

        gp.currentGameState = 3; //some arbitrary game state that isn't the playing state
        gp.update();

        int playerYPosBeforeKeyPress = player.worldY;

        //simulating down key press
        KeyEvent key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_S,'S');
        gp.getKeyListeners()[0].keyPressed(key);

        keyHandler.keyPressed(key);
        gp.update();

        //players position should remain the same after key press
        int playerYPosAfterKeyPress = player.worldY;

        //position should stay the same since game is not in play state
        assertEquals(playerYPosBeforeKeyPress, playerYPosAfterKeyPress);
    }
}