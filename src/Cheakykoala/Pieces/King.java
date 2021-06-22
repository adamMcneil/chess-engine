package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class King extends Piece {

    public King (Color c, Position position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x2654;
        } else {
            piece = (char) 0x265A;
        }
    }

    public ArrayList<Move> getMoves(Board board) {
        return null;
    }
}
