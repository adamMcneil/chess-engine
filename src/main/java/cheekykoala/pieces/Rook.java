package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    public static List<Move> getRookMoves(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        Piece piece = board.getPieceAt(position);
        for (int change : Directions.vertical) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition, MoveType.normal);
            while (Position.isOnBoard(checkPosition)) {
                if (piece.isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (filter.test(move)) {
                    moves.add(move);
                }
                if (piece.isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition, MoveType.normal);
            }
        }
        for (int change : Directions.horizontal) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition, MoveType.normal);
            while (Position.isOnBoard(checkPosition) && Position.isSameRow(position, checkPosition)) {
                if (piece.isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (filter.test(move)) {
                    moves.add(move);
                }
                if (piece.isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition, MoveType.normal);
            }
        }
        return moves;
    }

    @Override
    public List<Move> getMoves(Board board, int position, Predicate<Move> filter) {
        return getRookMoves(board, position, filter);
    }
}
