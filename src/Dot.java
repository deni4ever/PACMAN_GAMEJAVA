import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a dot that Pac-Man can eat to gain points.
 */
public class Dot {
    private int x, y;        // Position coordinates
    private boolean visible; // Tracks if dot is still available
    private final int SIZE = 8; // Size of the dot
    
    /**
     * Constructs a new Dot at the specified position.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Dot(int x, int y) {
        setPosition(x, y);
        this.visible = true;
    }
    
    /**
     * Sets the dot's position.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Checks if the dot is visible.
     * @return true if visible, false otherwise
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Sets the visibility of the dot.
     * @param visible The visibility status
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Draws the dot on the screen if it is visible.
     * @param g Graphics context
     */
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.WHITE);
            g.fillOval(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
        }
    }
    
    /**
     * Checks if Pac-Man has eaten this dot.
     * @param pacMan The Pac-Man instance
     * @return true if Pac-Man has eaten the dot, false otherwise
     */
    public boolean isEaten(PacMan pacMan) {
        if (!visible) {
            return false;
        }
        
        // Center of dot
        int dotCenterX = x;
        int dotCenterY = y;
        
        // Center of Pac-Man
        int pacManCenterX = pacMan.getX() + pacMan.getSize() / 2;
        int pacManCenterY = pacMan.getY() + pacMan.getSize() / 2;
        
        // Calculate distance between centers
        double distance = Math.sqrt(
            Math.pow(pacManCenterX - dotCenterX, 2) +
            Math.pow(pacManCenterY - dotCenterY, 2)
        );
        
        // If distance is less than half Pac-Man's size plus half dot's size
        return distance < (pacMan.getSize() / 2) + (SIZE / 2);
    }
    
    // Getters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}