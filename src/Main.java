import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JPanel implements KeyListener {
    private Board board;
    private List<ColoredSquare> initialColoredSquares;

    public Main() {
        initialColoredSquares = new ArrayList<>();
        initialColoredSquares.add(new ColoredSquare(new Position(0, 0), " ■ ", ConsoleColors.YELLOW));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 0), " ■ ", ConsoleColors.YELLOW));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 2), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 2), " ■ ", ConsoleColors.PURPLE));

        resetBoard();

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        // Add reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetBoard());
        add(resetButton);
    }

    private void resetBoard() {
        // Deep copy initialColoredSquares to reset colored squares
        List<ColoredSquare> newSquares = new ArrayList<>();
        for (ColoredSquare square : initialColoredSquares) {
            newSquares.add(new ColoredSquare(new Position(square.position.x, square.position.y), square.type, square.color));
        }
        board = new Board(3, 3, 2, newSquares, new Wall[]{});
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        int cellSize = 100;
        for (int row = 0; row < board.boardY; row++) {
            for (int col = 0; col < board.boardX; col++) {
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
        JFrame frame = new JFrame("Colored Squares Game");
        Main gamePanel = new Main();

        frame.add(gamePanel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
