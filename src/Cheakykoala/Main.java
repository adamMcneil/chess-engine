package Cheakykoala;

import Cheakykoala.Pieces.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //I CHANGED MOVE
        Board board = new Board();
//        board.importBoard("r3k2r/pPpppppp/8/8/8/8/PPPPPPPP/RQ2Kp1R w KQkq - 0 1");
//        Position beginning = new Position(1,1);
//        Position end = new Position(0,0);
//        Piece piece = new Knight(Color.w, end);
//        Move move = new PromotionMove(beginning, end, piece);
//        board.getPieceAt(beginning).move(board, move);

//         beginning = new Position(3,0);
//         end = new Position(4,0);
//         move = new Move(beginning, end);
//        board.getPieceAt(beginning).move(board, move);

        board.printBoard();
//        for (Move m : board.getPieceAt(new Position(1,1)).getPromotionMoves(board)){
//            System.out.println(m);
//        }

//        System.out.println (board.getWhiteCastleMoveState());
//        System.out.println (board.getBlackCastleMoveState());
      playMinimax(board);
    }

    public static void comparePlay(Board board, int depth) throws InterruptedException {
        for (int i = 0; i < depth; i++) {
            board.printBoard();
            System.out.println();
//            Thread.sleep(4000);
            playMinimax(board);
            board.printBoard();
            System.out.println();
//            Thread.sleep(4000);
            playRandom(board, Color.b);
        }
    }

    public static void playRandom(Board board, Color color) {
        ArrayList<Move> moves = new ArrayList();
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == color) {
                    for (Move m : p.getMoves(board)) {
                        moves.add(m);
                    }
                }
            }
        }
        int y = (int) (Math.random() * moves.size());
        board.getPieceAt(moves.get(y).getBeginning()).move(board, moves.get(y));
    }

    public static void playTwoTurns(Board board) throws InterruptedException {
        ArrayList<Move> whiteMoves = new ArrayList<>();
        ArrayList<Move> blackMoves = new ArrayList<>();
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == Color.w) {
                    for (Move m : p.getMoves(board)) {
                        whiteMoves.add(m);
                    }
                }
            }
        }
        int x = (int) (Math.random() * whiteMoves.size());
        System.out.println("white moves: " + whiteMoves.size());
        board.getPieceAt(whiteMoves.get(x).getBeginning()).move(board, whiteMoves.get(x));
        board.printBoard();
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == Color.b) {
                    for (Move m : p.getMoves(board)) {
                        blackMoves.add(m);
                    }
                }
            }
        }
        System.out.println("black moves: " + blackMoves.size());
        int y = (int) (Math.random() * blackMoves.size());
        board.getPieceAt(blackMoves.get(y).getBeginning()).move(board, blackMoves.get(y));
        board.printBoard();

    }

    public static void playMinimax(Board board) {
        double bestMoveValue = Double.NEGATIVE_INFINITY;
        ArrayList<Move> bestMoves = new ArrayList<>();
        Move bestMove;
        Board child;
        ArrayList<Double> evals = new ArrayList<>();
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == Color.w) {
                    for (Move m : p.getMoves(board)) {
                        child = board.getChild(board, m);
                        double mx = minimax(child, 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
                        if (bestMoveValue == mx) {
                            bestMoves.add(m);
                        }
                        if (mx > bestMoveValue) {
                            bestMoveValue = mx;
                            bestMoves.clear();
                            bestMoves.add(m);
                        }
                    }
                }
            }
        }
        if (bestMoves.size() == 0)
            board.getWinState();
        bestMove = bestMoves.get((int) (Math.random() * bestMoves.size()));
        board.getPieceAt(bestMove.getBeginning()).move(board, bestMove);
    }

    public static double evalBoard(Board board) {
        int eval = 0;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                if (piece.getColor() == Color.w) {
                    eval += getPieceEval(piece);
                } else {
                    eval -= getPieceEval(piece);
                }
            }
        }
        return eval;
    }

    public static double getPieceEval(Piece piece) {
        char letter = piece.getLetter();
        letter = Character.toUpperCase(letter);
        switch (letter) {
            case 'R':
                return 5;
            case 'N':
            case 'B':
                return 3;
            case 'Q':
                return 9;
            case 'P':
                return 1;
        }
        return 0;
    }

    public static double minimax(Board board, int depth, double alpha, double beta, boolean isMaxPlayer) {
        double eval;
        double minEval;
        double maxEval;
        if (depth == 0) {
            return (evalBoard(board));
        }
        if (isMaxPlayer) {
            maxEval = -999;
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    for (Move move : piece.getMoves(board)) {
                        //System.out.println("here");
                        eval = minimax(board.getChild(board, move), depth - 1, alpha, beta, false);
                        //System.out.println(depth);
                        maxEval = Math.max(alpha, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        } else {
            minEval = 999;
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    for (Move move : piece.getMoves(board)) {
                        //System.out.println("here");
                        eval = minimax(board.getChild(board, move), depth - 1, alpha, beta, true);
                        //System.out.println(depth);
                        minEval = Math.min(minEval, eval);
                        beta = Math.max(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }
}