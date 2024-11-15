import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        // User Input
        System.out.println("Enter the method:");
        System.out.println("1. User Play");
        System.out.println("2. Level1");
        int i = scanner.nextInt();
        switch (i){
            case 1:
            {
                if (!isrest) {
                    board = logic.InitializeBoard().cloneBoard();
                    clone = board.cloneBoard();
                    isrest = true;
                } else {
                    board = clone.cloneBoard();
                    board.displayBoard();
                    System.out.println();
                }
            break;
            }
            case 2:
            {
                level1 = Level.createLevel1();
                board = level1.getBoard().cloneBoard();
                break;
            }
        }


        // Static Level 8


        System.out.println("Enter number of the play");
        System.out.println("1.DFS");
        System.out.println("2.BFS");
      int x ;

      x =  scanner.nextInt();
        Node root = new Node(null, board.cloneBoard());
        Logic solver = new Logic();
        switch (x){
            case 1:
            {
                Node solutionNode = solver.dfs(root);

                if (solutionNode != null) {
                    System.out.println("Solution found!");
                } else {
                    System.out.println("No solution exists.");
                }
                break;
            }
            case 2:
            {
                Node solutionNode = solver.bfs(root);

                if (solutionNode != null) {
                    System.out.println("Solution found!");
                } else {
                    System.out.println("No solution exists.");
                }
                break;
            }
            case 3:
            {
                Node solutionNode = solver.dfs(root);

                if (solutionNode != null) {
                    System.out.println("Solution found!");
                } else {
                    System.out.println("No solution exists.");
                }
                break;
            }
            default:{

            }
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
