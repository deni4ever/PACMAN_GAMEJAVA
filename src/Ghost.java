import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private int dx, dy;
    private int speed;
    private Image ghostImage;
    private GameBoard board; // Reference to game board

    private static final int BLOCK_SIZE = 24;
    private static final int N_BLOCKS = 15; // Adjust as needed
    private static final Random random = new Random();

    public Ghost(int x, int y, int speed, Image ghostImage, GameBoard board) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.ghostImage = ghostImage;
        this.board = board;
        this.dx = 0;
        this.dy = 0;
    }

    public void move() {
        int pos = (x / BLOCK_SIZE) + N_BLOCKS * (y / BLOCK_SIZE);
        int count = 0;
        int[] possibleDx = new int[4];
        int[] possibleDy = new int[4];

        // Get screen data from board
        short[] screenData = board.getScreenData();

        if ((screenData[pos] & 1) == 0 && dx != 1) { // Left
            possibleDx[count] = -1;
            possibleDy[count] = 0;
            count++;
        }
        if ((screenData[pos] & 2) == 0 && dy != 1) { // Up
            possibleDx[count] = 0;
            possibleDy[count] = -1;
            count++;
        }
        if ((screenData[pos] & 4) == 0 && dx != -1) { // Right
            possibleDx[count] = 1;
            possibleDy[count] = 0;
            count++;
        }
        if ((screenData[pos] & 8) == 0 && dy != -1) { // Down
            possibleDx[count] = 0;
            possibleDy[count] = 1;
            count++;
        }

        if (count == 0) {
            dx = -dx;
            dy = -dy;
        } else {
            int chosenDirection = random.nextInt(count);
            dx = possibleDx[chosenDirection];
            dy = possibleDy[chosenDirection];
        }

        x += dx * speed;
        y += dy * speed;

        checkCollision();
    }

    private void checkCollision() {
        int pacmanX = board.getPacmanX();
        int pacmanY = board.getPacmanY();

        if (Math.abs(pacmanX - x) < 12 && Math.abs(pacmanY - y) < 12) {
            board.setPacmanDying(true);
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(ghostImage, x + 1, y + 1, board);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
