import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Main class for the Pac-Man game.
 */
public class PacManGame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs the main game window.
     */
    public PacManGame() {
        setTitle("Pac-Man Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        GameBoard gameBoard = new GameBoard();
        add(gameBoard);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Main entry point for the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PacManGame game = new PacManGame();
            game.setVisible(true);
        });
    }
}