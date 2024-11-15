import Model.*;

import java.util.*;

public class Logic {
    Structure structure = new Structure();
    static Scanner scanner = new Scanner(System.in);

    public  List<Node> generateNextStates(Node node) {
        List<Node> nextStates = new ArrayList<>();
        for (Move move : Move.values()) {
            Board clonedBoard = node.board.cloneBoard();
            structure.applyMove(clonedBoard, move);
            Node newNode = new Node(node, clonedBoard);
            newNode.action = move;
//        newNode.PrintNode();
//        System.out.println();
            nextStates.add(newNode);
        }

        return nextStates;
    }

    public Node bfs(Node root) {
        Queue<Node> queue = new LinkedList<>();
        Set<Board> visited = new HashSet<>();
        queue.add(root);
        visited.add(root.board);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            node.PrintNode();


            if (FinalState(visited, node)) return node; // Solution found

            // Generate all possible next states
            for (Node nextState : generateNextStates(node)) {
                if (!visited.contains(nextState.board)) {
                    queue.add(nextState);
                    visited.add(nextState.board);
                }
            }
        }

        return null; // No solution found
    }

    private boolean FinalState(Set<Board> visited, Node node) {
        if (structure.isFinalState(node)) {
            System.out.println("Visited nodes: " + visited.size());
            List<Move> pathToSolution = node.getPath();
            System.out.println("Solution found! Path to solution:");
            for (Move move : pathToSolution) {
                System.out.println(move);
            }
            return true;
        }
        return false;
    }

    public Node dfsUsingLoop(Node root) {
        Stack<Node> stack = new Stack<>();
        Set<Board> visited = new HashSet<>();
        stack.push(root);
        visited.add(root.board);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            node.PrintNode();

            if (FinalState(visited, node)) return node; // Solution found


            for (Node nextState : generateNextStates(node)) {
                if (!visited.contains(nextState.board)) {
                    stack.push(nextState);
                    visited.add(nextState.board);
                }
            }
        }

        return null;
    }

    public Node dfsUsingRecursion(Node node, Set<Board> visited) {
        if (node == null) return null;

        node.PrintNode();

       if (FinalState(visited, node)) return node;

       visited.add(node.board);

        for (Node nextState : generateNextStates(node)) {
            if (!visited.contains(nextState.board)) {
               Node result = dfsUsingRecursion(nextState, visited);
                if (result != null) return result;
            }
        }

        return null;
    }


    public  Board InitializeBoard() {
        System.out.print("Enter the size of the board X: ");
        int boardX = scanner.nextInt();
        System.out.print("Enter the size of the board Y: ");
        int boardY = scanner.nextInt();

        Set<String> occupiedPositions = new HashSet<>();
        System.out.print("How many Colors Do You need? ");
        int numOfColors = scanner.nextInt();

        System.out.print("Enter the number of colored squares: ");
        int numberOfColoredSquares = scanner.nextInt();

        Map<Integer, List<ColoredSquare>> coloredSquaresByColor = new HashMap<>();

        System.out.println("Choose a color for each colored square:");
        System.out.println("1. Red\n2. Green\n3. Yellow\n4. Blue\n5. Purple\n6. Cyan\n7. White");

        for (int i = 0; i < numberOfColoredSquares; i++) {
            while (true) {
                System.out.print("Enter the position of colored square " + (i + 1) + " (x y): ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                String pos = x + "," + y;

                if (isValidPosition(x, y, boardX, boardY) && !occupiedPositions.contains(pos)) {
                    System.out.print("Enter your choice of color for square " + (i + 1) + ": ");
                    int colorChoice = scanner.nextInt();

                    String color = getColorFromChoice(colorChoice);

                    ColoredSquare coloredSquare = new ColoredSquare(new Position(x, y), " ■ ", color);

                    // Add colored square to the list in the map for the chosen color
                    coloredSquaresByColor
                            .computeIfAbsent(colorChoice, k -> new ArrayList<>())
                            .add(coloredSquare);

                    occupiedPositions.add(pos);
                    break;
                } else {
                    System.out.println("Invalid or occupied position. Please enter a unique position within the board boundaries.");
                }
            }
        }

        System.out.print("Enter the number of walls: ");
        int numberOfWalls = scanner.nextInt();
        Wall[] walls = new Wall[numberOfWalls];

        for (int i = 0; i < numberOfWalls; i++) {
            while (true) {
                System.out.print("Enter the position of wall " + (i + 1) + " (x y): ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                String pos = x + "," + y;

                if (isValidPosition(x, y, boardX, boardY) && !occupiedPositions.contains(pos)) {
                    walls[i] = new Wall(new Position(x, y));
                    occupiedPositions.add(pos);
                    break;
                } else {
                    System.out.println("Invalid or occupied position. Please enter a unique position within the board boundaries.");
                }
            }
        }
        System.out.println();
        return new Board(boardX, boardY, numOfColors, coloredSquaresByColor, walls);
    }

    private static boolean isValidPosition(int x, int y, int boardX, int boardY) {
        return x >= 0 && x < boardX && y >= 0 && y < boardY;
    }

    private  String getColorFromChoice(int choice) {
        switch (choice) {
            case 1: return ConsoleColors.RED;
            case 2: return ConsoleColors.GREEN;
            case 3: return ConsoleColors.YELLOW;
            case 4: return ConsoleColors.BLUE;
            case 5: return ConsoleColors.PURPLE;
            case 6: return ConsoleColors.CYAN;
            case 7: return ConsoleColors.WHITE;
            default:
                System.out.println("Invalid choice. Defaulting to White.");
                return ConsoleColors.WHITE;
        }
    }
}
