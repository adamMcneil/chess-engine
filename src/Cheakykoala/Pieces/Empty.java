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
    }

    public ArrayList<Move> getMoves(Board board) {
        return null;
    }
}
