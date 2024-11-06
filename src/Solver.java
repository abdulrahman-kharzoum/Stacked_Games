import java.util.*;

public class Solver {
    public static Node bfs(Node root) {
        Queue<Node> queue = new LinkedList<>();
        Set<Board> visited = new HashSet<>();  // Track visited boards to avoid loops
        queue.add(root);
        visited.add(root.board);

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            // Check if this node is the final state
            if (Structure.isFinalState(node)) {
                return node; // Solution found
            }

            // Generate all possible next states
            for (Node nextState : Logic.generateNextStates(node)) {
                if (!visited.contains(nextState.board)) {
                    queue.add(nextState);
                    visited.add(nextState.board);
                }
            }
        }

        return null; // No solution found
    }

    public static Node dfs(Node root) {
        Stack<Node> stack = new Stack<>();
        Set<Board> visited = new HashSet<>();
        stack.push(root);
        visited.add(root.board);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            // Check if this node is the final state
            if (Structure.isFinalState(node)) {
                return node; // Solution found
            }

            // Generate all possible next states
            for (Node nextState : Logic.generateNextStates(node)) {
                if (!visited.contains(nextState.board)) {
                    stack.push(nextState);
                    visited.add(nextState.board);
                }
            }
        }

        return null; // No solution found
    }
}
