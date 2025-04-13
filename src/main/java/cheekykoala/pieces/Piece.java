package cheekykoala.pieces;

import cheekykoala.*;

import java.util.List;

public abstract class Piece {
    static public final int numberOfBits = 4;
    static private final Piece[] pieces = {
            Empty.getInstance(),

            Pawn.getWhitePiece(),
            Rook.getWhitePiece(),
            Knight.getWhitePiece(),
            Bishop.getWhitePiece(),
            Queen.getWhitePiece(),
            King.getWhitePiece(),

            Pawn.getBlackPiece(),
            Rook.getBlackPiece(),
            Knight.getBlackPiece(),
            Bishop.getBlackPiece(),
            Queen.getBlackPiece(),
            King.getBlackPiece()};

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
            default -> 0; // Empty piece
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

    public static Piece getPieceFromBits(int bits) {
        if (bits < 0 || bits >= pieces.length) {
            throw new IllegalArgumentException("Invalid piece bits: " + bits);
        }
        return pieces[bits];
    }

    public static int getBitsFromPiece(Piece piece) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == piece) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid piece: " + piece);
    }

    public static Piece getPiece(char letter) {
        return switch (letter) {
            case 'r' -> Rook.getBlackPiece();
            case 'R' -> Rook.getWhitePiece();
            case 'n' -> Knight.getBlackPiece();
            case 'N' -> Knight.getWhitePiece();
            case 'b' -> Bishop.getBlackPiece();
            case 'B' -> Bishop.getWhitePiece();
            case 'q' -> Queen.getBlackPiece();
            case 'Q' -> Queen.getWhitePiece();
            case 'k' -> King.getBlackPiece();
            case 'K' -> King.getWhitePiece();
            case 'p' -> Pawn.getBlackPiece();
            case 'P' -> Pawn.getWhitePiece();
            default -> throw new IllegalArgumentException("Invalid piece letter: " + letter);
        };
    }
}
