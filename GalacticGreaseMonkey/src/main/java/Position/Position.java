package Position;

public class Position {

    public Position() { }

    public Position(int x, int y, int speed, String direction) {
        setX(x);
        setY(y);
        setSpeed(speed);
        this.direction = direction;
    }

    private int x;
    private int y;
    private int speed;
    private String direction;

    public int getX() {
       return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
