import javax.swing.*;
import java.awt.*;

public class PacMan {
    private int x;
    private int y;
    private Direction direction;
    private int score;
    private static final int MOVEMENT_SPEED = 5;
    private static final int COLLISION_THRESHOLD = 20;
    private Image pacman1, pacman2up, pacman3up, pacman4up;
    private Image pacman2down, pacman3down, pacman4down;
    private Image pacman2left, pacman3left, pacman4left;
    private Image pacman2right, pacman3right, pacman4right;
    private int pacmanAnimPos = 0;
    private int view_dx = -1, view_dy = 0;

    public PacMan(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = Direction.LEFT;
        this.score = 0;
        loadImages();
    }

    private void drawPacman(Graphics2D g2d) {
        if (view_dx == -1) {
            drawPacmanLeft(g2d);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {
        g2d.drawImage(getPacmanImage(pacman2up, pacman3up, pacman4up), x, y, null);
    }

    private void drawPacmanDown(Graphics2D g2d) {
        g2d.drawImage(getPacmanImage(pacman2down, pacman3down, pacman4down), x, y, null);
    }

    private void drawPacmanLeft(Graphics2D g2d) {
        g2d.drawImage(getPacmanImage(pacman2left, pacman3left, pacman4left), x, y, null);
    }

    private void drawPacmanRight(Graphics2D g2d) {
        g2d.drawImage(getPacmanImage(pacman2right, pacman3right, pacman4right), x, y, null);
    }

    private Image getPacmanImage(Image img1, Image img2, Image img3) {
        switch (pacmanAnimPos) {
            case 1: return img1;
            case 2: return img2;
            case 3: return img3;
            default: return pacman1;
        }
    }

    private void loadImages() {
        pacman1 = new ImageIcon("src/resources/images/pacman.png").getImage();
        pacman2up = new ImageIcon("src/resources/images/up1.png").getImage();
        pacman3up = new ImageIcon("src/resources/images/up2.png").getImage();
        pacman4up = new ImageIcon("src/resources/images/up3.png").getImage();
        pacman2down = new ImageIcon("src/resources/images/down1.png").getImage();
        pacman3down = new ImageIcon("src/resources/images/down2.png").getImage();
        pacman4down = new ImageIcon("src/resources/images/down3.png").getImage();
        pacman2left = new ImageIcon("src/resources/images/left1.png").getImage();
        pacman3left = new ImageIcon("src/resources/images/left2.png").getImage();
        pacman4left = new ImageIcon("src/resources/images/left3.png").getImage();
        pacman2right = new ImageIcon("src/resources/images/right1.png").getImage();
        pacman3right = new ImageIcon("src/resources/images/right2.png").getImage();
        pacman4right = new ImageIcon("src/resources/images/right3.png").getImage();
    }

    public void move() {
        switch (direction) {
            case UP -> y -= MOVEMENT_SPEED;
            case DOWN -> y += MOVEMENT_SPEED;
            case LEFT -> x -= MOVEMENT_SPEED;
            case RIGHT -> x += MOVEMENT_SPEED;
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

    public void setDirection(Direction direction) {
        this.direction = direction;
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
