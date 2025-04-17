package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class King extends Piece {
    private static final Piece white = new King(Color.w);
    private static final Piece black = new King(Color.b);
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

    public King(Color c) {
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
    public boolean isKing() {
        return true;
    }

    public List<Move> getCastleMoves(Board board, int position, Predicate<Move> filter) {
        if (color == Color.w) {
            if (!board.isWhiteCanCastleKingSide() && !board.isWhiteCanCastleQueenSide()) {
                return List.of();
            }
        } else {
            if (!board.isBlackCanCastleKingSide() && !board.isBlackCanCastleQueenSide()) {
                return List.of();
            }
        }
        if (board.isColorInCheck(color)) {
            return List.of();
        }
        List<Move> castleMoves = new ArrayList<>();
        if (color == Color.w) {
            if (board.isWhiteCanCastleKingSide()) {
                int right1 = 61;
                int right2 = 62;
                Move move1 = new Move(position, right1, MoveType.normal);
                Move castleMove = new Move(position, right2, MoveType.castling);
                if (board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(castleMove.getEnd()).getColor() != Color.b
                        && move1.isMoveLegal(board, color)) {
                    if (filter.test(castleMove)) {
                        castleMoves.add(castleMove);
                    }
                }
            }
            if (board.isWhiteCanCastleQueenSide()) {
                int left1 = 59;
                int left2 = 58;
                int left3 = 57;
                Move move1 = new Move(position, left1, MoveType.normal);
                Move move2 = new Move(position, left2, MoveType.normal);
                Move castleMove = new Move(position, left3, MoveType.castling);
                if (board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.b
                        && move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)) {
                    if (filter.test(castleMove)) {
                        castleMoves.add(castleMove);
                    }
                }
            }
        } else {
            if (board.isBlackCanCastleKingSide()) {
                int right1 = 5;
                int right2 = 6;
                Move move1 = new Move(position, right1, MoveType.normal);
                Move castleMove = new Move(position, right2, MoveType.castling);
                if (board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(castleMove.getEnd()).getColor() != Color.w
                        && move1.isMoveLegal(board, color)) {
                    if (filter.test(move1)) {
                        castleMoves.add(castleMove);
                    }
                }
            }
            if (board.isBlackCanCastleQueenSide()) {
                int left1 = 3;
                int left2 = 2;
                int left3 = 1;
                Move move1 = new Move(position, left1, MoveType.normal);
                Move move2 = new Move(position, left2, MoveType.normal);
                Move castleMove = new Move(position, left3, MoveType.castling);
                if (board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.w
                        && move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)
                ) {
                    if (filter.test(castleMove)) {
                        castleMoves.add(castleMove);
                    }
                }
            }
        }
        return castleMoves;
    }

    @Override
    public List<Move> getMoves(Board board, int position, Predicate<Move> filter) {
        List<Move> moves = new ArrayList<>();
        Move move;
        int checkPosition;
        for (int change : Directions.orthogonal) {
            checkPosition = position + change;
            move = new Move(position, checkPosition, MoveType.normal);
            if (Position.isSameRow(position, checkPosition) || Position.isSameColumn(position, checkPosition)) {
                if (filter.test(move)) {
                    moves.add(move);
                }
            }
        }
        for (int change : Directions.diagonal) {
            checkPosition = position + change;
            move = new Move(position, checkPosition, MoveType.normal);
            if (Position.isDiagonal(position, checkPosition) && filter.test(move)) {
                moves.add(move);
            }
        }
        moves.addAll(getCastleMoves(board, position, filter));
        return moves;
    }
}
