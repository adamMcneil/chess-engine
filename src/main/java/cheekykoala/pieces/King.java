package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;
import cheekykoala.Position;

import java.util.ArrayList;
import java.util.List;

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

    public List<Move> getPseudoCastleMoves(Board board, int position) {
        if (board.isColorInCheck(color)) {
            return List.of();
        }
        List<Move> castleMoves = new ArrayList<>();
        if (color == Color.w) {
            if (board.isWhiteCanCastleKingSide()) {
                int right1 = 61;
                int right2 = 62;
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (move1.isMoveLegal(board, color)
                        && board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.b) {
                    castleMoves.add(new Move(position, 62));
                }
            }
            if (board.isWhiteCanCastleQueenSide()) {
                int left1 = 59;
                int left2 = 58;
                int left3 = 57;
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (move1.isMoveLegal(board, color)
                        && board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.b) {
                    castleMoves.add(new Move(position, 58));
                }
            }
        } else {
            if (board.isBlackCanCastleKingSide()) {
                int right1 = 5;
                int right2 = 6;
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (move1.isMoveLegal(board, color)
                        && board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.w) {
                    castleMoves.add(new Move(position, 6));
                }
            }
            if (board.isBlackCanCastleQueenSide()) {
                int left1 = 3;
                int left2 = 2;
                int left3 = 1;
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (move1.isMoveLegal(board, color)
                        && board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.w) {
                    castleMoves.add(new Move(position, 2));
                }
            }
        }
        return castleMoves;
    }

    public List<Move> getCastleMoves(Board board, int position) {
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
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.b
                        && move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)) {
                    castleMoves.add(new Move(position, 62));
                }
            }
            if (board.isWhiteCanCastleQueenSide()) {
                int left1 = 59;
                int left2 = 58;
                int left3 = 57;
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.b
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.b
                        && move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)) {
                    castleMoves.add(new Move(position, 58));
                }
            }
        } else {
            if (board.isBlackCanCastleKingSide()) {
                int right1 = 5;
                int right2 = 6;
                Move move1 = new Move(position, right1);
                Move move2 = new Move(position, right2);
                if (board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.w
                        && move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)) {
                    castleMoves.add(new Move(position, 6));
                }
            }
            if (board.isBlackCanCastleQueenSide()) {
                int left1 = 3;
                int left2 = 2;
                int left3 = 1;
                Move move1 = new Move(position, left1);
                Move move2 = new Move(position, left2);
                if (board.getPieceAt(left3).isEmpty() && board.getPieceAt(move1.getEnd()).getColor() != Color.w
                        && board.getPieceAt(move2.getEnd()).getColor() != Color.w
                        && move1.isMoveLegal(board, color) && move2.isMoveLegal(board, color)
                ) {
                    castleMoves.add(new Move(position, 2));
                }
            }
        }
        return castleMoves;
    }

    @Override
    public List<Move> getMoves(Board board, int position) {
        List<Move> moves = new ArrayList<>();
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
        moves.addAll(getCastleMoves(board, position));
        return moves;
    }

    @Override
    public List<Move> getPseudoMoves(Board board, int position) {
        ArrayList<Move> moves = new ArrayList<>();
        Move move;
        int checkPosition;
        for (int change : Directions.orthogonal) {
            checkPosition = position + change;
            move = new Move(position, checkPosition);
            if (Position.isOnBoard(checkPosition)
                    && board.getPieceAt(checkPosition).color != color
                    && (Position.isSameRow(position, checkPosition) || Position.isSameColumn(position, checkPosition))) {
                moves.add(move);
            }
        }
        for (int change : Directions.diagonal) {
            checkPosition = position + change;
            move = new Move(position, checkPosition);
            if (Position.isOnBoard(checkPosition)
                    && board.getPieceAt(checkPosition).color != color
                    && Position.isDiagonal(position, checkPosition)) {
                moves.add(move);
            }
        }
        moves.addAll(getPseudoCastleMoves(board, position));
        return moves;
    }

}
