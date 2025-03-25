package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Move;
import cheekykoala.Position;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Empty extends Piece {

    public Empty(Position position) {
        this.position = position;
        this.color = color.g;
        this.piece = ' ';
        this.letter = ' ';
    }

    @Override
    public double[] getTableValue() {
        return new double[] {};
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    public ArrayList<Move> getMoves(Board board) {
        return new ArrayList();
    }
}
