package Entity;

import Game.GamePanel;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.score = this.maxLife;

    }

    public void update() {

        if (user == gp.player) {
            int alienIndex = gp.collisionChecker.checkEntity(this, gp.alien);
            gp.collisionChecker.checkTile(this);
            if (alienIndex != 999) {
                gp.player.damageAlien(alienIndex);
                alive = false;
            }
        }
        gp.collisionChecker.checkTile(this);
        if (collisionDetected){
            alive = false;
            collisionDetected = false;
        }
        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        score--;
        if (score <= 0) {
            alive = false;
        }

        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
