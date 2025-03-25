package cheekykoala.pieces;

import cheekykoala.*;

import java.util.ArrayList;

public abstract class Piece {
    Position position;
    char piece;
    char letter;
    Color color;

    public Piece() {
    }

    public Piece(Piece other) {
        this.position = other.position;
        this.piece = other.piece;
        this.letter = other.letter;
        this.color = other.color;
    }

    public double getValue() {
        Position piecePosition = getPosition();
        int x = piecePosition.getX();
        int y = piecePosition.getY();
        int index = y * 8 + x;
        if (color == Color.w) {
            return getValue(index);
        } else {
            return getValue(63 - index);
        }
    }

    public double getValue(int index) {
        return getTableValue()[index];
    }

    public double getTableValue(int[] table) {
        Position piecePosition = getPosition();
        int x = piecePosition.getX();
        int y = piecePosition.getY();
        if (color == Color.w) {
            return table[(y * 8) + x];
        } else if (color != Color.g) {

            return table[63 - ((y * 8) + x)];
        }
        return 0;
    }


    public abstract double[] getTableValue();

    public double getPieceEval() {
        this.letter = Character.toUpperCase(this.letter);
        switch (this.letter) {
            case 'R':
                return 500;
            case 'N':
                return 320;
            case 'B':
                return 330;
            case 'Q':
                return 900;
            case 'P':
                return 100;
            case 'K':
                return 20000;
        }
        return 0;
    }

    public boolean isOppositeColor(Piece piece) {
        return (this.color == Color.w && piece.getColor() == Color.b)
                || (this.color == Color.b && piece.getColor() == Color.w);
    }

    public boolean isSameColor(Piece piece) {
        return color == piece.getColor();
    }

    public abstract ArrayList<Move> getMoves(Board board);

    public ArrayList<Move> getPromotionMoves(Board board) {
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

    public void promotionMove(Move move, Board board) {
        board.addPiece(move.getEnd(), move.getPiece());
        board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
        board.setCanEnpassant(false);
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
        board.setCanEnpassant(false);
    }

    public void inPassingMove(Move move, Board board) {
        Position taken = new Position(move.getEnd().getX(), move.getBeginning().getY());
        board.addPiece(move.getEnd(), new Pawn(color, move.getEnd()));
        board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
        board.addPiece(taken, new Empty(taken));
        board.setCanEnpassant(false);
    }

    public void upTwoMove(Move move, Board board) {
        normalMove(move, board);
        int direction;
        if (color == Color.w)
            direction = -1;
        else
            direction = 1;
        Position inPassingSquare = new Position(move.getBeginning().getX(), move.getBeginning().getY() + direction);
        board.setInPassingSquare(inPassingSquare);
        board.setCanEnpassant(true);
    }

    public void normalMove(Move move, Board board) {
        board.addPiece(move.getEnd(), this);
        this.setPosition(move.getEnd());
        board.addPiece(move.getBeginning(), new Empty(move.getBeginning()));
        board.setCanEnpassant(false);
    }

    public void move(Board board, Move move) {
        board.changeEval(move, this);
        board.setMoveState(this, move);
        if (move.isPromotionMove(move)) {
            promotionMove(move, board);
        } else if (move.isCastleMove(board)) {
            castleMove(move, board);
        } else if (move.isInPassingMove(board)) {
            inPassingMove(move, board);
            Board.increaseCaptures();
        } else if (move.isUpTwoMove(board)) {
            upTwoMove(move, board);
        } else {
            normalMove(move, board);
            if (move.isCapture(board)) {
                Board.increaseCaptures();
            }
        }
    }

    public char getLetter() {
        return this.letter;
    }
}
