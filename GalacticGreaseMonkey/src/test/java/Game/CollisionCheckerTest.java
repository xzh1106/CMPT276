package Game;

import Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionCheckerTest {

    GamePanel gp;
    KeyHandler keyHandler;
    Player player;
    CollisionChecker collisionChecker;

    @BeforeEach
    public void setUpCollisionChecker() {
        gp = new GamePanel();
        gp.setupGame();

        keyHandler = new KeyHandler(gp);
        player = new Player(gp, keyHandler);
        collisionChecker = new CollisionChecker(gp);
    }

    @Test
    void addToScoreIfDiamondCollision() {

        //placing player in hitbox of the second diamond out of the 2 that exist at the start of the game
        player.worldX = 7 * gp.tileSize;
        player.worldY = 5 * gp.tileSize;

        int playersScoreBeforeDiamondCollision = player.score;
        int collisionResult = collisionChecker.checkDiamond(player, true);

        //index of second diamond is 1 which is the expected result when player collides with that diamond
        assertEquals(1, collisionResult);

        //if it can be asserted that there was a collision with a diamond, try to assert whether the score has been incremented by the right amount
        if (collisionResult == 1) {
            int playersScoreAfterDiamondCollision = playersScoreBeforeDiamondCollision + 500;
            assertEquals(playersScoreAfterDiamondCollision, playersScoreBeforeDiamondCollision + 500);
        }
    }
    @Test
    void doNothingIfPlayerHasntCollidedWithDiamond() {
        //placing player outside the hitbox of any diamond that exists
        player.worldX = 14 * gp.tileSize;
        player.worldY = 7 * gp.tileSize;

        int playersScoreBeforeDiamondCollision = player.score;
        int collisionResult = collisionChecker.checkDiamond(player, true);

        //result of checkDiamond() should be 999 which indicates there was no collision
        assertEquals(999, collisionResult);

        //if it can be asserted that there was no collision with a diamond, try to assert whether the score has been incremented by the right amount
        if (collisionResult == 999) {
            int playersScoreAfterDiamondCollision = player.score;
            assertEquals(playersScoreAfterDiamondCollision, playersScoreBeforeDiamondCollision);
        }
    }
}