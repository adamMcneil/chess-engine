package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Color c, Position position) {
        this.position = position;
        this.color = c;
        if (c == Color.w) {
            piece = (char) 0x265F;
            letter = 'W';
        } else {
            piece = (char) 0x2659;
            letter = 'p';
        }
    }

    public boolean hasMoved() {
        return !((position.getY() == 1 && color == Color.b) || (position.getY() == 6 && color == Color.w));
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition;
        int direction;
        if (color == Color.w)
            direction = -1;
        else
            direction = 1;

        checkPosition = new Position(position.getX(), position.getY() + direction);
        Move move = new Move(position, checkPosition);
        if (move.isMoveLegal(board, color) && board.getPieceAt(checkPosition).getColor() == Color.g) {
            moves.add(move);
        }

        Position right = new Position(position.getX() + 1, position.getY() + direction);
        Position left = new Position(position.getX() - 1, position.getY() + direction);
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        if (moveLeft.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(left))) {
            moves.add(moveLeft);
        }
        if (moveRight.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(right))) {
            moves.add(moveRight);
        }

        if (!hasMoved()) {
            checkPosition = new Position(position.getX(), position.getY() + direction * 2);
            move = new Move(position, checkPosition);
            Position oneAbove = new Position(position.getX(), checkPosition.getY() - direction);
            if (move.isMoveLegal(board, color)&& board.getPieceAt(oneAbove).getColor() == Color.g && board.getPieceAt(checkPosition).getColor() == Color.g )
                moves.add(move);
        }
        return moves;
    }

    public ArrayList<Move> getMovesNotCheck(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition;
        int direction;
        if (color == Color.w)
            direction = -1;
        else
            direction = 1;

        checkPosition = new Position(position.getX(), position.getY() + direction);
        Move move = new Move(position, checkPosition);
        if (move.isMoveLegalNotCheck(board, color)) {
            moves.add(move);
        }
        Position right = new Position(position.getX() + 1, position.getY() + direction);
        Position left = new Position(position.getX() - 1, position.getY() + direction);
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        if (moveLeft.isMoveLegalNotCheck(board, color) && this.isOppositeColor(board.getPieceAt(left))) {
            moves.add(moveLeft);
        }
        if (moveRight.isMoveLegalNotCheck(board, color) && this.isOppositeColor(board.getPieceAt(right))) {
            moves.add(moveRight);
        }
        if (!hasMoved()) {
            checkPosition = new Position(position.getX(), position.getY() + direction * 2);
            move = new Move(position, checkPosition);
            if (move.isMoveLegalNotCheck(board, color))
                moves.add(move);
        }
        return moves;
    }
}
