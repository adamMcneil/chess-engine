package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Pawn extends Piece {
    private static final Piece white = new Pawn(Color.w);
    private static final Piece black = new Pawn(Color.b);
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

    public Pawn(Color c) {
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265F;
            letter = 'P';
        } else {
            piece = (char) 0x2659;
            letter = 'p';
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
    public boolean isPawn() {
        return true;
    }

    public boolean getHasMoved(int position) {
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

    public List<Move> getUpOne(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        int checkPosition = position + direction;
        Move move = new Move(position, checkPosition, MoveType.normal);
        if (board.getPieceAt(checkPosition).getColor() == Color.g
                && Position.getRow(checkPosition) != 0 && Position.getRow(checkPosition) != 7) {
            if (filter.test(move)) {
                moves.add(move);
            }
        }
        return moves;
    }

    public List<Move> getUpTwo(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        int checkPosition;
        int direction = getDirection(color);
        if (!getHasMoved(position)) {
            checkPosition = position + (direction * 2);
            Move move = new Move(position, checkPosition, MoveType.upTwo);
            int oneAbove = position + direction;
            if (board.getPieceAt(oneAbove).getColor() == Color.g
                    && board.getPieceAt(checkPosition).getColor() == Color.g) {
                if (filter.test(move)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    public List<Move> getAttacks(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        int right = position + direction - 1;
        int left = position + direction + 1;
        Move moveLeft = new Move(position, left, MoveType.normal);
        Move moveRight = new Move(position, right, MoveType.normal);
        if (Position.getRow(left) != 0 && Position.getRow(left) != 7 && Position.isDiagonal(position, left)
                && isOppositeColor(board.getPieceAt(left))) {
            if (filter.test(moveLeft)) {
                moves.add(moveLeft);
            }
        }
        if (Position.getRow(right) != 0 && Position.getRow(right) != 7 && Position.isDiagonal(position, right)
                && isOppositeColor(board.getPieceAt(right))) {
            if (filter.test(moveRight)) {
                moves.add(moveRight);
            }
        }
        return moves;
    }

    public static List<Move> makePromotionMoves(Color color, int position, int end) {
        Move moveQueen = new PromotionMove(position, end, Queen.getPiece(color));
        Move moveKnight = new PromotionMove(position, end, Knight.getPiece(color));
        Move moveRook = new PromotionMove(position, end, Rook.getPiece(color));
        Move moveBishop = new PromotionMove(position, end, Bishop.getPiece(color));
        return Arrays.asList(moveQueen, moveKnight, moveRook, moveBishop);
    }

    public List<Move> getPromotionMoves(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        int checkPosition = position + direction;
        Move move = new Move(position, checkPosition, MoveType.promotion);
        if (board.getPieceAt(checkPosition).getColor() == Color.g
                && (Position.getRow(checkPosition) == 0 || Position.getRow(checkPosition) == 7)) {
            if (filter.test(move)) {
                moves.addAll(makePromotionMoves(color, position, checkPosition));
            }
        }

        int left = position + direction - 1;
        int right = position + direction + 1;
        Move moveLeft = new Move(position, left, MoveType.promotion);
        Move moveRight = new Move(position, right, MoveType.promotion);
        if ((Position.getRow(left) == 0 || Position.getRow(left) == 7)
                && Position.isDiagonal(position, left) && isOppositeColor(board.getPieceAt(left))) {
            if (filter.test(moveRight)) {
                moves.addAll(makePromotionMoves(color, position, left));
            }
        }
        if ((Position.getRow(right) == 0 || Position.getRow(right) == 7)
                && Position.isDiagonal(position, right) && isOppositeColor(board.getPieceAt(right))) {
            if (filter.test(moveLeft)) {
                moves.addAll(makePromotionMoves(color, position, right));
            }
        }
        return moves;
    }

    private List<Move> getInPassingMoves(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        int right = position + direction - 1;
        int left = position + direction + 1;
        Move moveLeft = new Move(position, left, MoveType.inPassing);
        Move moveRight = new Move(position, right, MoveType.inPassing);
        if (Position.getRow(left) != 0 && Position.getRow(left) != 7 && Position.isDiagonal(position, left)
                && board.getCanInPassingAttack() && left == board.getInPassingSquare()) {
            if (filter.test(moveLeft)) {
                moves.add(moveLeft);
            }
        }
        if (Position.getRow(right) != 0 && Position.getRow(right) != 7 && Position.isDiagonal(position, right)
                && board.getCanInPassingAttack() && right == board.getInPassingSquare()) {
            if (filter.test(moveRight)) {
                moves.add(moveRight);
            }
        }
        return moves;
    }

    @Override
    public double[] getTableValue() {
        return valueTable;
    }

    @Override
    public List<Move> getMoves(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(getUpOne(board, position, filter));
        moves.addAll(getUpTwo(board, position, filter));
        moves.addAll(getAttacks(board, position, filter));
        moves.addAll(getInPassingMoves(board, position, filter));
        moves.addAll(getPromotionMoves(board, position, filter));
        return moves;
    }
}
