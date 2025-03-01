import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Represents a ghost that chases Pac-Man.
 */
public class Ghost {
    private int x, y;             // Position coordinates
    private Direction direction;  // Current direction
    private final Color color;    // Ghost color
    private final int SIZE = 30;  // Size of ghost
    private final Random random;  // For random movement
    
    /**
     * Constructs a new Ghost at the specified position with the given color.
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param color The ghost's color
     */
    public Ghost(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.direction = Direction.values()[new Random().nextInt(4)];
        this.random = new Random();
    }
    
    /**
     * Moves the ghost randomly.
     * @param gameWidth Width of the game board
     * @param gameHeight Height of the game board
     */
    public void move(int gameWidth, int gameHeight) {
        int speed = 3;
        
        // Occasionally change direction
        if (random.nextInt(20) == 0) {
            direction = Direction.values()[random.nextInt(4)];
        }
        
        // Update position based on direction
        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }
        
        // Keep ghost within game boundaries
        if (x < 0) {
            x = 0;
            direction = Direction.RIGHT;
        } else if (x > gameWidth - SIZE) {
            x = gameWidth - SIZE;
            direction = Direction.LEFT;
        }
        
        if (y < 0) {
            y = 0;
            direction = Direction.DOWN;
        } else if (y > gameHeight - SIZE) {
            y = gameHeight - SIZE;
            direction = Direction.UP;
        }
    }
    
    /**
     * Checks collision with Pac-Man.
     * @param pacMan The Pac-Man instance
     * @return true if collision detected, false otherwise
     */
    public boolean checkCollision(PacMan pacMan) {
        // Simple collision detection using bounding boxes
        int pacManSize = pacMan.getSize();
        return (x < pacMan.getX() + pacManSize &&
                x + SIZE > pacMan.getX() &&
                y < pacMan.getY() + pacManSize &&
                y + SIZE > pacMan.getY());
    }
    
    /**
     * Draws the ghost on the screen.
     * @param g Graphics context
     */
    public void draw(Graphics g) {
        g.setColor(color);
        
        // Draw ghost body (semicircle on top of rectangle)
        g.fillArc(x, y, SIZE, SIZE, 0, 180);
        g.fillRect(x, y + SIZE/2, SIZE, SIZE/2);
        
        // Draw wavy bottom
        int waveHeight = SIZE / 6;
        int segments = 3;
        int segmentWidth = SIZE / segments;
        
        for (int i = 0; i < segments; i++) {
            g.fillArc(x + i * segmentWidth, y + SIZE - waveHeight, 
                      segmentWidth, waveHeight * 2, 180, 180);
        }
        
        // Draw eyes
        g.setColor(Color.WHITE);
        g.fillOval(x + SIZE/4 - 2, y + SIZE/3, SIZE/4, SIZE/4);
        g.fillOval(x + SIZE/2, y + SIZE/3, SIZE/4, SIZE/4);
        
        // Draw pupils
        g.setColor(Color.BLACK);
        int pupilOffset;
        switch (direction) {
            case LEFT:
                pupilOffset = -1;
                break;
            case RIGHT:
                pupilOffset = 1;
                break;
            case UP:
                pupilOffset = 0;
                break;
            case DOWN:
                pupilOffset = 0;
                break;
            default:
                pupilOffset = 0;
        }
        
        g.fillOval(x + SIZE/4 + 2 + pupilOffset, y + SIZE/3 + 2, SIZE/8, SIZE/8);
        g.fillOval(x + SIZE/2 + 2 + pupilOffset, y + SIZE/3 + 2, SIZE/8, SIZE/8);
    }
    
    // Getters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Direction getDirection() {
        return direction;
    }
}