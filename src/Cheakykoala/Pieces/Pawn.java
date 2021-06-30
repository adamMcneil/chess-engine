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
            letter = 'P';
        } else {
            piece = (char) 0x2659;
            letter = 'p';
        }
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean getHasMoved() {
        return !((position.getY() == 1 && color == Color.b) || (position.getY() == 6 && color == Color.w));
    }

    @Override
    protected void setHasMoved() {

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
//        boolean testMove = moveRight.isMoveLegal(board, color) && Board.getCanEnpassant();
//        boolean testCan = Board.getCanEnpassant();
//        boolean comparePositionTest = right.comparePositions(new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY()));
//        Position test = new Position(board.getInPassingSquareX(), board.getInPassingSquareY());
        if ((moveLeft.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(left))) || (moveLeft.isMoveLegal(board, color) && board.getCanEnpassant() && left.comparePositions(new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())))) {
            moves.add(moveLeft);
        }
        if ((moveRight.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(right))) || (moveRight.isMoveLegal(board, color) && board.getCanEnpassant() && right.comparePositions(new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())))) {
            moves.add(moveRight);
        }

        if (!getHasMoved()) {
            checkPosition = new Position(position.getX(), position.getY() + direction * 2);
            move = new Move(position, checkPosition);
            Position oneAbove = new Position(position.getX(), checkPosition.getY() - direction);
            if (move.isMoveLegal(board, color)&& board.getPieceAt(oneAbove).getColor() == Color.g && board.getPieceAt(checkPosition).getColor() == Color.g )
                moves.add(move);
        }

        return moves;
    }
}
