package cheekykoala;

import cheekykoala.pieces.Piece;

public class Move {
    int beginning;
    int end;

    public Move(int beginning, int end) {
        this.beginning = beginning;
        this.end = end;
    }

    public static Move moveFromString(String string, Board board) {
        Move move;
        int start = (Position.charToInt(string.charAt(0)) + 8 * (8 - Character.getNumericValue(string.charAt(1))));
        int end = (Position.charToInt(string.charAt(2)) + 8 * (8 - Character.getNumericValue(string.charAt(3))));

        if (string.length() == 5) {
            char letter = string.charAt(4);
            Color color = board.getPieceAt(start).getColor();
            if (color == Color.w) {
                letter = Character.toUpperCase(letter);
            }
            move = new PromotionMove(start, end, Piece.makePiece(letter, end));

        } else {
            move = new Move(start, end);
        }
        return move;
    }

    public Piece getPiece() {
        return null;
    }

    public boolean isPromotionMove() {
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
        return (board.getPieceAt(getBeginning()).isPawn() && board.getPieceAt(getEnd()).isEmpty()
                && Position.getColumn(getEnd()) != Position.getColumn(getBeginning()));
    }

    public boolean isUpTwoMove(Board board) {
        return (board.getPieceAt(this.getBeginning()).isPawn()
                && Math.abs(getBeginning() - getEnd()) == 16);
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
        Board checkBoard = board.getChild(this); // TODO: we could just undo the move here
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
