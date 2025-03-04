package Cheakykoala.Pieces;

import Cheakykoala.Board;
import Cheakykoala.Color;
import Cheakykoala.Move;
import Cheakykoala.Position;
import Cheakykoala.PromotionMove;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Color c, Position position) {
        this.valueTable = new int[] {
                0, 0, 0, 0, 0, 0, 0, 0,
                98, 134, 61, 95, 68, 126, 34, -11,
                -6, 7, 26, 31, 65, 56, 25, -20,
                -14, 13, 6, 21, 23, 12, 17, -23,
                -27, -2, -5, 12, 17, 6, 10, -25,
                -26, -4, -4, -10, 3, 3, 33, -12,
                -35, -1, -20, -23, -15, 24, 38, -22,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
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

    public boolean getHasMoved() {
        return !((position.getY() == 1 && color == Color.b) || (position.getY() == 6 && color == Color.w));
    }

    public int getDirection(Color color) {
        int direction = 0;
        if (color == Color.w)
            direction = -1;
        else
            direction = 1;
        return direction;
    }

    public ArrayList<Move> getUpOne(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition;
        int direction = getDirection(color);
        checkPosition = new Position(position.getX(), position.getY() + direction);
        Move move = new Move(position, checkPosition);
        if (move.isMoveLegal(board, color) && board.getPieceAt(checkPosition).getColor() == Color.g
                && checkPosition.getY() != 0 && checkPosition.getY() != 7) {
            moves.add(move);
        }
        return moves;
    }

    public ArrayList<Move> getUpTwo(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition;
        int direction = getDirection(color);
        if (!getHasMoved()) {
            checkPosition = new Position(position.getX(), position.getY() + direction * 2);
            Move move = new Move(position, checkPosition);
            Position oneAbove = new Position(position.getX(), checkPosition.getY() - direction);
            if (move.isMoveLegal(board, color) && board.getPieceAt(oneAbove).getColor() == Color.g
                    && board.getPieceAt(checkPosition).getColor() == Color.g)
                moves.add(move);
        }
        return moves;
    }

    public ArrayList<Move> getAttack(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = getDirection(color);
        Position right = new Position(position.getX() + 1, position.getY() + direction);
        Position left = new Position(position.getX() - 1, position.getY() + direction);
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        if (left.getY() != 0 && left.getY() != 7 && moveLeft.isMoveLegal(board, color)
                && this.isOppositeColor(board.getPieceAt(left))) {
            moves.add(moveLeft);
        }
        if (right.getY() != 0 && right.getY() != 7
                && (moveRight.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(right)))) {
            moves.add(moveRight);
        }
        return moves;
    }

    public ArrayList<Move> getInPassingMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int direction = this.getDirection(color);
        Position left = new Position(position.getX() - 1, position.getY() + direction);
        Position right = new Position(position.getX() + 1, position.getY() + direction);
        Move moveLeft = new Move(position, left);
        Move moveRight = new Move(position, right);
        Position test = board.getInPassingSquare();
        Boolean test2 = board.getCanEnpassant();
        if (moveLeft.isMoveLegal(board, color) && board.getCanEnpassant()
                && left.comparePositions(board.getInPassingSquare())) {
            moves.add(moveLeft);
            // System.out.println ("We just found a inPassingMoveLeft");
        }
        if (moveRight.isMoveLegal(board, color) && board.getCanEnpassant()
                && right.comparePositions(board.getInPassingSquare())) {
            moves.add(moveRight);
            // System.out.println ("We just found a inPassingMoveRight");
        }
        return moves;
    }

    public ArrayList<Move> getPromotionMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        Position checkPosition;
        int direction = this.getDirection(color);
        checkPosition = new Position(position.getX(), position.getY() + direction);
        Move move = new Move(position, checkPosition);
        if (move.isMoveLegal(board, color) && board.getPieceAt(checkPosition).getColor() == Color.g
                && (checkPosition.getY() == 0 || checkPosition.getY() == 7)) {
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
        if ((checkPosition.getY() == 0 || checkPosition.getY() == 7)
                && (moveLeft.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(left)))) {
            Move moveQueen = new PromotionMove(position, left, new Queen(this.color, moveLeft.getEnd()));
            Move moveKnight = new PromotionMove(position, left, new Knight(this.color, moveLeft.getEnd()));
            Move moveRook = new PromotionMove(position, left, new Rook(this.color, moveLeft.getEnd()));
            Move moveBishop = new PromotionMove(position, left, new Bishop(this.color, moveLeft.getEnd()));
            moves.add(moveQueen);
            moves.add(moveKnight);
            moves.add(moveRook);
            moves.add(moveBishop);
        }
        if ((checkPosition.getY() == 0 || checkPosition.getY() == 7)
                && (moveRight.isMoveLegal(board, color) && this.isOppositeColor(board.getPieceAt(right)))) {
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

        moves.addAll(getUpOne(board));
        moves.addAll(getUpTwo(board));
        moves.addAll(getAttack(board));
        moves.addAll(getInPassingMoves(board));
        moves.addAll(getPromotionMoves(board));

        return moves;
    }

    public boolean canMove(Board board) {
        if (getUpOne(board).size() > 0)
            return true;
        if (getUpTwo(board).size() > 0)
            return true;
        if (getAttack(board).size() > 0)
            return true;
        if (getInPassingMoves(board).size() > 0)
            return true;
        if (getPromotionMoves(board).size() > 0)
            return true;
        return false;
    }
}
