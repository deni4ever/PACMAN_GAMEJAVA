import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameBoard {
    private ArrayList<Dot> dots;
    private ArrayList<Ghost> ghosts;
    private PacMan pacMan;
    private Random random;

    public GameBoard() {
        dots = new ArrayList<>();
        ghosts = new ArrayList<>();
        pacMan = new PacMan(0, 0);
        random = new Random();
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < 10; i++) {
            dots.add(new Dot(i * 10, i * 10));
            ghosts.add(new Ghost((i + 1) * 15, (i + 1) * 15));
        }
    }

    public void updateGame() {
        pacMan.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
            if (pacMan.checkCollision(ghost)) {
                System.out.println("Game Over! Pac-Man was caught by a ghost.");
                System.out.println("Final Score: " + pacMan.getScore());
                System.exit(0);
            }
            if (ghost.checkCollision(pacMan)) {
                System.out.println("Game Over! Ghost collided with Pac-Man.");
                System.out.println("Final Score: " + pacMan.getScore());
                System.exit(0);
            }
        }
        for (Dot dot : dots) {
            pacMan.eatDot(dot);
            if (!dot.isVisible()) {
                repositionDot(dot);
            }
        }
        if (isGameOver()) {
            System.out.println("Game Over! All dots have been eaten.");
            System.out.println("Final Score: " + pacMan.getScore());
            System.exit(0);
        }
    }

    public void repositionDot(Dot dot) {
        int newX = random.nextInt(150);
        int newY = random.nextInt(150);
        dot.setPosition(newX, newY);
        dot.setVisible(true);
    }

    public boolean isGameOver() {
        for (Dot dot : dots) {
            if (dot.isVisible()) {
                return false;
            }
        }
        return true;
    }

    public void printDotsAndGhostsPositions() {
        System.out.println("Dots Positions:");
        for (Dot dot : getDots()) {
            System.out.println("Dot at (" + dot.getX() + ", " + dot.getY() + "), Visible: " + dot.isVisible());
        }
        System.out.println("Ghosts Positions:");
        for (Ghost ghost : getGhosts()) {
            System.out.println("Ghost at (" + ghost.getX() + ", " + ghost.getY() + ")");
        }
    }

    public PacMan getPacMan() {
        return pacMan;
    }

    public ArrayList<Dot> getDots() {
        return dots;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Enter direction (UP, DOWN, LEFT, RIGHT) or QUIT to exit:");
            String input = scanner.nextLine().toUpperCase();
            
            if (input.equals("QUIT")) {
                break;
            }
            
            try {
                Direction direction = Direction.valueOf(input);
                gameBoard.getPacMan().setDirection(direction);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid direction. Please enter UP, DOWN, LEFT, or RIGHT.");
                continue;
            }
            
            gameBoard.updateGame();
            gameBoard.printDotsAndGhostsPositions(); // Printing positions for debugging
        }
        
        scanner.close();
    }
}