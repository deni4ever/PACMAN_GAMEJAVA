import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoard extends JPanel implements ActionListener {
    private static final int BLOCK_SIZE = 24;
    private static final int N_BLOCKS = 15; // Adjust based on the level
    private static final int N_GHOSTS = 4;
    private static final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

    private Timer timer;
    private short[] screenData; // Pac-Man grid
    private List<Ghost> ghosts;
    private PacMan pacman;
    private boolean pacmanDying;
    private Image ghostImage;
    private JFrame frame;
    private Color mazeColor = new Color(5, 100, 5); // Define a maze color
    private Color dotColor = new Color(255, 184, 151); // Define dot color

    public GameBoard() {
        pacman = new PacMan(7 * BLOCK_SIZE, 7 * BLOCK_SIZE); // Initialize PacMan
        loadImages();
        initBoard();
        initGame();
    }

    private void loadImages() {
        ghostImage = new ImageIcon("src/resources/images/ghost.png").getImage(); // Load ghost sprite
    }

    private void initBoard() {
        setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_SIZE, SCREEN_SIZE));
        setBackground(Color.BLACK);
    }

    private void initGame() {
        screenData = new short[N_BLOCKS * N_BLOCKS]; // Initialize map data
        ghosts = new ArrayList<>();
        pacmanDying = false;

        // Initialize Ghosts
        for (int i = 0; i < N_GHOSTS; i++) {
            ghosts.add(new Ghost(6 * BLOCK_SIZE, 6 * BLOCK_SIZE, 2, ghostImage, this));
        }

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.move();
        moveGhosts();
        repaint();
    }

    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawMaze(g2d);
        drawPacman(g2d);
        drawGhosts(g2d);
    }

    private void drawMaze(Graphics2D g2d) {
        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void drawPacman(Graphics2D g2d) {
        pacman.draw(g2d); // Ensure PacMan class has a draw() method
    }

    private void drawGhosts(Graphics2D g2d) {
        for (Ghost ghost : ghosts) {
            ghost.draw(g2d); // Ensure Ghost class has a draw() method
        }
    }

    // Getter methods for Ghost class
    public short[] getScreenData() {
        return screenData;
    }

    public int getPacmanX() {
        return pacman.getX();
    }

    public int getPacmanY() {
        return pacman.getY();
    }

    public void setPacmanDying(boolean dying) {
        this.pacmanDying = dying;
    }

    public void startGame() {
        frame = new JFrame("Pac-Man");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard game = new GameBoard();
            game.startGame();
        });
    }
}
