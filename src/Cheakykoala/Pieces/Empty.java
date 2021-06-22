package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

public class Empty extends Piece {
    Position position;
    char piece;
    Color color;

    public Empty(Color c, Position position) {
        this.position = position;
        piece = ' ';
    }

    public Move[] getMoves(Board board) {
        return new Move[0];
    }
}
