package cheekykoala;

import cheekykoala.pieces.Piece;

public class Move {
    Position beginning;
    Position end;

    public Move(Position beginning, Position end) {
        this.beginning = beginning;
        this.end = end;
    }

    public Piece getPiece() {
        return null;
    }

    public boolean isPromotionMove(Move move) {
        return false;
    }

    public boolean isCastleMove(Board board) {
        return (board.getPieceAt(this.getBeginning()).isKing()
                && (this.getEnd().getX() - this.getBeginning().getX() == 2
                || this.getEnd().getX() - this.getBeginning().getX() == -2));
    }

    public boolean isCapture(Board board) {
        return !board.getPieceAt(this.end).isEmpty();
    }

    public boolean isInPassingMove(Board board) {
        return (board.getPieceAt(this.getBeginning()).isPawn() && board.getPieceAt(this.getEnd()).isEmpty()
                && this.getBeginning().getX() != this.getEnd().getX());
    }

    public boolean isUpTwoMove(Board board) {
        return (board.getPieceAt(this.getBeginning()).isPawn()
                && Math.abs(this.getBeginning().getY() - this.getEnd().getY()) == 2);
    }

    public String toString() {
        return beginning + " --> " + end.toString();
    }

    public boolean isMoveLegal(Board board, Color color) {
        if (!end.isOnBoard()) {
            return false;
        }
        Piece moved = board.getPieceAt(beginning);
        Piece taken = board.getPieceAt(end);
        if (moved.isSameColor(taken)) {
            return false;
        }
        Board checkBoard = board.getChild(this);
        if (checkBoard.isColorInCheck(color)) {
            return false;
        }
        return true;
    }

    public Position getBeginning() {
        return beginning;
    }

    public Position getEnd() {
        return end;
    }
}
