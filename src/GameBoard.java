import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Represents the game board where Pac-Man, ghosts, and dots are rendered.
 */
public class GameBoard extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 600;
    private static final int DOT_SPACING = 50;
    private static final int DELAY = 20; // milliseconds
    
    private ArrayList<Dot> dots;         // Stores all dots
    private ArrayList<Ghost> ghosts;     // Stores all ghosts
    private PacMan pacMan;               // Instance of Pac-Man
    private Timer timer;                 // Game update timer
    private boolean isGameOver;          // Game over flag
    private boolean isWinner;            // Winner flag
    
    /**
     * Constructs a new game board and initializes the game components.
     */
    public GameBoard() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        pacMan.setDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        pacMan.setDirection(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        pacMan.setDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        pacMan.setDirection(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_SPACE:
                        if (isGameOver) {
                            initializeBoard();
                        }
                        break;
                }
            }
        });
        
        initializeBoard();
    }
    
    /**
     * Sets up dots, ghosts, and Pac-Man's position.
     */
    public void initializeBoard() {
        dots = new ArrayList<>();
        ghosts = new ArrayList<>();
        
        // Create dots in a grid pattern
        for (int x = DOT_SPACING; x < BOARD_WIDTH; x += DOT_SPACING) {
            for (int y = DOT_SPACING; y < BOARD_HEIGHT; y += DOT_SPACING) {
                dots.add(new Dot(x, y));
            }
        }
        
        // Create Pac-Man in the center of the board
        pacMan = new PacMan(BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
        
        // Create ghosts at different corners
        ghosts.add(new Ghost(50, 50, Color.RED));
        ghosts.add(new Ghost(BOARD_WIDTH - 80, 50, Color.CYAN));
        ghosts.add(new Ghost(50, BOARD_HEIGHT - 80, Color.PINK));
        ghosts.add(new Ghost(BOARD_WIDTH - 80, BOARD_HEIGHT - 80, Color.ORANGE));
        
        isGameOver = false;
        isWinner = false;
        
        // Start the timer for game updates
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    /**
     * Updates the game state, moves characters, and checks collisions.
     */
    public void updateGame() {
        if (isGameOver) {
            return;
        }
        
        // Move Pac-Man
        pacMan.move(BOARD_WIDTH, BOARD_HEIGHT);
        
        // Check for dot collisions
        int visibleDots = 0;
        for (Dot dot : dots) {
            if (dot.isVisible()) {
                visibleDots++;
                if (dot.isEaten(pacMan)) {
                    pacMan.eatDot(dot);
                }
            }
        }
        
        // Check if all dots are eaten
        if (visibleDots == 0) {
            isGameOver = true;
            isWinner = true;
            return;
        }
        
        // Move ghosts and check for collisions
        for (Ghost ghost : ghosts) {
            ghost.move(BOARD_WIDTH, BOARD_HEIGHT);
            
            if (ghost.checkCollision(pacMan)) {
                isGameOver = true;
                return;
            }
        }
    }
    
    /**
     * Returns true if Pac-Man is caught or all dots are collected.
     * @return Game over status
     */
    public boolean isGameOver() {
        return isGameOver;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw all game elements
        for (Dot dot : dots) {
            dot.draw(g);
        }
        
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }
        
        pacMan.draw(g);
        
        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + pacMan.getScore(), 20, 20);
        
        // Game over messages
        if (isGameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.YELLOW);
            String message = isWinner ? "You Win!" : "Game Over";
            int messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (BOARD_WIDTH - messageWidth) / 2, BOARD_HEIGHT / 2);
            
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartMsg = "Press SPACE to restart";
            int restartWidth = g.getFontMetrics().stringWidth(restartMsg);
            g.drawString(restartMsg, (BOARD_WIDTH - restartWidth) / 2, BOARD_HEIGHT / 2 + 40);
        }
    }
    
    // Getters
    public int getBoardWidth() {
        return BOARD_WIDTH;
    }
    
    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }
}