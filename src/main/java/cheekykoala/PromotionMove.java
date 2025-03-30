package cheekykoala;

import cheekykoala.pieces.Piece;

public class PromotionMove extends Move {

    Piece piece;

    public PromotionMove(int beginning, int end, Piece piece) {
        super(beginning, end);
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean isPromotionMove(Move move) {
        return true;
    }
}
