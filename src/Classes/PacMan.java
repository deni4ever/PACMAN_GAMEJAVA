public class PacMan {
    private int x;
    private int y;
    private Direction direction;
    private int score;
    private static final int MOVEMENT_SPEED = 5;
    private static final int COLLISION_THRESHOLD = 20;

    public PacMan(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.direction = Direction.LEFT;
        this.score = 0;
    }

    public void move() {
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

    public void eatDot(Dot dot) {
        if (dot.isVisible() && x == dot.getX() && y == dot.getY()) {
            score += 10;
            dot.setVisible(false);
        }
    }

    public boolean checkCollision(Ghost ghost) {
        int dx = x - ghost.getX();
        int dy = y - ghost.getY();
        return Math.sqrt(dx * dx + dy * dy) < COLLISION_THRESHOLD;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public int getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}