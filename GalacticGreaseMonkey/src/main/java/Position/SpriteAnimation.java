package Position;

public class SpriteAnimation {

    //Determines how fast the animation will change
    //Increments by 1 each time player calls update() and a key is pressed
    public int spriteCounter;

    //Indicates which version of a movement to use (E.g. down1 or down2)
    public int spriteNumber;

    public SpriteAnimation() {
        spriteCounter = 0;
        spriteNumber = 1;
    }
}
