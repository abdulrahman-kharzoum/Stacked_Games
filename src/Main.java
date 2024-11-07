import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JPanel implements KeyListener {
    Logic logic = new Logic();
    Board clone = null;

    boolean isrest = false;

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
//
//            if (!isrest) {
//                board = logic.InitializeBoard().cloneBoard();
//                clone = board.cloneBoard();
//                isrest = true;
//            } else {
//                board = clone.cloneBoard();
//                board.displayBoard();
//                System.out.println();
//            }


        // Static Level 8
//
      level1 = Level.createLevel8();
      board = level1.getBoard().cloneBoard();
//      board.displayBoard();


        // Next States
       Node root = new Node(null, board);

        Node solutionNode = logic.dfs(root);

        if (solutionNode != null) {
            System.out.println("Solution found!");
        } else {
            System.out.println("No solution exists.");
        }


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
            case KeyEvent.VK_UP -> logic.structure.applyMove(board, Move.UP);
            case KeyEvent.VK_DOWN -> logic.structure.applyMove(board, Move.DOWN);
            case KeyEvent.VK_LEFT -> logic.structure.applyMove(board, Move.LEFT);
            case KeyEvent.VK_RIGHT -> logic.structure.applyMove(board, Move.RIGHT);
        }
        repaint();
    }


    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stacked Game");
        Main gamePanel = new Main();

        frame.add(gamePanel);
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
