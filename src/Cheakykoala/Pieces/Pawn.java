package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class Pawn extends Piece {


    Position ogPosition;

    public Pawn(Color c, Position position) {
        ogPosition = position;
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265F;
        } else {
            piece = (char) 0x2659;
        }
    }

    public boolean hasMoved() {
        if (position != ogPosition) {
            return true;
        }
        return false;
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition;
        if (color == Color.b) {
            checkPosition = new Position(position.getX(), position.getY() + 1);
        } else {
            checkPosition = new Position(position.getX(), position.getY() - 1);
        }
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
            Move move = new Move(position, checkPosition);
            moves.add(move);
        }
        if (color == Color.b) {
            checkPosition = new Position(position.getX() + 1, position.getY() + 1);
        } else {
            checkPosition = new Position(position.getX() + 1, position.getY()-1);
        }
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color && board.getPieceAt(checkPosition).getColor() != color.g) {
            Move move = new Move(position, checkPosition);
            moves.add(move);
        }
        if (color == Color.b) {
            checkPosition = new Position(position.getX() - 1, position.getY() + 1);
        } else {
            checkPosition = new Position(position.getX() - 1, position.getY()-1);
        }
        if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color && board.getPieceAt(checkPosition).getColor() != color.g) {
            Move move = new Move(position, checkPosition);
            moves.add(move);
        }
        if (!hasMoved()) {
            if (color == Color.b) {
                checkPosition = new Position(position.getX(), position.getY() + 2);
            } else {
                checkPosition = new Position(position.getX(), position.getY() - 2);
            }
            if (checkPosition.isOnBoard() && board.getPieceAt(checkPosition).getColor() != color) {
                Move move = new Move(position, checkPosition);
                moves.add(move);
            }
        }
        return moves;
    }
}
