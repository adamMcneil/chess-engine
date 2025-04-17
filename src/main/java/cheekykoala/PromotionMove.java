package cheekykoala;

import cheekykoala.pieces.Piece;

public class PromotionMove extends Move {

    Piece piece;

    public PromotionMove(int beginning, int end, Piece piece) {
        super(beginning, end, MoveType.promotion);
        this.piece = piece;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean isPromotionMove() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + piece.getLetter();
    }
}
