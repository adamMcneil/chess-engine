package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;

public class Rook extends Piece {
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

    public Rook(Rook other) {
        super(other);
    }

    @Override
    public Piece copy() {
        return new Rook(this);
    }

    public Rook(Color c, int position) {
        super();
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265C;
            letter = 'R';
        } else {
            piece = (char) 0x2656;
            letter = 'r';
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

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] directions = Directions.orthogonal;
        int home = position;
        for (int change : directions) {
            int checkPosition = home + change;
            Move move = new Move(home, checkPosition);
            while (Position.isOnBoard(checkPosition) && (Position.isSameColumn(home, checkPosition) || Position.isSameRow(home, checkPosition))) {
                if (isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (move.isMoveLegal(board, color)) {
                    moves.add(move);
                }
                if (isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(home, checkPosition);
            }
        }
        return moves;
    }

}
