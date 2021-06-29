package Cheakykoala;

import Cheakykoala.Pieces.Piece;

public class Move {
    Position beginning;
    Position end;

    public Move(Position beginning, Position end) {
        this.beginning = beginning;
        this.end = end;
    }

    public String toString() {
        return beginning + " --> " + end.toString();
    }

    public boolean isMoveCheck(Board board) {
        if (board.getPieceAt(end).isKing())
            return true;
        return false;
    }

    public boolean isMoveLegal(Board board, Color color) {
//        System.out.println(this);
        if (!end.isOnBoard()) {
            return false;
        }
        Piece moved = board.getPieceAt(beginning);
        Piece taken = board.getPieceAt(end);
        if (moved.isSameColor(taken)) {
            return false;
        }
        Board checkBoard = board.getChild(board, this);
        if (checkBoard.isColorInCheck(color)) {
            return false;
        }
        return true;
    }

    public boolean isMoveLegalNotCheck(Board board, Color color) {
        if (!end.isOnBoard()) {
            return false;
        }
        Piece moved = board.getPieceAt(beginning);
        Piece taken = board.getPieceAt(end);
        if (moved.isSameColor(taken)) {
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