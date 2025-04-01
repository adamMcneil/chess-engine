package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;
import cheekykoala.Position;
import cheekykoala.PromotionMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece {
    private static final double[] valueTable = new double[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            98, 134, 61, 95, 68, 126, 34, -11,
            -6, 7, 26, 31, 65, 56, 25, -20,
            -14, 13, 6, 21, 23, 12, 17, -23,
            -27, -2, -5, 12, 17, 6, 10, -25,
            -26, -4, -4, -10, 3, 3, 33, -12,
            -35, -1, -20, -23, -15, 24, 38, -22,
            0, 0, 0, 0, 0, 0, 0, 0,
    };

    public Pawn(Pawn other) {
        super(other);
    }

    @Override
    public Piece copy() {
        return new Pawn(this);
    }

    public Pawn(Color c, int position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265F;
            letter = 'P';
        } else {
            piece = (char) 0x2659;
            letter = 'p';
        }
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    public boolean getHasMoved() {
        return !((Position.getRow(position) == 1 && color == Color.b) || (Position.getRow(position) == 6 && color == Color.w));
    }

    public int getDirection(Color color) {
        int direction;
        if (color == Color.w)
            direction = -8;
        else
            direction = 8;
        return direction;
    }

    public ArrayList<Move> getUpOne(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        int checkPosition = position + direction;
        Move move = new Move(position, checkPosition);
        if (move.isMoveLegal(board, color) && board.getPieceAt(checkPosition).getColor() == Color.g
                && Position.getRow(checkPosition) != 0 && Position.getRow(checkPosition) != 7) {
            moves.add(move);
        }
        return moves;
    }

    public ArrayList<Move> getUpTwo(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int checkPosition;
        int direction = getDirection(color);
        if (!getHasMoved()) {
            checkPosition = position + (direction * 2);
            Move move = new Move(position, checkPosition);
            int oneAbove = position + direction;
            if (move.isMoveLegal(board, color) && board.getPieceAt(oneAbove).getColor() == Color.g
                    && board.getPieceAt(checkPosition).getColor() == Color.g)
                moves.add(move);
        }
        return moves;
    }

    public ArrayList<Move> getAttack(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        int right = position + direction - 1;
        int left = position + direction + 1;
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        if (Position.getRow(left) != 0 && Position.getRow(left) != 7 && moveLeft.isMoveLegal(board, color)
                && ((isOppositeColor(board.getPieceAt(left)) && Position.isDiagonal(position, left)) || (board.getCanEnpassant() && left == board.getInPassingSquare()))) {
            moves.add(moveLeft);
        }
        if (Position.getRow(right) != 0 && Position.getRow(right) != 7 && moveRight.isMoveLegal(board, color)
                && ((isOppositeColor(board.getPieceAt(right)) && Position.isDiagonal(position, right)) || (board.getCanEnpassant() && right == board.getInPassingSquare()))) {
            moves.add(moveRight);
        }
        return moves;
    }

    public static List<Move> makePromotionMoves(Color color, int position, int end) {
        Move moveQueen = new PromotionMove(position, end, new Queen(color, end));
        Move moveKnight = new PromotionMove(position, end, new Knight(color, end));
        Move moveRook = new PromotionMove(position, end, new Rook(color, end));
        Move moveBishop = new PromotionMove(position, end, new Bishop(color, end));
        return Arrays.asList(moveQueen, moveKnight, moveRook, moveBishop);
    }

    public ArrayList<Move> getPromotionMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        int checkPosition = position + direction;
        Move move = new Move(position, checkPosition);
        if (move.isMoveLegal(board, color) && board.getPieceAt(checkPosition).getColor() == Color.g
                && (Position.getRow(checkPosition) == 0 || Position.getRow(checkPosition) == 7)) {
            moves.addAll(makePromotionMoves(color, position, checkPosition));
        }

        int left = position + direction - 1;
        int right = position + direction + 1;
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        if ((Position.getRow(left) == 0 || Position.getRow(left) == 7)
                && (moveLeft.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(left)))) {
            moves.addAll(makePromotionMoves(color, position, left));
        }
        if ((Position.getRow(right) == 0 || Position.getRow(right) == 7)
                && (moveRight.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(right)))) {
            moves.addAll(makePromotionMoves(color, position, right));
        }
        return moves;
    }

    @Override
    public double[] getTableValue() {
        return valueTable;
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        moves.addAll(getUpOne(board));
        moves.addAll(getUpTwo(board));
        moves.addAll(getAttack(board));
        moves.addAll(getPromotionMoves(board));

        return moves;
    }

}
