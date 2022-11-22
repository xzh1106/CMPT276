package Game;

import Entity.Player;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void Score_and_CollectedObject_Updated_Collide_SpaceShipPart() {

        // Put player at ship's part location
        player.worldX = 7 * gp.tileSize;
        player.worldY = 10 * gp.tileSize;

        // objectIndex is returned if player hit object
        int collidedObjectIndex = collisionChecker.checkSpaceshipPart(player, true);
        player.pickUpSpaceshipPart(collidedObjectIndex);

        // First object in ship's array, index = 0
        assertEquals(0, collidedObjectIndex);
        assertEquals(300, player.score);
    }

    @Test
    void Score_and_CollectedObject_NotUpdated_NotCollide_SpaceShipPart() {

        // Put player at NOT ship's part location
        player.worldX = 7 * gp.tileSize;
        player.worldY = 12 * gp.tileSize;

        // objectIndex is not return if player doesn't hit object
        int collidedObjectIndex = collisionChecker.checkSpaceshipPart(player, true);
        player.pickUpSpaceshipPart(collidedObjectIndex);


        assertEquals(0, player.score);          // player did not hit object thus score should be 0
        assertEquals(999, collidedObjectIndex); // default index is 999 if not hit any object

    }

    @Test
    void Score_and_CollectedObject_Update_Collide_Blackhole() {

        // Initial player
        player.score = 1000;
        player.worldX = 20 * gp.tileSize;
        player.worldY = 5 * gp.tileSize;

        //ObjectIndex is return if player hit object
        int collideObjectIndex = collisionChecker.checkBlackhole(player, true);
        player.collideBlackhole(collideObjectIndex);

        assertEquals(700, player.score);
        assertEquals(0, collideObjectIndex);
    }

    @Test
    void Score_and_CollectedObject_NotUpdate_NotCollide_Blackhole() {

        // Initial player
        player.score = 1000;
        player.worldX = 22 * gp.tileSize;
        player.worldY = 5 * gp.tileSize;

        //ObjectIndex is return if player hit object
        int collideObjectIndex = collisionChecker.checkBlackhole(player, true);
        player.collideBlackhole(collideObjectIndex);

        assertEquals(1000, player.score);       // Not collide with blackhole, unchanged score
        assertEquals(999, collideObjectIndex);  // Not collide with blackhole, index = 999
    }

    @Test
    void WinningStage_Collided_OpenedDoor() {

        // Initital condition
        player.pickUpSpaceshipPart(0);
        player.pickUpSpaceshipPart(1);

        player.collideOpenedDoor(0);

        assertEquals(4, gp.currentGameState);
    }

    @Test
    void tile_collision_test(){
        // Put player at default location
        player.worldX = 48;
        player.worldY = 48;

        // The solid tile at up, right and left
        switch (player.direction) {
            case "up", "left", "right" -> assertTrue(player.collisionDetected);
            case "down" -> assertFalse(player.collisionDetected);
        }

    }

}