import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

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
        System.out.println("1. User Input");
        System.out.println("2. Level1");
        System.out.println("3. Level2");
        System.out.println("4. Level3");
        System.out.println("5. Level8");
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
            case 3:
            {
                level1 = Level.createLevel2();
                board = level1.getBoard().cloneBoard();
                break;
            }
            case 4:
            {
                level1 = Level.createLevel3();
                board = level1.getBoard().cloneBoard();
                break;
            }
            case 5:
            {
                level1 = Level.createLevel8();
                board = level1.getBoard().cloneBoard();
                break;
            }
            default:{
                break;
            }
        }



        System.out.println("Enter number of the Algorithm:");
        System.out.println("1.DFS Using LOOPS");
        System.out.println("2.DFS Using Recursion ");
        System.out.println("3.BFS");
        System.out.println("4.UCS");

      int x ;

      x =  scanner.nextInt();
        Node root = new Node(null, board.cloneBoard());
        Logic solver = new Logic();
        switch (x){
            case 1:
            {
                Node solutionNode = solver.dfsUsingLoop(root);

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
                Node solutionNode = solver.dfsUsingRecursion(root,new HashSet<Board>());

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

        // Get screen dimensions and set the frame to the right side
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set the frame's location to the right side of the screen
        int frameWidth = 700; // Width of the frame
        int xPosition = screenWidth - frameWidth; // Position frame on the right side
        int yPosition = (screenHeight - 800) / 2 - 20; // Vertically center the frame
        frame.setLocation(xPosition, yPosition);

        frame.setVisible(true);
    }
}
