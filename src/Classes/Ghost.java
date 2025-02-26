import java.util.Random;

public class Ghost {
    private int x;
    private int y;
    private Direction direction;
    private static final int MOVEMENT_SPEED = 5;
    private static final int COLLISION_THRESHOLD = 20;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = Direction.LEFT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void move() {
        Random random = new Random();
        int randomDirection = random.nextInt(4);
        switch (randomDirection) {
            case 0:
                direction = Direction.UP;
                break;
            case 1:
                direction = Direction.DOWN;
                break;
            case 2:
                direction = Direction.LEFT;
                break;
            case 3:
                direction = Direction.RIGHT;
                break;
        }

        switch (direction) {
            case UP:
                y -= MOVEMENT_SPEED;
                break;
            case DOWN:
                y += MOVEMENT_SPEED;
                break;
            case LEFT:
                x -= MOVEMENT_SPEED;
                break;
            case RIGHT:
                x += MOVEMENT_SPEED;
                break;
        }
    }

    public boolean checkCollision(PacMan pacMan) {
        int dx = x - pacMan.getX();
        int dy = y - pacMan.getY();
        return Math.sqrt(dx * dx + dy * dy) < COLLISION_THRESHOLD;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}