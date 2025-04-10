package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    private static final Piece white = new Queen(Color.w);
    private static final Piece black = new Queen(Color.b);
    private static final double[] valueTable = new double[]{
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -5, 0, 5, 5, 5, 5, 0, -5,
            0, 0, 5, 5, 5, 5, 0, -5,
            -10, 5, 5, 5, 5, 5, 0, -10,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20
    };

    public Queen(Color c) {
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265B;
            letter = 'Q';
        } else {
            piece = (char) 0x2655;
            letter = 'q';
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
    public boolean isQueen() {
        return true;
    }

    @Override
    public List<Move> getMoves(Board board, int position) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int change : Directions.orthogonal) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && (Position.isSameRow(position, checkPosition) || Position.isSameColumn(checkPosition, position))) {
                if (isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (move.isMoveLegal(board, color)) {
                    moves.add(move);
                }
                if (isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        for (int change : Directions.diagonal) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && (Position.isDiagonal(position, checkPosition))) {
                if (isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (move.isMoveLegal(board, color)) {
                    moves.add(move);
                }
                if (isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        return moves;
    }

    @Override
    public List<Move> getPseudoMoves(Board board, int position) {
        List<Move> moves = new ArrayList<>();
        for (int change : Directions.orthogonal) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && (Position.isSameRow(position, checkPosition) || Position.isSameColumn(checkPosition, position))) {
                if (isSameColor(board.getPieceAt(checkPosition)))
                    break;
                moves.add(move);
                if (isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        for (int change : Directions.diagonal) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && (Position.isDiagonal(position, checkPosition))) {
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
