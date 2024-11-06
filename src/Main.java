import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JPanel implements KeyListener {
    private Board board;
    private List<ColoredSquare> initialColoredSquares;

    public Main() {
        initialColoredSquares = new ArrayList<>();
        initialColoredSquares.add(new ColoredSquare(new Position(0, 0), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 1), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 2), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 3), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 4), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(0, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(1, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 5), " ■ ", ConsoleColors.BLUE));
        initialColoredSquares.add(new ColoredSquare(new Position(1, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(2, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 1), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(3, 2), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 1), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(4, 2), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 0), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 1), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 2), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 3), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(5, 4), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(1,3 ), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(1,4 ), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(2,3 ), " ■ ", ConsoleColors.PURPLE));
        initialColoredSquares.add(new ColoredSquare(new Position(2,4 ), " ■ ", ConsoleColors.PURPLE));
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

        Map<Integer, List<ColoredSquare>> coloredSquaresByColor = new HashMap<>();

        for (ColoredSquare square : initialColoredSquares) {

            int colorCode = square.colorCode;

            coloredSquaresByColor
                    .computeIfAbsent(colorCode, k -> new ArrayList<>())
                    .add(square);
        }
        board = new Board(6, 6, 2, coloredSquaresByColor, new Wall[]{
                new Wall(new Position(1,1)),
                new Wall(new Position(1,2)),
                new Wall(new Position(2,1)),
                new Wall(new Position(2,2)),
                new Wall(new Position(3,3)),
                new Wall(new Position(3,4)),
                new Wall(new Position(4,3)),
                new Wall(new Position(4,4))
        });
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
        JFrame frame = new JFrame("Stacked Game");
        Main gamePanel = new Main();

        frame.add(gamePanel);
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
