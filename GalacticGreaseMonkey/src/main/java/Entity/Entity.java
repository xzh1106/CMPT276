package Entity;

import Position.Position;

import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;

    protected int score = 0;

    protected Position position = new Position();

    //Sprite animations stored in these variables
    protected BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
}