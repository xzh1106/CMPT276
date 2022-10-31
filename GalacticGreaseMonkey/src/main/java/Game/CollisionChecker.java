package Game;

import Entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity e) {
        int leftX = e.position.getX() + e.hitBox.x;
        int rightX = e.position.getX() + e.hitBox.x + e.hitBox.width;
        int topY = e.position.getY() + e.hitBox.y;
        int bottomY = e.position.getY() + e.hitBox.y + e.hitBox.height;

        int leftCol = leftX/gp.tileSize;
        int rightCol = rightX/gp.tileSize;
        int topRow = topY/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileA, tileB;
        switch (e.position.getDirection()) {
            case "up":
                topRow = (topY - e.position.getSpeed())/gp.tileSize;
                tileA = gp.tileManager.mapTiles[leftCol][topRow];
                tileB = gp.tileManager.mapTiles[rightCol][topRow];
                if(gp.tileManager.tiles[tileA].collision == true ||
                        gp.tileManager.tiles[tileB].collision == true) {
                    e.collsionDetected = true;
                }
                break;
            case "down":
                bottomRow = (bottomY + e.position.getSpeed())/gp.tileSize;
                tileA = gp.tileManager.mapTiles[leftCol][bottomRow];
                tileB = gp.tileManager.mapTiles[rightCol][bottomRow];
                if(gp.tileManager.tiles[tileA].collision == true ||
                        gp.tileManager.tiles[tileB].collision == true) {
                    e.collsionDetected = true;
                }
                break;
            case "left":
                leftCol = (leftX - e.position.getSpeed())/gp.tileSize;
                tileA = gp.tileManager.mapTiles[leftCol][topRow];
                tileB = gp.tileManager.mapTiles[leftCol][bottomRow];
                if(gp.tileManager.tiles[tileA].collision == true ||
                        gp.tileManager.tiles[tileB].collision == true) {
                    e.collsionDetected = true;
                }
                break;
            case "right":
                rightCol = (rightX + e.position.getSpeed())/gp.tileSize;
                tileA = gp.tileManager.mapTiles[rightCol][topRow];
                tileB = gp.tileManager.mapTiles[rightCol][bottomRow];
                if(gp.tileManager.tiles[tileA].collision == true ||
                        gp.tileManager.tiles[tileB].collision == true) {
                    e.collsionDetected = true;
                }
                break;
        }
    }
}
