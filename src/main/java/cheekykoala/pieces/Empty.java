package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;

import java.util.List;
import java.util.function.Predicate;

public class Empty extends Piece {
    private static final Empty INSTANCE = new Empty();

    public static Empty getInstance() {
        return INSTANCE;
    }

    public Empty() {
        this.color = Color.g;
        this.piece = ' ';
        this.letter = ' ';
    }

    @Override
    public double[] getTableValue() {
        return new double[]{};
    }

    public static Piece getWhitePiece() {
        return INSTANCE;
    }

    public static Piece getBlackPiece() {
        return INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public List<Move> getMoves(Board board, int position, Predicate<Move> filter) {
        return List.of();
    }
}
