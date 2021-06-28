package Cheakykoala;

import Cheakykoala.Pieces.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board();
        board.printBoard();
        comparePlay(board, 20);
//        Position position = new Position(3, 6);
//        ArrayList<Move> moves = board.getPieceAt(position).getMoves(board);
//        for (Move move : moves) {
//            System.out.println(move);
//        }
    }

    public static void comparePlay(Board board, int depth) throws InterruptedException {
        for (int i = 0; i < depth; i++) {
            board.printBoard();
            System.out.println();
            playMinimax(board);
            board.printBoard();
            System.out.println();
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
        ArrayList<Move> bestMoves = new ArrayList<>();
        Move bestMove = new Move(new Position(0, 0), new Position(0, 0));
        Board child;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == Color.w) {
                    for (Move m : p.getMoves(board)) {
                        child = board.getChild(board, m);
                        double mx = minimax(child, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
                        if (bestMoveValue == mx){
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
        bestMove = bestMoves.get((int)(Math.random() * bestMoves.size()));
        board.getPieceAt(bestMove.getBeginning()).move(board, bestMove);
    }

    public static int evalBoard(Board board) {
        int eval = 0;
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                if (piece.getColor() == Color.w) {
                    eval = eval + getPieceEval(piece);
                } else {
                    eval = eval - getPieceEval(piece);
                }
            }
        }
        return eval;
    }

    public static int getPieceEval(Piece piece) {
        char letter = piece.getChar();
        switch (letter) {
            case 'R':
                return 5;
            case 'N':
                return 3;
            case 'B':
                return 3;
            case 'Q':
                return 9;
            case 'K':
                return 999;
            case 'P':
                return 1;
        }
            return 1;
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