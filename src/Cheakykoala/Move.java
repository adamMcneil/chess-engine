package Cheakykoala;

import Cheakykoala.Pieces.Piece;

public class Move {
    Position beginning;
    Position end;

    public Move(Position beginning, Position end) {
        this.beginning = beginning;
        this.end = end;
    }



    public void printMove() {
        System.out.println(beginning + "        " + end.toString());
    }

    public boolean isMoveCheck(Board board) {
        if (board.getPieceAt(end).isKing())
            return true;
        return false;
    }

    public boolean isMoveLegal(Board board, Color color) {
        if (end.isOnBoard()) {
            Piece moved = board.getPieceAt(beginning);
            Piece taken = board.getPieceAt(end);
            moved.move(board, this);
            if (!board.isColorInCheck(color) && taken.getColor() != color) {
               moved.undoMove(board, this, taken);
                return true;
            }
           moved.undoMove(board, this, taken);
        }
        return false;
    }

    public Position getBeginning() {
        return beginning;
    }

    public Position getEnd() {
        return end;
    }
}