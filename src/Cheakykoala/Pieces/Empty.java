package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class Empty extends Piece {

    public Empty(Color c, Position position) {
        this.position = position;
        piece = ' ';
    }

    public ArrayList<Move> getMoves(Board board) {
        return null;
    }
}
