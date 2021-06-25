package Cheakykoala;

import Cheakykoala.Pieces.Empty;
import Cheakykoala.Pieces.Piece;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

//        board.importBoard("r6R/1r4R1/2r2R2/3rR3/3Rr3/2R2r2/1R4r1/R6r w - - 0 1");
//        board.printBoard();
//        System.out.println(board.get(2, 3).getPiece());
//        int i = 0;
//        for (Move m : board.get(0, 1).getMoves(board)) {
//            i++;
//            System.out.println(m.end);
//        }
//        System.out.println(i);
//        Position beginning = new Position(1, 1);
//        Position end = new Position(1, 2);
//        Move move = new Move(beginning, end);
//        board.getPieceAt(beginning).move(board, move);
//        Piece e = new Empty(null);
//        board.getPieceAt(end).undoMove(board, move, e);
//        board.printBoard();
//        System.out.println(board.getPieceAt(end).getPosition());
       playGame(board);
    }

    public static void playGame(Board board) {
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
        for (int x = 0; x < whiteMoves.size()-1; x++){
            whiteMoves.get(x).printMove();
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
        for ( x = 0; x < blackMoves.size()-1; x++){
            blackMoves.get(x).printMove();
        }
        System.out.println("black moves: " + blackMoves.size());
        int y = (int) (Math.random() * blackMoves.size());
        board.getPieceAt(blackMoves.get(y).getBeginning()).move(board, blackMoves.get(y));
        board.printBoard();

    }
}