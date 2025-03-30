package cheekykoala;

import cheekykoala.pieces.Piece;

public class Move {
    int beginning;
    int end;

    public Move(int beginning, int end) {
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
        return (board.getPieceAt(getBeginning()).isKing()
                && (this.getEnd() - this.getBeginning() == 2
                || this.getEnd() - this.getBeginning() == -2));
    }

    public boolean isCapture(Board board) {
        return !board.getPieceAt(this.end).isEmpty();
    }

    public boolean isInPassingMove(Board board) {
        return (board.getPieceAt(this.getBeginning()).isPawn() && board.getPieceAt(this.getEnd()).isEmpty()
                && this.getBeginning() != getEnd());
    }

    public boolean isUpTwoMove(Board board) {
        return (board.getPieceAt(this.getBeginning()).isPawn()
                && Math.abs(this.getBeginning() - this.getEnd()) == 2);
    }

    public String toString() {
        return Position.toString(beginning) + Position.toString(end);
    }

    public boolean isMoveLegal(Board board, Color color) {
        if (!Position.isOnBoard(end)) {
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

    public int getBeginning() {
        return beginning;
    }

    public int getEnd() {
        return end;
    }
}
