import Model.*;

import java.util.*;

public class Logic {
    Structure structure = new Structure();
    static Scanner scanner = new Scanner(System.in);

    public List<Node> generateNextStates(Node node) {
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

    public List<Node> generateNextCostStates(Node node) {
        List<Node> nextCostStates = new ArrayList<>();
        var thisBoard = node.board;
        for (Move move : Move.values()) {
            Board clonedBoard = thisBoard.cloneBoard();
            int removedSquares = structure.applyMove(clonedBoard, move);
            if (removedSquares > 0) {
                int newCost = node.cost - removedSquares + 10;
                Node newNode = new Node(node, clonedBoard, newCost, move);
                nextCostStates.add(newNode);
                //        newNode.PrintNode();
                //        System.out.println();

            } else {
                int newCost = node.cost + move.getCost();
                Node newNode = new Node(node, clonedBoard, newCost, move);
                nextCostStates.add(newNode);
            }
        }

        return nextCostStates;
    }

    public List<Node> generateNextHeuristicStates(Node node) {
        List<Node> nextHeuristicStates = new ArrayList<>();
        var thisBoard = node.board;
        for (Move move : Move.values()) {
            Board clonedBoard = thisBoard.cloneBoard();
            int removedSquares = structure.applyMove(clonedBoard, move);
            if (removedSquares > 0) {
                int newHeuristic = clonedBoard.getHeuristic(move);
                Node newNode = new Node(node, clonedBoard, move, 0, newHeuristic);
                System.out.println("heuristic : " + newHeuristic);
                nextHeuristicStates.add(newNode);
            } else {
                int newHeuristic = clonedBoard.getHeuristic(move) + move.getCost();
                Node newNode = new Node(node, clonedBoard, move, 0, newHeuristic);
                System.out.println("heuristic : " + newHeuristic);
                nextHeuristicStates.add(newNode);
            }

            //        newNode.PrintNode();
            //        System.out.println();
        }

        return nextHeuristicStates;
    }

    public List<Node> generateNextAstarCostStates(Node node) {
        List<Node> nextCostStates = new ArrayList<>();
        var thisBoard = node.board;

        for (Move move : Move.values()) {
            Board clonedBoard = thisBoard.cloneBoard();
            int removedSquares = structure.applyMove(clonedBoard, move);
            if (removedSquares > 0) {
                int g = node.cost + 1 - removedSquares;
                int h = clonedBoard.getHeuristic(move);
                System.out.println("heuristic : " + h);

                Node newNode = new Node(node, clonedBoard, move, g, h);
                nextCostStates.add(newNode);
            } else {
                int g = node.cost + 1 + move.getCost();
                int h = clonedBoard.getHeuristic(move);
                System.out.println("heuristic : " + h);

                Node newNode = new Node(node, clonedBoard, move, g, h);
                nextCostStates.add(newNode);
            }

        }
        return nextCostStates;
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

    public Node ucs(Node root) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        Set<Board> visited = new HashSet<>();
        priorityQueue.add(root);
        visited.add(root.board);

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            node.PrintNode();


            if (FinalState(visited, node)) return node; // Solution found

            for (Node nextState : generateNextCostStates(node)) {
                if (!visited.contains(nextState.board)) {
                    priorityQueue.add(nextState);
                    visited.add(nextState.board);
                }
            }
        }

        return null; // No solution found
    }


    public Node hillClimbing(Node root) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getHeuristic));
        Set<Board> visited = new HashSet<>();
        root.cost = 0;
//        root.heuristic = root.board.getNumOfSquaresLeft() * 10;
        root.heuristic = root.getHeuristic();
        priorityQueue.add(root);
        visited.add(root.board);

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            node.PrintNode();


            if (FinalState(visited, node)) return node; // Solution found


            for (Node nextState : generateNextHeuristicStates(node)) {
                if (!visited.contains(nextState.board)) {
                    priorityQueue.add(nextState);
                    visited.add(nextState.board);
                }
            }
        }

        return null; // No solution found
    }

    public Node AStar(Node root) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getAstarCost));
        Set<Board> visited = new HashSet<>();
        root.cost = 0;
//        root.heuristic = root.board.getNumOfSquaresLeft() * 10;
        root.heuristic = root.getHeuristic();
        priorityQueue.add(root);
        visited.add(root.board);

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            node.PrintNode();


            if (FinalState(visited, node)) return node; // Solution found


            for (Node nextState : generateNextAstarCostStates(node)) {
                if (!visited.contains(nextState.board)) {
                    priorityQueue.add(nextState);
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


    public Board InitializeBoard() {
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
                    coloredSquaresByColor.computeIfAbsent(colorChoice, k -> new ArrayList<>()).add(coloredSquare);

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

    private String getColorFromChoice(int choice) {
        switch (choice) {
            case 1:
                return ConsoleColors.RED;
            case 2:
                return ConsoleColors.GREEN;
            case 3:
                return ConsoleColors.YELLOW;
            case 4:
                return ConsoleColors.BLUE;
            case 5:
                return ConsoleColors.PURPLE;
            case 6:
                return ConsoleColors.CYAN;
            case 7:
                return ConsoleColors.WHITE;
            default:
                System.out.println("Invalid choice. Defaulting to White.");
                return ConsoleColors.WHITE;
        }
    }


}
