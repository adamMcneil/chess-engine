package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;
import cheekykoala.Position;

import java.util.ArrayList;

public class King extends Piece {
    private static final double[] valueTable = new double[]{
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            20, 20, 0, 0, 0, 0, 20, 20,
            20, 30, 10, 0, 0, 10, 30, 20
    };

    public King(King other) {
        super(other);
    }

    @Override
    public Piece copy() {
        return new King(this);
    }

    public King(Color c, int position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265A;
            letter = 'K';
        } else {
            piece = (char) 0x2654;
            letter = 'k';
        }
    }

    @Override
    public double[] getTableValue() {
        return valueTable;
    }

    @Override
    public boolean isKing() {
        return true;
    }

    public ArrayList<Move> getCastleMoves(Board board) {
        ArrayList<Move> castleMoves = new ArrayList<>();
        int moveState;
        if (color == Color.w) {
            moveState = board.getWhiteCastleMoveState();
            if ((moveState == 0 || moveState == 2) && !board.isColorInCheck(color)) {
                int right1 = 61;
                int right2 = 62;
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)
                        && board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.b) {
                    castleMoves.add(new Move(position, 62));
                }
            }
            if ((moveState == 0 || moveState == 1) && !board.isColorInCheck(color)) {
                int left1 = 59;
                int left2 = 58;
                int left3 = 57;
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)
                        && board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.b) {
                    castleMoves.add(new Move(position, 58));
                }
            }
        } else {
            moveState = board.getBlackCastleMoveState();
            if ((moveState == 0 || moveState == 2) && !board.isColorInCheck(color)) {
                int right1 = 5;
                int right2 = 6;
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)
                        && board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.w) {
                    castleMoves.add(new Move(position, 6));
                }
            }
            if ((moveState == 0 || moveState == 1) && !board.isColorInCheck(color)) {
                int left1 = 3;
                int left2 = 2;
                int left3 = 1;
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)
                        && board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.w) {
                    castleMoves.add(new Move(this.position, 2));
                }
            }
        }
        return castleMoves;
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Move move;
        int checkPosition;
        for (int change : Directions.orthogonal) {
            checkPosition = position + change;
            move = new Move(position, checkPosition);
            if (move.isMoveLegal(board, color) && (Position.isSameRow(position, checkPosition) || Position.isSameColumn(position, checkPosition))) {
                moves.add(move);
            }
        }
        for (int change : Directions.diagonal) {
            checkPosition = position + change;
            move = new Move(position, checkPosition);
            if (move.isMoveLegal(board, color) && Position.isDiagonal(position, checkPosition)) {
                moves.add(move);
            }
        }
        moves.addAll(getCastleMoves(board));
        return moves;
    }

}
