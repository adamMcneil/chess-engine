package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

public class King extends Piece {
    Position position;
    char piece;
    Color color;

    public King (Color c, Position position) {
        this.position = position;
        if (c == Color.w) {
            piece = (char) 0x2654;
        } else {
            piece = (char) 0x265A;
        }
    }

    public Move[] getMoves(Board board) {
        return null;
    }
}
