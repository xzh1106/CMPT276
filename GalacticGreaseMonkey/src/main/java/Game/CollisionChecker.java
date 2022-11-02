package Game;

import Entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity e) {
        int leftX = e.worldX + e.hitBox.x;
        int rightX = e.worldX + e.hitBox.x + e.hitBox.width;
        int topY = e.worldY + e.hitBox.y;
        int bottomY = e.worldY + e.hitBox.y + e.hitBox.height;

        int leftCol = leftX/gp.tileSize;
        int rightCol = rightX/gp.tileSize;
        int topRow = topY/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileA, tileB;
        switch (e.direction) {
            case "up":
                topRow = (topY - e.speed)/gp.tileSize;
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[rightCol][topRow];
                if(gp.tileManager.tile[tileA].collision == true ||
                        gp.tileManager.tile[tileB].collision == true) {
                    e.collisionDetected = true;
                }
                break;
            case "down":
                bottomRow = (bottomY + e.speed)/gp.tileSize;
                tileA = gp.tileManager.mapTileNum[leftCol][bottomRow];
                tileB = gp.tileManager.mapTileNum[rightCol][bottomRow];
                if(gp.tileManager.tile[tileA].collision == true ||
                        gp.tileManager.tile[tileB].collision == true) {
                    e.collisionDetected = true;
                }
                break;
            case "left":
                leftCol = (leftX - e.speed)/gp.tileSize;
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[leftCol][bottomRow];
                if(gp.tileManager.tile[tileA].collision == true ||
                        gp.tileManager.tile[tileB].collision == true) {
                    e.collisionDetected = true;
                }
                break;
            case "right":
                rightCol = (rightX + e.speed)/gp.tileSize;
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
