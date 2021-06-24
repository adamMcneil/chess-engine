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
            piece = (char) 0x265C;
        } else {
            piece = (char) 0x2656;
        }
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition = new Position(position.getX() + 1, position.getY());
        Move move = new Move(position, checkPosition);
        while (move.isMoveLegal(board, color)) {
            moves.add(move);
            if (board.getPieceAt(checkPosition).getColor() != Color.g)
                break;
            checkPosition = new Position(checkPosition.getX() + 1, position.getY());
            move = new Move(position, checkPosition);
        }

        checkPosition = new Position(position.getX() - 1, position.getY());
        move = new Move(position, checkPosition);
        while (move.isMoveLegal(board, color)) {
            moves.add(move);
            if (board.getPieceAt(checkPosition).getColor() != Color.g)
                break;
            checkPosition = new Position(checkPosition.getX() - 1, position.getY());
            move = new Move(position, checkPosition);
        }

        checkPosition = new Position(position.getX(), position.getY() + 1);
        while (move.isMoveLegal(board, color)) {
            move = new Move(position, checkPosition);
            moves.add(move);
            if (board.getPieceAt(checkPosition).getColor() != Color.g)
                break;
            checkPosition = new Position(position.getX(), checkPosition.getY() + 1);
            move = new Move(position, checkPosition);
        }

        checkPosition = new Position(position.getX(), position.getY() - 1);
        while (move.isMoveLegal(board, color)) {
            move = new Move(position, checkPosition);
            moves.add(move);
            if (board.getPieceAt(checkPosition).getColor() != Color.g)
                break;
            checkPosition = new Position(position.getX(), checkPosition.getY() - 1);
            move = new Move(position, checkPosition);
        }
        return moves;
    }
}
