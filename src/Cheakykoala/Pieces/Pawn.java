package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

public class Pawn extends Piece {
    Position position;
    char piece;
    Color color;

    public Pawn(Color c, Position position) {
        this.position = position;
        if (c == Color.w) {
            piece = (char) 0x2659;
        } else {
            piece = (char) 0x265F;
        }
    }

    public Move[] getMoves(Board board) {
        return new Move[0];
    }
}
