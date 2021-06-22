package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

public class Queen extends Piece {
    Position position;
    char piece;
    Color color;

    public Queen(Color c, Position position) {
        this.position = position;
        if (c == Color.w) {
            piece = (char) 0x2655;
        } else {
            piece = (char) 0x265B;
        }
    }

    public Move[] getMoves(Board board) {
        return new Move[0];
    }
}
