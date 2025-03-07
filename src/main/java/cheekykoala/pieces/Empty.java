package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Move;
import cheekykoala.Position;

import java.util.ArrayList;

public class Empty extends Piece {

    public Empty(Position position) {
        this.position = position;
        this.color = color.g;
        this.piece = ' ';
        this.letter = ' ';
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    public ArrayList<Move> getMoves(Board board) {
        return new ArrayList();
    }

    @Override
    public boolean canMove(Board board) {
        return false;
    }
}
