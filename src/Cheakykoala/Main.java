package Cheakykoala;

import Cheakykoala.Pieces.*;

import java.util.ArrayList;

public class Main {
    public static int index = 0;

    public static void main(String[] args) throws InterruptedException {
        //I CHANGED MOVE
        Board board = new Board();
//        board.addPiece(new Position(1,3), new Pawn(Color.w, new Position(1,3)));
//        board.printBoard();
//        Position beginning = new Position(0,1);
//        Position end = new Position(0,3);
//        Move move = new Move(beginning, end);
//        board.getPieceAt(beginning).move(board, move);
//        board.printBoard();
//        for (Move m: board.getPieceAt(new Position(1,3)).getMoves(board)){
//            System.out.println (m);
//        }
        playMinimax(board);
        System.out.println(index);
    }

    public static Color getOppositeColor(Color color) {
        if (color == Color.w) {
            return Color.b;
        }
        return Color.w;
    }

    public static void countNodes(int depth, Color color) {
        Board board = new Board();
        int count = 0;
        for (int x = depth; x >= 0; x++) {
            for (Move move : getAllMoves(board, color)) {
                board.getChild(board, move);
                countNodes(x - 1, getOppositeColor(color));
            }
        }

    }
        System.out.println(count);
}

    public static ArrayList<Move> getAllMoves(Board board, Color color) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece[] pieces : board.getBoard()) {
            for (Piece p : pieces) {
                if (p.getColor() == color) {
                    moves.addAll(p.getMoves(board));
                }
            }
        }
        return moves;
    }


    public static void comparePlay(Board board, int depth) throws InterruptedException {
        for (int i = 0; i < depth; i++) {
            board.printBoard();
            System.out.println();
//            Thread.sleep(3000);
            playMinimax(board);
            board.printBoard();
            System.out.println();
//            Thread.sleep(3000);
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
                        double mx = minimax(child, 4, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
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
        bestMove = bestMoves.get((int) (Math.random() * bestMoves.size()));
        board.getPieceAt(bestMove.getBeginning()).move(board, bestMove);
    }

    public static double evalBoard(Board board) {
        index++;
//        board.printBoard();
//        System.out.println(index);
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
//        System.out.println(board.getWinState(Color.w) + " " + board.getWinState(Color.b));
        if (depth == 0 || board.getWinState(Color.w) != 0 || board.getWinState(Color.b) != 0) {
            return (evalBoard(board));
        }
        if (isMaxPlayer) {
            maxEval = -9999;
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    if (piece.getColor() == Color.w) {
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
            }
            return maxEval;
        } else {
            minEval = 9999;
            for (Piece[] pieces : board.getBoard()) {
                for (Piece piece : pieces) {
                    if (piece.getColor() == Color.b) {
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
            }
            return minEval;
        }
    }
}