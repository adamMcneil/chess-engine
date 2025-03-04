package cheekykoala;

import cheekykoala.pieces.Piece;

public class PromotionMove extends Move {

    Piece piece;

    public PromotionMove(Position beginning, Position end, Piece piece) {
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
