package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;

public class Rook extends Piece {
        private static final double[] valueTable = new double[] {
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

    public Rook(Color c, Position position) {
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
        int[][] baseMoves = {
                { 1, 0 },
                { 0, 1 },
                { -1, 0 },
                { 0, -1 },
        };
        for (int[] arr : baseMoves) {
            Position checkPosition = new Position(position.getX() + arr[0], position.getY() + arr[1]);
            Move move = new Move(position, checkPosition);
            while (move.getEnd().isOnBoard()) {
                if (this.isSameColor(board.getPieceAt(move.getEnd())))
                    break;
                if (move.isMoveLegal(board, color)) {
                    move = new Move(position, checkPosition);
                    moves.add(move);
                }
                if (this.isOppositeColor(board.getPieceAt(move.getEnd())))
                    break;
                checkPosition = new Position(checkPosition.getX() + arr[0], checkPosition.getY() + arr[1]);
                move = new Move(position, checkPosition);
            }
        }
        return moves;
    }

}
