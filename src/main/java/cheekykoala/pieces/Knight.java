package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;
import cheekykoala.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private final static Piece white = new Knight(Color.w);
    private final static Piece black = new Knight(Color.b);
    private static final double[] valueTable = new double[]{
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50,
    };

    public Knight(Color c) {
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265E;
            letter = 'N';
        }
        if (c == Color.b) {
            piece = (char) 0x2658;
            letter = 'n';
        }
    }

    @Override
    public double[] getTableValue() {
        return valueTable;
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
    public ArrayList<Move> getMoves(Board board, int position) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] directions = Directions.knights;
        for (int change : directions) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            if (move.isMoveLegal(board, color)
                    && Math.abs(Position.getRow(checkPosition) - Position.getRow(position)) < 3
                    && Math.abs(Position.getColumn(checkPosition) - Position.getColumn(position)) < 3) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public List<Move> getPseudoMoves(Board board, int position) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] directions = Directions.knights;
        for (int change : directions) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            if (Position.isOnBoard(checkPosition)
                    && board.getPieceAt(checkPosition).color != color
                    && Math.abs(Position.getRow(checkPosition) - Position.getRow(position)) < 3
                    && Math.abs(Position.getColumn(checkPosition) - Position.getColumn(position)) < 3) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public boolean isKnight() {
        return true;
    }
}
