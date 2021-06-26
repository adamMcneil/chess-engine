package Cheakykoala;

import Cheakykoala.Pieces.Piece;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board();
        board.importBoard("8/8/8/8/8/3p4/3R4/3K4 w - - 0 1");
        board.printBoard();
        Position position = new Position(3, 6);
        ArrayList<Move> moves = board.getPieceAt(position).getMoves(board);
        for (Move move : moves) {
            System.out.println(move);
        }
    }

    public static void comparePlay(Board board, int depth) {
        for (int i = 0; i < depth; i++) {
            board.printBoard();
            playMinimax(board);
            board.printBoard();
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
        Thread.sleep(1000);

        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == Color.b) {
                    for (Move m : p.getMoves(board)) {
                        blackMoves.add(m);
                    }
                }
            }
        }
//        for (x = 0; x < blackMoves.size() - 1; x++) {
//            System.out.println(blackMoves.get(x));
//        }
        System.out.println("black moves: " + blackMoves.size());
        int y = (int) (Math.random() * blackMoves.size());
        board.getPieceAt(blackMoves.get(y).getBeginning()).move(board, blackMoves.get(y));
        board.printBoard();

    }

    public static void playMinimax(Board board) {
        double bestMoveValue = Double.NEGATIVE_INFINITY;
        Move bestMove = new Move(new Position(0, 0), new Position(0, 0));
        Board child;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == Color.w) {
                    for (Move m : p.getMoves(board)) {
                        child = board.getChild(board, m);
                        double mx = minimax(child, 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
                        if (bestMoveValue < mx) {
                            bestMoveValue = mx;
                            bestMove = m;
                        }
                    }
                }
            }
        }
        board.getPieceAt(bestMove.getBeginning()).move(board, bestMove);
    }

    public static int evalBoard(Board board) {
        int eval = 0;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                if (piece.getColor() == Color.w) {
                    eval++;
                } else {
                    eval--;
                }
            }
        }
        return eval;
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
                        eval = minimax(board.getChild(board, move), depth - 1, alpha, beta, false);
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
                        eval = minimax(board.getChild(board, move), depth - 1, alpha, beta, true);
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