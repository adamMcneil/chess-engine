package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;

import java.util.List;

public class Empty extends Piece {
    private static final Empty INSTANCE = new Empty(0);

    public static Empty getInstance() {
        return INSTANCE;
    }

    public Empty(int position) {
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
        return new double[]{};
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public List<Move> getMoves(Board board) {
        return List.of();
    }

    @Override
    public List<Move> getPseudoMoves(Board board) {
        return List.of();
    }
}
