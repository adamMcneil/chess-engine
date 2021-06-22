package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

public class Knight extends Piece {
    char piece;
    Position position;

    public Knight(Color c, Position position) {
        this.position = position;
        if (c == Color.w) {
            piece = (char) 0x2658;
        } else {
            piece = (char) 0x265E;
        }
    }

    public Move[] getMove() {
        Move move[] = new Move[2];
        for (int i = -2; i < 3; i++) {

        }
        return move;
    }

    public Move[] getMoves(Board board) {
        return new Move[0];
    }
}
