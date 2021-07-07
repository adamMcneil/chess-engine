package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;
import Cheakykoala.PromotionMove;

import java.util.ArrayList;

public class Pawn extends Piece {
    int count = 0;

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

    public ArrayList<Move> getPromotionMoves(Board board){
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition;
        int direction;
        if (color == Color.w)
            direction = -1;
        else
            direction = 1;

        checkPosition = new Position(position.getX(), position.getY() + direction);
        Move move = new Move(position, checkPosition);
        if (move.isMoveLegal(board, color) && board.getPieceAt(checkPosition).getColor() == Color.g && checkPosition.getY() == 0 || checkPosition.getY() == 7) {
            Move moveQueen = new PromotionMove(position, checkPosition, new Queen(this.color, move.getEnd()));
            Move moveKnight = new PromotionMove(position, checkPosition, new Knight(this.color, move.getEnd()));
            Move moveRook = new PromotionMove(position, checkPosition, new Rook(this.color, move.getEnd()));
            Move moveBishop = new PromotionMove(position, checkPosition, new Bishop(this.color, move.getEnd()));
            moves.add(moveQueen);
            moves.add(moveKnight);
            moves.add(moveRook);
            moves.add(moveBishop);
        }

        Position right = new Position(position.getX() + 1, position.getY() + direction);
        Position left = new Position(position.getX() - 1, position.getY() + direction);
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        if ((checkPosition.getY() == 0 || checkPosition.getY() == 7) && (moveLeft.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(left))) || (moveLeft.isMoveLegal(board, color) && board.getCanEnpassant() && left.comparePositions(new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())))) {
            Move moveQueen = new PromotionMove(position, left, new Queen(this.color, moveLeft.getEnd()));
            Move moveKnight = new PromotionMove(position, left, new Knight(this.color, moveLeft.getEnd()));
            Move moveRook = new PromotionMove(position, left, new Rook(this.color, moveLeft.getEnd()));
            Move moveBishop = new PromotionMove(position, left, new Bishop(this.color, moveLeft.getEnd()));
            moves.add(moveQueen);
            moves.add(moveKnight);
            moves.add(moveRook);
            moves.add(moveBishop);
        }
        if ((checkPosition.getY() == 0 || checkPosition.getY() == 7) && (moveRight.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(right))) || (moveRight.isMoveLegal(board, color) && board.getCanEnpassant() && right.comparePositions(new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())))) {
            Move moveQueen = new PromotionMove(position, right, new Queen(this.color, moveRight.getEnd()));
            Move moveKnight = new PromotionMove(position, right, new Knight(this.color, moveRight.getEnd()));
            Move moveRook = new PromotionMove(position, right, new Rook(this.color, moveRight.getEnd()));
            Move moveBishop = new PromotionMove(position, right, new Bishop(this.color, moveRight.getEnd()));
            moves.add(moveQueen);
            moves.add(moveKnight);
            moves.add(moveRook);
            moves.add(moveBishop);
        }
        return moves;
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
        if (move.isMoveLegal(board, color) && board.getPieceAt(checkPosition).getColor() == Color.g && checkPosition.getY() != 0 && checkPosition.getY() != 7) {
            moves.add(move);
        }

        Position right = new Position(position.getX() + 1, position.getY() + direction);
        Position left = new Position(position.getX() - 1, position.getY() + direction);
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        if (checkPosition.getY() != 0 && checkPosition.getY() != 7 && (moveLeft.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(left))) || (moveLeft.isMoveLegal(board, color) && board.getCanEnpassant() && left.comparePositions(new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())))) {
            moves.add(moveLeft);
            if (board.getPieceAt(moveLeft.getEnd()).isEmpty()){
               board.increaseCount();
            }
        }
        if (checkPosition.getY() != 0 && checkPosition.getY() != 7 && (moveRight.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(right))) || (moveRight.isMoveLegal(board, color) && board.getCanEnpassant() && right.comparePositions(new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())))) {
            moves.add(moveRight);
            if (board.getPieceAt(moveRight.getEnd()).isEmpty()){
                board.increaseCount();
            }
        }

        if (!getHasMoved()) {
            checkPosition = new Position(position.getX(), position.getY() + direction * 2);
            move = new Move(position, checkPosition);
            Position oneAbove = new Position(position.getX(), checkPosition.getY() - direction);
            if (move.isMoveLegal(board, color)&& board.getPieceAt(oneAbove).getColor() == Color.g && board.getPieceAt(checkPosition).getColor() == Color.g )
                moves.add(move);
        }
        moves.addAll(getPromotionMoves(board));

        return moves;
    }
}
