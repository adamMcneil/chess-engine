package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private static final Piece white = new Rook(Color.w);
    private static final Piece black = new Rook(Color.b);
    private static final double[] valueTable = new double[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, 10, 10, 10, 10, 5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            0, 0, 0, 5, 5, 0, 0, 0
    };

    public Rook(Color c) {
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265C;
            letter = 'R';
        } else {
            piece = (char) 0x2656;
            letter = 'r';
        }
    }

    public static Piece getWhitePiece() {
        return white;
    }

    public static Piece getBlackPiece() {
        return black;
    }

    public static Piece getPiece(Color color) {
        if (color == Color.w) {
            return white;
        } else {
            return black;
        }
    }

    @Override
    public double[] getTableValue() {
        return valueTable;
    }

    @Override
    public boolean isRook() {
        return true;
    }



    public static List<Move> getRookMoves(Board board, int position) {
        List<Move> moves = new ArrayList<>();
        Piece piece = board.getPieceAt(position);
        for (int change : Directions.vertical) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition)) {
                if (piece.isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (move.isMoveLegal(board, piece.color)) {
                    moves.add(move);
                }
                if (piece.isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        for (int change : Directions.horizontal) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && Position.isSameRow(position, checkPosition)) {
                if (piece.isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (move.isMoveLegal(board, piece.color)) {
                    moves.add(move);
                }
                if (piece.isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        return moves;
    }

    @Override
    public List<Move> getMoves(Board board, int position) {
        return getRookMoves(board, position);
    }

    @Override
    public List<Move> getPseudoMoves(Board board, int position) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int change : Directions.vertical) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition)) {
                if (isSameColor(board.getPieceAt(checkPosition)))
                    break;
                moves.add(move);
                if (isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        for (int change : Directions.horizontal) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isSameRow(position, checkPosition)) {
                if (isSameColor(board.getPieceAt(checkPosition)))
                    break;
                moves.add(move);
                if (isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        return moves;
    }

}
