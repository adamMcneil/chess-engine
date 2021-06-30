package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class King extends Piece {

    public King(Color c, Position position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265A;
            letter = 'K';
        } else {
            piece = (char) 0x2654;
            letter = 'k';
        }
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public void setHasMoved() {
        hasMoved = true;
    }

    @Override
    public boolean getHasMoved() {
        return hasMoved;
    }

    public char returnPiece() {
        return piece;
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] baseMoves = {
                {1, 1},
                {1, 0},
                {1, -1},
                {0, -1},
                {-1, -1},
                {-1, 0},
                {-1, 1},
                {0, 1},
        };
        for (int[] arr : baseMoves) {
            Position checkPosition = new Position(position.getX() + arr[0], position.getY() + arr[1]);
            Move move = new Move(position, checkPosition);
            if (move.isMoveLegal(board, color)) {
                moves.add(move);
            }
        }
        moves.addAll(castleMoves(board));
//        for (Move move : moves) {
//            System.out.println(move);
//        }
        return moves;
    }

    public ArrayList<Move> castleMoves(Board board) {
        ArrayList<Move> castleMoves = new ArrayList<>();
        int moveState;
        if (color == Color.w) {
            moveState = board.setWhiteCastleMoveState();
            if ((moveState == 0 || moveState == 1)) {
                castleMoves.add(new Move(this.position, new Position(2, 7)));
            }
            if (moveState == 2) {
                castleMoves.add(new Move(this.position, new Position(6, 7)));
            }
        } else {
            moveState = board.setBlackCastleMoveState();
            if (moveState == 0 || moveState == 1) {
                castleMoves.add(new Move(this.position, new Position(2, 0)));
            }
            if (moveState == 2) {
                castleMoves.add(new Move(this.position, new Position(6, 0)));
            }
        }
        return castleMoves;
    }
}
