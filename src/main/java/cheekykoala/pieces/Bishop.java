package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;
import cheekykoala.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private static final double[] valueTable = new double[]{
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -20, -10, -10, -10, -10, -10, -10, -20,
    };

    public Bishop(Bishop bishop) {
        super(bishop);
    }

    @Override
    public Piece copy() {
        return new Bishop(this);
    }

    public Bishop(Color c, int position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265D;
            letter = 'B';
        } else {
            piece = (char) 0x2657;
            letter = 'b';
        }
    }

    @Override
    public boolean isBishop() {
        return true;
    }

    @Override
    public double[] getTableValue() {
        return valueTable;
    }

    @Override
    public List<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] directions = Directions.diagonal;
        for (int change : directions) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && Position.isDiagonal(position, checkPosition)) {
                if (isSameColor(board.getPieceAt(checkPosition)))
                    break;
                if (move.isMoveLegal(board, color)) {
                    moves.add(move);
                }
                if (this.isOppositeColor(board.getPieceAt(checkPosition)))
                    break;
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        return moves;
    }

    @Override
    public List<Move> getPseudoMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] directions = Directions.diagonal;
        for (int change : directions) {
            int checkPosition = position + change;
            Move move = new Move(position, checkPosition);
            while (Position.isOnBoard(checkPosition) && Position.isDiagonal(position, checkPosition)) {
                if (isSameColor(board.getPieceAt(checkPosition))) {
                    break;
                }
                moves.add(move);
                if (isOppositeColor(board.getPieceAt(checkPosition))) {
                    break;
                }
                checkPosition += change;
                move = new Move(position, checkPosition);
            }
        }
        return moves;
    }

}
