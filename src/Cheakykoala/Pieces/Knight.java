package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Color c, Position position) {
        this.position = position;
        this.color = Color.w;
        if (c == Color.w) {
            piece = (char) 0x265E;
        }
        if (c == Color.b) {
            piece = (char) 0x2658;
        }
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int j;
        for (int i = -2; i < 3; i++) {
            if (Math.abs(i) == 2)
                j = 1;
            else
                j = 2;
            if (i != 0) {
                Position end = new Position(position.getX() + i, position.getY() + j);
                if (end.getX() < 8 && end.getX() > -1 && end.getY() < 8 && end.getY() > -1) {
                    if (board.get(end.getX(),end.getY()).color != color){
                        Move move = new Move(position, end);
                        moves.add(move);
                    }
                }

                end = new Position(position.getX() + i, position.getY() - j);
                if (end.getX() < 8 && end.getX() > -1 && end.getY() < 8 && end.getY() > -1) {
                    if (board.get(end.getX(),end.getY()).color != color){
                        Move move = new Move(position, end);
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }
}
