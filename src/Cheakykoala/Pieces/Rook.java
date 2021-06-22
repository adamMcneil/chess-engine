package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

public class Rook extends Piece {
    Position position;
    char piece;
    Color color;

    public Rook(Color c, Position position) {
        this.position = position;
        if (c == Color.w) {
            piece = (char) 0x2656;
        } else {
            piece = (char) 0x265C;
        }
    }

    public Move[] getMoves(Board board) {
        return new Move[0];
    }
}
