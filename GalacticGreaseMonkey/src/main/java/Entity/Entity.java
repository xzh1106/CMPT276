package Entity;

import Position.Position;

import java.awt.image.BufferedImage;

public class Entity {
    public int score = 0;

    public Position position = new Position();

    //Sprite animations stored in these variables
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;

    //Keeping track of anima
    public int spriteCounter = 0;
    public int spriteNum = 1;
}