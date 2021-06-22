package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

public class Bishop extends Piece {
    Position position;
    char piece;
    Color color;

    public Bishop(Color c, Position position) {
        this.position = position;
        if (c == Color.w) {
            piece = (char) 0x2657;
        } else {
            piece = (char) 0x265D;
        }
    }

    public Move[] getMoves(Board board) {
        return null;
    }
}
