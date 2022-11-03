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
            case "up" -> {
                //"predict" which tile player will be in the next 60 frames when UP key is pressed
                topRow = (topY - e.speed) / gp.tileSize;

                /*
                    if the tiles above player's top left OR top right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[rightCol][topRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
            case "down" -> {
                //"predict" which tile player will be in the next 60 frames when DOWN key is pressed
                bottomRow = (bottomY + e.speed) / gp.tileSize;

                /*
                    if the tiles below player's bottom left OR bottom right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][bottomRow];
                tileB = gp.tileManager.mapTileNum[rightCol][bottomRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
            case "left" -> {
                //"predict" which tile player will be in the next 60 frames when LEFT key is pressed
                leftCol = (leftX - e.speed) / gp.tileSize;

                /*
                    if the tiles to the left of player's top left OR bottom left hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[leftCol][bottomRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
            case "right" -> {
                //"predict" which tile player will be in the next 60 frames when RIGHT key is pressed
                rightCol = (rightX + e.speed) / gp.tileSize;

                /*
                    if the tiles to the right of player's top right OR bottom right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[rightCol][topRow];
                tileB = gp.tileManager.mapTileNum[rightCol][bottomRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
        }
    }

    public int checkSpaceshipPart(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i  < gp.spaceshipPart.length; i++) {
            if(gp.spaceshipPart[i] != null) {

                // Get entity's solid area position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's solid area position
                gp.spaceshipPart[i].hitBox.x = gp.spaceshipPart[i].worldX + gp.spaceshipPart[i].hitBox.x;
                gp.spaceshipPart[i].hitBox.y = gp.spaceshipPart[i].worldY + gp.spaceshipPart[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gp.spaceshipPart[i].hitBox)) {
                    if (gp.spaceshipPart[i].collision) {
                        entity.collisionDetected = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                gp.spaceshipPart[i].hitBox.x = gp.spaceshipPart[i].solidAreaDefaultX;
                gp.spaceshipPart[i].hitBox.y = gp.spaceshipPart[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public int checkBlackhole(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i  < gp.blackhole.length; i++) {
            if(gp.blackhole[i] != null) {

                // Get entity's solid area position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's solid area position
                gp.blackhole[i].hitBox.x = gp.blackhole[i].worldX + gp.blackhole[i].hitBox.x;
                gp.blackhole[i].hitBox.y = gp.blackhole[i].worldY + gp.blackhole[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gp.blackhole[i].hitBox)) {
                    if (gp.blackhole[i].collision) {
                        entity.collisionDetected = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                gp.blackhole[i].hitBox.x = gp.blackhole[i].solidAreaDefaultX;
                gp.blackhole[i].hitBox.y = gp.blackhole[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // Entity collision
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i  < target.length; i++) {
            if(target[i] != null) {

                // Get entity's solid area position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's solid area position
                target[i].hitBox.x = target[i].worldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].worldY + target[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(target[i].hitBox)) {
                    if (target[i] != entity) {
                        entity.collisionDetected = true;
                        index = i;
                    }
                }
                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                target[i].hitBox.x = target[i].solidAreaDefaultX;
                target[i].hitBox.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity) {
        // Get entity's solid area position
        entity.hitBox.x = entity.worldX + entity.hitBox.x;
        entity.hitBox.y = entity.worldY + entity.hitBox.y;

        // Get object's solid area position
        gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;

        switch (entity.direction) {
            case "up" -> entity.hitBox.y -= entity.speed;
            case "down" -> entity.hitBox.y += entity.speed;
            case "left" -> entity.hitBox.x -= entity.speed;
            case "right" -> entity.hitBox.x += entity.speed;
        }
        if (entity.hitBox.intersects(gp.player.hitBox)) {
            if (gp.player != entity) {
                entity.collisionDetected = true;
            }
        }
        entity.hitBox.x = entity.solidAreaDefaultX;
        entity.hitBox.y = entity.solidAreaDefaultY;
        gp.player.hitBox.x = gp.player.solidAreaDefaultX;
        gp.player.hitBox.y = gp.player.solidAreaDefaultY;
    }
}
