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
        } else {
            piece = (char) 0x2654;
        }
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
        Position checkPosition = new Position(position.getX() + 1, position.getY() + 1);
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);

        }
        checkPosition = new Position(position.getX() - 1, position.getY() + 1);
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);

        }
        checkPosition = new Position(position.getX() + 1, position.getY() - 1);
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);

        }
        checkPosition = new Position(position.getX() - 1, position.getY() - 1);
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);

        }
        checkPosition = new Position(position.getX() + 1, position.getY());
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);
        }
        checkPosition = new Position(position.getX() - 1, position.getY());
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);

        }
        checkPosition = new Position(position.getX(), position.getY() + 1);
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);

        }
        checkPosition = new Position(position.getX(), position.getY() - 1);
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);

        }
        return moves;

    }
    public ArrayList<Move> getMovesBetter(Board board) {
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
            while (move.isMoveLegal(board, color)) {
                move = new Move(position, checkPosition);
                moves.add(move);
                if (board.getPieceAt(checkPosition).getColor() != Color.g)
                    break;
                checkPosition = new Position(checkPosition.getX() + arr[0], checkPosition.getY() + arr[1]);
                move = new Move(position, checkPosition);
            }
        }
        return moves;
    }

}
