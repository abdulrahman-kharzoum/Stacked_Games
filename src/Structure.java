public class Structure {
    public static void print(Board board){
        board.displayBoard();
    }
}

//import Model.Position;
//
//public class Structure {
////    static void print(Board board) {
////        char[][] boardArray = board.getBoard();
////
////        // Top wall
////        for (int j = 0; j < board.getBoardY() + 2; j++) {
////            System.out.print("#");
////        }
////        System.out.println();
////
////        for (int i = 0; i < board.getBoardX(); i++) {
////            System.out.print("#"); // Left wall
////
////            for (int j = 0; j < board.getBoardY(); j++) {
////                System.out.print(boardArray[i][j]);
////            }
////
////            System.out.print("#"); // Right wall
////            System.out.println();
////        }
////
////        // Bottom wall
////        for (int j = 0; j < board.getBoardY() + 2; j++) {
////            System.out.print("#");
////        }
////        System.out.println();
////    }
//
//    static boolean canMove(Board board, Move move) {
//        return canMoveGoal(board, move) || canMoveBall(board, move);
//    }
//    static boolean canMoveBall(Board board, Move move) {
//        boolean canMoveBall = false;
//
//        for (Position ball : board.getBalls()) {
//            Position nextPositionBall = getNextPosition(ball, move);
//            if (isOutOfBounds(nextPositionBall, board) || isWall(nextPositionBall, board)
//                    || isGoal(nextPositionBall, board) || isBall(nextPositionBall,board)) {
//                 canMoveBall = move.equals(Move.DOWN);
//            }
//        }
//
//        return  canMoveBall;
//    }
//    static  boolean canMoveGoal(Board board,Move move){
//        boolean canMoveGoal = false;
//        Position nextPositionGoal = getNextPosition(board.getGoal(), move);
//        canMoveGoal =(!isOutOfBounds(nextPositionGoal, board) && !isWall(nextPositionGoal, board) && !isBall(nextPositionGoal,board));
//        return canMoveGoal;
//    }
//    static void applyMove(Board board, Move move) {
//        for (Position ball : board.getBalls()) {
//            Position nextPosition = getNextPosition(ball, move);
//
//            while (canMoveBall(board,move)) {
//                ball.x = nextPosition.x;
//                ball.y = nextPosition.y;
//                nextPosition = getNextPosition(ball, move);
//                //board.setBalls();
//
//            }
//        }
//
//        Position nextPositionGoal = getNextPosition(board.getGoal(), move);
//        while (canMoveGoal(board,move)) {
//            board.getGoal().x = nextPositionGoal.x;
//            board.getGoal().y = nextPositionGoal.y;
//            nextPositionGoal = getNextPosition(board.getGoal(), move);
//            board.setGoal(nextPositionGoal);
//        }
//        print(board);
//    }
//
//    static boolean isOtherBall(Position pos, Position currentBall, Board board) {
//        for (Position ball : board.getBalls()) {
//            if (!ball.equals(currentBall) && ball.equals(pos)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//
//    static Position getNextPosition(Position current, Move move) {
//        return new Position(current.x + move.getMoveX(), current.y + move.getMoveY());
//    }
//
//    static boolean isOutOfBounds(Position pos, Board board) {
//        return pos.x < 0 || pos.x >= board.getBoardX() || pos.y < 0 || pos.y >= board.getBoardY();
//    }
//
//
//    static boolean isWall(Position pos, Board board) {
//        for (Position wall : board.getWalls()) {
//            if (pos.x == wall.x && pos.y == wall.y) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    static boolean isGoal(Position pos, Board board) {
//        return board.getGoal().x == pos.x && board.getGoal().y == pos.y;
//    }
//
//
//    static boolean isBall(Position pos, Board board) {
//       for(Position ball : board.getBalls())
//        {
//            if (pos.x == ball.x && pos.y == ball.y){
//                return true;
//            }
//        }
//       return false;
//    }
//
//}
