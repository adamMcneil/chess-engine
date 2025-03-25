package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;
import cheekykoala.Position;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Empty extends Piece {
    private static final Empty INSTANCE = new Empty(new Position(0, 0));

    public static Empty getInstance() {
        return INSTANCE;
    }

    public Empty(Position position) {
        this.position = position;
        this.color = Color.g;
        this.piece = ' ';
        this.letter = ' ';
    }

    @Override
    public Piece copy() {
        return INSTANCE;
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
