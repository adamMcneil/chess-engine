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
}
