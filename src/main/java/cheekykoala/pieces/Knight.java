package cheekykoala.pieces;

import cheekykoala.Board;
import cheekykoala.Color;
import cheekykoala.Move;
import cheekykoala.Position;

import java.util.ArrayList;

public class Knight extends Piece {
        private static final double[] valueTable = new double[] {
        -50, -40, -30, -30, -30, -30, -40, -50,
                -40, -20, 0, 0, 0, 0, -20, -40,
                -30, 0, 10, 15, 15, 10, 0, -30,
                -30, 5, 15, 20, 20, 15, 5, -30,
                -30, 0, 15, 20, 20, 15, 0, -30,
                -30, 5, 10, 15, 15, 10, 5, -30,
                -40, -20, 0, 5, 5, 0, -20, -40,
                -50, -40, -30, -30, -30, -30, -40, -50,
    };
    public Knight(Color c, Position position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265E;
            letter = 'N';
        }
        if (c == Color.b) {
            piece = (char) 0x2658;
            letter = 'n';
        }
    }

    @Override
    public double[] getTableValue() {
        return valueTable;
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] baseMoves = {
                { 2, 1 },
                { 2, -1 },
                { 1, 2 },
                { 1, -2 },
                { -1, 2 },
                { -1, -2 },
                { -2, 1 },
                { -2, -1 },
        };
        for (int[] arr : baseMoves) {
            Position checkPosition = new Position(position.getX() + arr[0], position.getY() + arr[1]);
            Move move = new Move(position, checkPosition);
            if (move.isMoveLegal(board, color)) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public boolean isKnight() {
        return true;
    }
}
