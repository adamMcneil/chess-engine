package Cheakykoala.Pieces;

import Cheakykoala.*;

import java.util.ArrayList;

public abstract class Piece {

    Position position;
    char piece;
    char letter;
    Color color;
    boolean hasMoved = false;

    public Piece() {

    }

    public Color getOppositeColor() {
        if (this.color == Color.g) {
            return Color.g;
        } else if (this.color == Color.w) {
            return Color.b;
        } else {
            return Color.w;
        }
    }

    public boolean isOtherColor(Piece piece) {
        if (this.color != piece.getColor()) {
            return true;
        } else
            return false;
    }

    public boolean isOppositeColor(Piece piece) {
        return (this.color == Color.w && piece.getColor() == Color.b) || (this.color == Color.b && piece.getColor() == Color.w);
    }

    public boolean isSameColor(Piece piece) {
        return color == piece.getColor();
    }

    public abstract ArrayList<Move> getMoves(Board board);

    public ArrayList<Move> getPromotionMoves(Board board){
        return null;
    }

    public boolean isKing() {
        return false;
    }

    public boolean isQueen() {
        return false;
    }

    public boolean isBishop() {
        return false;
    }

    public boolean isKnight() {
        return false;
    }

    public boolean isPawn() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isRook() {
        return false;
    }

    public char getPiece() {
        return piece;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    protected void setHasMoved() {
        this.hasMoved = true;
    }

    public boolean isCastleMove(Move move, Board board) {
        if (board.getPieceAt(move.getBeginning()).isKing() && Math.abs(move.getEnd().getX() - move.getBeginning().getX()) == 2) {
            return true;
        }
        return false;
    }

    public void castleMove(Move move, Board board) {
        int x;
        int x1;
        int x2;
        int y = move.getBeginning().getY();
        if (move.getEnd().getX() < 4) {
            x = 3;
            x1 = 2;
            x2 = 0;
        } else {
            x = 5;
            x1 = 6;
            x2 = 7;
        }
        Position movedTo = new Position(x1, y);
        board.addPiece(movedTo, this);
        board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
        movedTo = new Position(x, y);
        board.addPiece(movedTo, board.get(x2, y));
        Position movedFrom = new Position(x2, y);
        board.addPiece(movedFrom, new Empty(movedFrom));
    }

    public void promotionMove(Move move, Board board){
        board.addPiece(move.getEnd(), move.getPiece());
        board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
    }

    public void move(Board board, Move move) {
        if (move.isPromotionMove(move)){
            promotionMove(move, board);
            board.setCanEnpassant(false);
            return;
        }
        if (isCastleMove(move, board)) {
            castleMove(move, board);
            if (color == Color.w) {
                board.increaseWhiteMoveState(3);
            } else {
                board.increaseBlackMoveState(3);
            }
            board.setCanEnpassant(false);
            return;
        }
        if (this.isKing()) {
            if (this.color == Color.w) {
                board.increaseWhiteMoveState(3);
            } else {
                board.increaseBlackMoveState(3);
            }
        }
        if (this.isRook()) {
            if (this.color == Color.w) {
                if (this.position.getX() == 0 && this.position.getY() == 7 && board.getWhiteCastleMoveState() != 2) {
                    board.increaseWhiteMoveState(2);
                } else if (this.position.getX() == 7 && this.position.getY() == 7 && board.getWhiteCastleMoveState() != 1) {
                    board.increaseWhiteMoveState(1);
                }
            }
            if (this.color == Color.b) {
                if (this.position.getX() == 0 && this.position.getY() == 0 && board.getBlackCastleMoveState() != 2) {
                    board.increaseBlackMoveState(2);
                } else if (this.position.getX() == 7 && this.position.getY() == 0 && board.getBlackCastleMoveState() != 1) {
                    board.increaseBlackMoveState(1);
                }
            }
        }

        if (move.getEnd().getX() == 0 && move.getEnd().getY() == 7 && board.getWhiteCastleMoveState() != 2) {
            board.increaseWhiteMoveState(2);
        } else if (move.getEnd().getX() == 7 && move.getEnd().getY() == 7 && board.getWhiteCastleMoveState() != 1) {
            board.increaseWhiteMoveState(1);
        } else if (move.getEnd().getX() == 0 && move.getEnd().getY() == 0 && board.getWhiteCastleMoveState() != 2) {
            board.increaseWhiteMoveState(2);
        } else if (move.getEnd().getX() == 7 && move.getEnd().getY() == 0 && board.getWhiteCastleMoveState() != 1) {
            board.increaseWhiteMoveState(1);
        }

        if (this.isPawn() && move.getEnd() == (new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())) && Board.getCanEnpassant()) {
            Position enPassantSquare = new Position(move.getEnd().getX(), move.getEnd().getY() + 1);
            board.addPiece(enPassantSquare, new Empty(move.getBeginning()));
        }
        board.addPiece(move.getEnd(), this);
        this.position = move.getEnd();
        board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
        if (this.isPawn() && (move.getBeginning().getY() == 1 || move.getBeginning().getY() == 6) && (move.getEnd().getY() == 3 || move.getEnd().getY() == 4)) {
            if (this.getColor() == Color.b) {
                Position placeHolder = new Position(move.getEnd().getX(), move.getEnd().getY() - 1);
                board.setInPassingSquare(placeHolder);
            } else {
                Position placeHolder = new Position(move.getEnd().getX(), move.getEnd().getY() + 1);
                board.setInPassingSquare(placeHolder);
            }
            board.setCanEnpassant(true);
            return;
        }
        board.setCanEnpassant(false);
    }

    public void moveUnofficial(Board board, Move move) {
        if (move.isPromotionMove(move)){
            promotionMove(move, board);
            return;
        }
        if (isCastleMove(move, board)) {
            castleMove(move, board);
            return;
        }

        if (this.isPawn() && move.getEnd() == (new Position(Board.getInPassingSquareX(), Board.getInPassingSquareY())) && Board.getCanEnpassant()) {
            Position enPassantSquare = new Position(move.getEnd().getX(), move.getEnd().getY() + 1);
            board.addPiece(enPassantSquare, new Empty(move.getBeginning()));
        }
        board.addPiece(move.getEnd(), this);
        this.position = move.getEnd();
        board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
        if (this.isPawn() && (move.getEnd().getY() == 3 || move.getEnd().getY() == 4)) {
            if (move.getEnd().getY() == 3) {
                Position placeHolder = new Position(move.getEnd().getX(), move.getEnd().getY() - 1);
                board.setInPassingSquare(placeHolder);
            } else {
                Position placeHolder = new Position(move.getEnd().getX(), move.getEnd().getY() + 1);
                board.setInPassingSquare(placeHolder);
            }
            board.setCanEnpassant(true);
            return;
        }
        board.setCanEnpassant(false);
    }

    public char getLetter() {
        return this.letter;
    }
}
