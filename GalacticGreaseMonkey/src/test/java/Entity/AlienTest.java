package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AlienTest {
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
    void playerOutOfRange(){
        player.worldX = 48;
        player.worldY = 48;

        Alien alien = new Alien(gp);
        alien.worldX = 20 * gp.tileSize;
        alien.worldY = 14 * gp.tileSize;

        alien.update();
        assertFalse(alien.aggressive);
    }

    @Test
    void playerInRange(){
        player.worldX = 48;
        player.worldY = 48;

        Alien alien = new Alien(gp);
        alien.worldX = 48;
        alien.worldY = 48 + 2 * gp.tileSize;

        alien.update();
        assertTrue(alien.aggressive);
    }
}
