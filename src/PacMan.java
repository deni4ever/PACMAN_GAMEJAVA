import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents the player-controlled Pac-Man character.
 */
public class PacMan {
    private int x, y;           // Position coordinates
    private Direction direction; // Current direction
    private int score;          // Player's score
    private final int SIZE = 30; // Size of Pac-Man
    private int mouthAngle;     // Angle for animating Pac-Man's mouth
    private boolean mouthClosing; // Flag for mouth animation direction
    
    /**
     * Constructs a new Pac-Man at the specified position.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public PacMan(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = Direction.RIGHT;
        this.score = 0;
        this.mouthAngle = 45;
        this.mouthClosing = false;
    }
    
    /**
     * Moves Pac-Man in the current direction.
     * @param gameWidth Width of the game board
     * @param gameHeight Height of the game board
     */
    public void move(int gameWidth, int gameHeight) {
        int speed = 5;
        
        // Update position based on direction
        switch (direction) {
            case UP:
                y = Math.max(y - speed, 0);
                break;
            case DOWN:
                y = Math.min(y + speed, gameHeight - SIZE);
                break;
            case LEFT:
                x = Math.max(x - speed, 0);
                break;
            case RIGHT:
                x = Math.min(x + speed, gameWidth - SIZE);
                break;
        }
        
        // Animate mouth
        if (mouthClosing) {
            mouthAngle -= 5;
            if (mouthAngle <= 5) {
                mouthClosing = false;
            }
        } else {
            mouthAngle += 5;
            if (mouthAngle >= 45) {
                mouthClosing = true;
            }
        }
    }
    
    /**
     * Increases score when eating a dot.
     * @param dot The dot being eaten
     */
    public void eatDot(Dot dot) {
        if (dot.isVisible()) {
            score += 10;
            dot.setVisible(false);
        }
    }
    
    /**
     * Checks collision with a ghost.
     * @param ghost The ghost to check collision with
     * @return true if collision detected, false otherwise
     */
    public boolean checkCollision(Ghost ghost) {
        // Simple collision detection using bounding boxes
        int ghostSize = 30;
        return (x < ghost.getX() + ghostSize &&
                x + SIZE > ghost.getX() &&
                y < ghost.getY() + ghostSize &&
                y + SIZE > ghost.getY());
    }
    
    /**
     * Draws Pac-Man on the screen.
     * @param g Graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        
        // Calculate start and end angles based on direction
        int startAngle = 0;
        switch (direction) {
            case RIGHT:
                startAngle = mouthAngle / 2;
                break;
            case DOWN:
                startAngle = 90 - mouthAngle / 2;
                break;
            case LEFT:
                startAngle = 180 - mouthAngle / 2;
                break;
            case UP:
                startAngle = 270 - mouthAngle / 2;
                break;
        }
        
        g.fillArc(x, y, SIZE, SIZE, startAngle, 360 - mouthAngle);
    }
    
    // Getters and setters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getSize() {
        return SIZE;
    }
}