package cheekykoala.pieces;

import cheekykoala.*;

import java.util.List;

public abstract class Piece {
    char piece;
    char letter;
    Color color;

    public Piece() {}

    public double getSquareEval(int position) {
        if (color == Color.w) {
            return getValue(position);
        } else {
            return -getValue(63 - position);
        }
    }

    public double getValue(int index) {
        if (this.isEmpty()) {
            return 0;
        }
        return getTableValue()[index];
    }

    public abstract double[] getTableValue();

    public double getPieceEval() {
        return switch (letter) {
            case 'r' -> -500;
            case 'n' -> -320;
            case 'b' -> -330;
            case 'q' -> -900;
            case 'p' -> -100;
            case 'k' -> -100000;

            case 'R' -> 500;
            case 'N' -> 320;
            case 'B' -> 330;
            case 'Q' -> 900;
            case 'P' -> 100;
            case 'K' -> 100000;
            default -> 0;
        };
    }

    public boolean isOppositeColor(Piece piece) {
        return (this.color == Color.w && piece.getColor() == Color.b)
                || (this.color == Color.b && piece.getColor() == Color.w);
    }

    public boolean isSameColor(Piece piece) {
        return color == piece.getColor();
    }

    public abstract List<Move> getMoves(Board board, int position);

    public abstract List<Move> getPseudoMoves(Board board, int position);

    public List<Move> getPromotionMoves(Board board, int position) {
        return null;
    }

    public boolean isKing() {
        return false;
    }

    public boolean isQueen() {
        return false;
    }

    public boolean isBishop() {
        return false;
    }

    public boolean isKnight() {
        return false;
    }

    public boolean isPawn() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isRook() {
        return false;
    }

    public char getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }

    public char getLetter() {
        return this.letter;
    }

    public static Piece getPiece(char letter) {
        Piece piece = null;
        return switch (letter) {
            case 'r' -> {
                piece = Rook.getBlackPiece();
                yield piece;
            }
            case 'R' -> {
                piece = Rook.getWhitePiece();
                yield piece;
            }
            case 'n' -> {
                piece = Knight.getBlackPiece();
                yield piece;
            }
            case 'N' -> {
                piece = Knight.getWhitePiece();
                yield piece;
            }
            case 'b' -> {
                piece = Bishop.getBlackPiece();
                yield piece;
            }
            case 'B' -> {
                piece = Bishop.getWhitePiece();
                yield piece;
            }
            case 'q' -> {
                piece = Queen.getBlackPiece();
                yield piece;
            }
            case 'Q' -> {
                piece = Queen.getWhitePiece();
                yield piece;
            }
            case 'k' -> {
                piece = King.getBlackPiece();
                yield piece;
            }
            case 'K' -> {
                piece = King.getWhitePiece();
                yield piece;
            }
            case 'p' -> {
                piece = Pawn.getBlackPiece();
                yield piece;
            }
            case 'P' -> {
                piece = Pawn.getWhitePiece();
                yield piece;
            }
            default -> piece;
        };
    }
}
