package Cheakykoala.Pieces;

import Cheakykoala.Color;
import Cheakykoala.Position;
import Cheakykoala.Board;
import Cheakykoala.Move;

import java.util.ArrayList;

public abstract class Piece {

    Position position;
    char piece;
    Color color;

    public abstract ArrayList<Move> getMoves(Board board);

    public boolean isKing(){
        return false;
    }

    public boolean isEmpty(){
        return false;
    }

    public char getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }



    public void move(Board board, Move move) {
            board.addPiece(move.getEnd(), this);
            this.position = move.getEnd();
            board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
            board.printBoard();
    }
    public void undoMove(Board board, Move move, Piece taken) {
        board.addPiece(move.getBeginning(), this);
        board.printBoard();
        this.position = move.getBeginning();

        board.addPiece(move.getEnd(), taken);
        board.printBoard();
    }

//    //white pieces
//    static char wk = (char) 0x2654;
//    static char wq = (char) 0x2655;
//    static char wr = (char) 0x2656;
//    static char wb = (char) 0x2657;
//    static char wn = (char) 0x2658;
//    static char wp = (char) 0x2659;
//     //black pieces
//    static char bk = (char) 0x265A;
//    static char bq = (char) 0x265B;
//    static char br = (char) 0x265C;
//    static char bb = (char) 0x265D;
//    static char bn = (char) 0x265E;
//    static char bp = (char) 0x265F;
//
//    static char[] whitePieces = {wk, wq, wr, wb, wn, wp};
//    static char[] blackPieces = {bk, bq, br, bb, bn, bp};
//
//    public static char getPiece(int x) {
//        switch (x) {
//            case 0: //blank space
//                return ' ';
//            case 1: //white king
//                return whitePieces[0];
//            case 2: //white queen
//                return whitePieces[1];
//            case 3: //white rook
//                return whitePieces[2];
//            case 4: //white bishop
//                return whitePieces[3];
//            case 5: //white knight
//                return whitePieces[4];
//            case 6: //white pawn
//                return whitePieces[5];
//            case 7: //black king
//                return blackPieces[0];
//            case 8: //black queen
//                return blackPieces[1];
//            case 9: //black rook
//                return blackPieces[2];
//            case 10: //black bishop
//                return blackPieces[3];
//            case 11: //black knight
//                return blackPieces[4];
//            case 12: //black pawn
//                return blackPieces[5];
//        }
//        return '?';
//    }
//    public static int[] getLocation(){
//        int[] x = {1,1};
//        return x;
//    }

}
