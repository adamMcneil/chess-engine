package Cheakykoala;

import Cheakykoala.Pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int index = 0;

    public static void main(String[] args) throws InterruptedException {
        testNodes2();
    }

    public static void testNodes(Board board){
        if (countNodes(board, 1, Color.w) == 20)
            System.out.println ("depth 1");
        if (countNodes(board, 2, Color.w) == 400)
            System.out.println ("depth 2");
        if (countNodes(board, 3, Color.w) == 8902)
            System.out.println ("depth 3");
        if (countNodes(board, 4, Color.w) == 197281)
            System.out.println ("depth 4");
        if (countNodes(board, 5, Color.w) == 4865609)
            System.out.println ("depth 5");
    }

    public static void testNodes2 (){
        Board board = new Board();
        board.importBoard("r3k2N/p6p/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        board.printBoard();
        Position position = new Position(4,0);
        System.out.println (board.getPieceAt(position).getMoves(board));
        System.out.println (board.getPieceAt(position).getMoves(board).size());
//        if (countNodes(board, 1, Color.w) == 48)
//            System.out.println ("depth 1");
//        if (countNodes(board, 2, Color.w) == 2039)
//            System.out.println ("depth 2");
//        if (countNodes(board, 3, Color.w) == 97862)
//            System.out.println ("depth 3");
//        if (countNodes(board, 4, Color.w) == 4085603)
//            System.out.println (countNodes(board, 1, Color.b));
    }

    public static void humanVsMinimax(Board board, int numTurns) {
        for (int i = 0; i < numTurns; i++) {
            board.printBoard();
            System.out.println();
            playMinimax(board);
            board.printBoard();
            System.out.println();
            humanPlay(board, Color.b);
        }
    }

    public static void humanPlay(Board board, Color color) {
        Scanner input = new Scanner(System.in);
        boolean wasLegal = false;
        while (!wasLegal) {
            System.out.print("Piece you want to move : ");
            String beginning = input.nextLine();
            System.out.println();
            System.out.print("Where you would so like to move your pieceage to sir : ");
            String end = input.nextLine();
            System.out.println();
            Position first = new Position(Character.getNumericValue(beginning.charAt(0)), Character.getNumericValue(beginning.charAt(1)));
            Position last = new Position(Character.getNumericValue(end.charAt(0)), Character.getNumericValue(end.charAt(1)));
            if (new Move(first, last).isMoveLegal(board, color)) {
                wasLegal = true;
                board.getPieceAt(first).move(board, new Move(first, last));
                System.out.println("mediocre move");
                return;
            }
            System.out.println("GO BACK TO CHECKERS!");
        }
    }

    public static Color getOppositeColor(Color color) {
        if (color == Color.w) {
            return Color.b;
        }
        return Color.w;
    }

    public static int countNodes(Board board, int depth, Color color) {
        int count = 0;
        Board child;
        if (depth == 0) {
            return 1;
        }
        for (Move move : board.getAllMoves(color)) {
            child = board.getChild(board, move);
            count += countNodes(child, depth - 1, getOppositeColor(color));
        }
        return count;
    }

//u64 Perft(int depth)
//{
//  MOVE move_list[256];
//  int n_moves, i;
//  u64 nodes = 0;
//
//  if (depth == 0)
//    return 1ULL;
//
//  n_moves = GenerateLegalMoves(move_list);
//  for (i = 0; i < n_moves; i++) {
//    MakeMove(move_list[i]);
//    nodes += Perft(depth - 1);
//    UndoMove(move_list[i]);
//  }
//  return nodes;
//}


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
                        double mx = minimax(child, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
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