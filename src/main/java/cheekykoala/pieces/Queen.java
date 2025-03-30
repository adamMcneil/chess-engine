package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;

public class Queen extends Piece {
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

    public Queen(Queen other) {
        super(other);
    }

    @Override
    public Piece copy() {
        return new Queen(this);
    }


    public Queen(Color c, int position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265B;
            letter = 'Q';
        } else {
            piece = (char) 0x2655;
            letter = 'q';
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

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] directions = Directions.all;
        for (int change : directions) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && (Position.isSameDiagonal(position, checkPosition) || Position.isSameRow(position, checkPosition) || Position.isSameColumn(checkPosition, position))) {
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

}
