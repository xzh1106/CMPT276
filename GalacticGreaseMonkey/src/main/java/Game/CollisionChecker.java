package Game;

import Entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity e) {
        //get top left coords of entity hitbox
        int leftX = e.worldX + e.hitBox.x;
        int topY = e.worldY + e.hitBox.y;

        //get bottom right coords of entity hitbox
        int rightX = e.worldX + e.hitBox.x + e.hitBox.width;
        int bottomY = e.worldY + e.hitBox.y + e.hitBox.height;

        //get block to the left
        int leftCol = leftX/gp.tileSize;
        int rightCol = rightX/gp.tileSize;
        int topRow = topY/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileA, tileB;
        switch (e.direction) {
            case "up":
                //"predict" which tile player will be in the next 60 frames when UP key is pressed
                topRow = (topY - e.speed)/gp.tileSize;

                /*
                    if the tiles above player's top left OR top right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[rightCol][topRow];
                if(gp.tileManager.tile[tileA].collision == true ||
                        gp.tileManager.tile[tileB].collision == true) {
                    e.collisionDetected = true;
                }
                break;
            case "down":
                //"predict" which tile player will be in the next 60 frames when DOWN key is pressed
                bottomRow = (bottomY + e.speed)/gp.tileSize;

                /*
                    if the tiles below player's bottom left OR bottom right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][bottomRow];
                tileB = gp.tileManager.mapTileNum[rightCol][bottomRow];
                if(gp.tileManager.tile[tileA].collision == true ||
                        gp.tileManager.tile[tileB].collision == true) {
                    e.collisionDetected = true;
                }
                break;
            case "left":
                //"predict" which tile player will be in the next 60 frames when LEFT key is pressed
                leftCol = (leftX - e.speed)/gp.tileSize;

                /*
                    if the tiles to the left of player's top left OR bottom left hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[leftCol][bottomRow];
                if(gp.tileManager.tile[tileA].collision == true ||
                        gp.tileManager.tile[tileB].collision == true) {
                    e.collisionDetected = true;
                }
                break;
            case "right":
                //"predict" which tile player will be in the next 60 frames when RIGHT key is pressed
                rightCol = (rightX + e.speed)/gp.tileSize;

                /*
                    if the tiles to the right of player's top right OR bottom right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[rightCol][topRow];
                tileB = gp.tileManager.mapTileNum[rightCol][bottomRow];
                if(gp.tileManager.tile[tileA].collision == true ||
                        gp.tileManager.tile[tileB].collision == true) {
                    e.collisionDetected = true;
                }
                break;
        }
    }
}
