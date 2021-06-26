package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

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
    public ArrayList<Move> getMovesNotCheck(Board board) {
        return null;
    }
}
