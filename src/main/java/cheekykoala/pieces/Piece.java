package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;

public abstract class Piece {
    int position;
    char piece;
    char letter;
    Color color;

    public Piece() {
    }

    public Piece(Piece other) {
        this.position = other.position;
        this.piece = other.piece;
        this.letter = other.letter;
        this.color = other.color;
    }

    public abstract Piece copy();

    public double getSquareEval() {
        if (color == Color.w) {
            return getValue(position);
        } else {
            return getValue(63 - position);
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
            case 'k' -> -20000;

            case 'R' -> 500;
            case 'N' -> 320;
            case 'B' -> 330;
            case 'Q' -> 900;
            case 'P' -> 100;
            case 'K' -> 20000;
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

    public abstract ArrayList<Move> getMoves(Board board);

    public ArrayList<Move> getPromotionMoves(Board board) {
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public char getLetter() {
        return this.letter;
    }

    public static Piece makePiece(char letter, int i) {
        Piece piece = null;
        return switch (letter) {
            case 'r' -> {
                piece = new Rook(Color.b, i);
                yield piece;
            }
            case 'R' -> {
                piece = new Rook(Color.w, i);
                yield piece;
            }
            case 'n' -> {
                piece = new Knight(Color.b, i);
                yield piece;
            }
            case 'N' -> {
                piece = new Knight(Color.w, i);
                yield piece;
            }
            case 'b' -> {
                piece = new Bishop(Color.b, i);
                yield piece;
            }
            case 'B' -> {
                piece = new Bishop(Color.w, i);
                yield piece;
            }
            case 'q' -> {
                piece = new Queen(Color.b, i);
                yield piece;
            }
            case 'Q' -> {
                piece = new Queen(Color.w, i);
                yield piece;
            }
            case 'k' -> {
                piece = new King(Color.b, i);
                yield piece;
            }
            case 'K' -> {
                piece = new King(Color.w, i);
                yield piece;
            }
            case 'p' -> {
                piece = new Pawn(Color.b, i);
                yield piece;
            }
            case 'P' -> {
                piece = new Pawn(Color.w, i);
                yield piece;
            }
            default -> piece;
        };
    }
}
