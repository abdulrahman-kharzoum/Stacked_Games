import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements KeyListener {
    private Board board;
    Level level1;
    public Main() {

        resetBoard();

        setLayout(new BorderLayout());
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        // Add reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            resetBoard();
            requestFocusInWindow();  // Move focus back
        });
        add(resetButton, BorderLayout.SOUTH);

    }

    private void resetBoard() {
        // User Input
//        board  = Logic.InitializeBoard().cloneBoard();
//        board.displayBoard();
//
        // Static Level 8

      level1 = Level.createLevel1();
      board = level1.getBoard().cloneBoard();

//       Node root = new Node(null, board);
//       Logic.generateNextStates(root);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        int cellSize = 100;
        for (int row = 0; row < board.boardX; row++) {
            for (int col = 0; col < board.boardY; col++) {
                Square square = board.squares[row][col];

                if (square.getSquareType() == SquareType.WALL) {
                    g.setColor(Color.BLACK);
                } else if (square.getSquareType() == SquareType.EMPTY) {
                    g.setColor(Color.WHITE);
                } else if (square instanceof ColoredSquare) {
                    ColoredSquare coloredSquare = (ColoredSquare) square;
                    g.setColor(coloredSquare.getColor());
                }

                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> Structure.applyMove(board, Move.UP);
            case KeyEvent.VK_DOWN -> Structure.applyMove(board, Move.DOWN);
            case KeyEvent.VK_LEFT -> Structure.applyMove(board, Move.LEFT);
            case KeyEvent.VK_RIGHT -> Structure.applyMove(board, Move.RIGHT);
        }
        repaint();
    }



    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stacked Game");
        Main gamePanel = new Main();

        frame.add(gamePanel);
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
