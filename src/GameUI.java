import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameUI extends JFrame implements ActionListener, KeyListener {
    private GameBoard gameBoard;
    private Timer timer;

    public GameUI() {
        gameBoard = new GameBoard();
        setTitle("PacMan Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        PacMan pacMan = gameBoard.getPacMan();
        g.setColor(Color.YELLOW);
        g.fillOval(pacMan.getX(), pacMan.getY(), 20, 20);

        for (Dot dot : gameBoard.getDots()) {
            if (dot.isVisible()) {
                g.setColor(Color.WHITE);
                g.fillOval(dot.getX(), dot.getY(), 10, 10);
            }
        }

        for (Ghost ghost : gameBoard.getGhosts()) {
            g.setColor(Color.RED);
            g.fillOval(ghost.getX(), ghost.getY(), 20, 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameBoard.updateGame();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            gameBoard.getPacMan().setDirection(Direction.UP);
        } else if (key == KeyEvent.VK_DOWN) {
            gameBoard.getPacMan().setDirection(Direction.DOWN);
        } else if (key == KeyEvent.VK_LEFT) {
            gameBoard.getPacMan().setDirection(Direction.LEFT);
        } else if (key == KeyEvent.VK_RIGHT) {
            gameBoard.getPacMan().setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameUI gameUI = new GameUI();
            gameUI.setVisible(true);
        });
    }
}