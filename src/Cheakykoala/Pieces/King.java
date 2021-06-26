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

    public boolean isinCheck(Board board){
        for (Piece[] pieces : board.getBoard()){
            for (Piece piece : pieces){
                for ( Move move : piece.getMoves(board)){
                    if (isOtherColor(piece)){
                        if (move.getEnd() == this.position){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isKing() {
        return true;
    }

    public char returnPiece(){
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
        return moves;
    }

    public ArrayList<Move> getMovesNotCheck(Board board) {
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
            if (move.isMoveLegalNotCheck(board, color)) {
                moves.add(move);
            }
        }
        return moves;
    }
}
