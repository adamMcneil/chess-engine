package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(Color c, Position position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x2656;
        } else {
            piece = (char) 0x265C;
        }
    }

    public ArrayList<Move> getMoves(Board board) {
        return null;
    }
}
