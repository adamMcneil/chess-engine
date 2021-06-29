package Cheakykoala;

import Cheakykoala.Pieces.*;

public class Board {

    private static Position inPassingSquare = new Position(8,8);
    private static boolean canEnpassant;
    Piece[][] board = new Piece[8][8];
    int whiteCastleMoveState = 0;
    int blackCastleMoveState = 0;

    public Board() {
        makeBoard();
    }

    public int getWhiteCastleMoveState(){
        return whiteCastleMoveState;
    }

    public int getBlackCastleMoveState(){
        return blackCastleMoveState;
    }

    public static int getInPassingSquareX() {
        return inPassingSquare.getX();
    }

    public static int getInPassingSquareY() {
        return inPassingSquare.getY();
    }

    public static boolean getCanEnpassant() {
        return canEnpassant;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print("\033[40m");
                }
                System.out.print(board[i][j].getPiece() + "  ");
                if ((i + j) % 2 == 0) {
                    System.out.print("\033[0m");
                }
            }
            System.out.println();
        }
    }

    public Piece getPieceAt(Position position) {
        return board[position.getY()][position.getX()];
    }

    public Piece get(int x, int y) {
        return board[y][x];
    }

    public boolean isColorInCheck(Color color) {
        for (Piece[] pieces : board) {
            for (Piece p : pieces) {
                if (p.getOppositeColor() == color) {
                    for (Move m : p.getMovesNotCheck(this)) {
                        if (this.getPieceAt(m.getEnd()).isKing())
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public void addPiece(Position position, Piece piece) {
        board[position.getY()][position.getX()] = piece;
    }

    private void makeBoard() {
        importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public void importBoard(String fenBoard) {
        int x = 0;
        int y = 0;
        String[] splitFen = fenBoard.split(" ");
        fenBoard = splitFen[0];
        for (int i = 0; i < fenBoard.length(); i++) {
            if (fenBoard.charAt(i) != '/' && !Character.isDigit(fenBoard.charAt(i))) {
                board[y][x] = getPiece(fenBoard.charAt(i), x, y);
                x++;
            } else if (Character.isDigit(fenBoard.charAt(i))) {
                for (int j = 0; j < Character.getNumericValue(fenBoard.charAt(i)); j++) {
                    board[y][x] = new Empty(new Position(x, y));
                    x++;
                }
            } else {
                x = 0;
                y++;
            }
        }
    }

    public Piece getPiece(char letter, int x, int y) {
        Piece piece;
        switch (letter) {
            case 'r':
                piece = new Rook(Color.b, new Position(x, y));
                return piece;
            case 'R':
                piece = new Rook(Color.w, new Position(x, y));
                return piece;
            case 'n':
                piece = new Knight(Color.b, new Position(x, y));
                return piece;
            case 'N':
                piece = new Knight(Color.w, new Position(x, y));
                return piece;
            case 'b':
                piece = new Bishop(Color.b, new Position(x, y));
                return piece;
            case 'B':
                piece = new Bishop(Color.w, new Position(x, y));
                return piece;
            case 'q':
                piece = new Queen(Color.b, new Position(x, y));
                return piece;
            case 'Q':
                piece = new Queen(Color.w, new Position(x, y));
                return piece;
            case 'k':
                piece = new King(Color.b, new Position(x, y));
                return piece;
            case 'K':
                piece = new King(Color.w, new Position(x, y));
                return piece;
            case 'p':
                piece = new Pawn(Color.b, new Position(x, y));
                return piece;
            case 'P':
                piece = new Pawn(Color.w, new Position(x, y));
                return piece;
        }
        piece = new Empty(new Position(x, y));
        return piece;
    }

    public void setInPassingSquare(Position x) {
        this.inPassingSquare.setY(x.getY());
        this.inPassingSquare.setX(x.getX());
    }

    public Board getChild(Board board, Move move) {
        Board child = new Board();
        int x = 0, y = 0;
        Piece newPiece;
        Position position = new Position(x, y);
        for (Piece[] pieces : board.getBoard()) {
            for (Piece piece : pieces) {
                Piece pieceCopy = getPiece(piece.getLetter(), x, y);
                child.getBoard()[y][x] = pieceCopy;
                x++;
                position = new Position(x, y);
            }
            x = 0;
            y++;
            position = new Position(x, y);
        }
        child.getPieceAt(move.getBeginning()).moveOffical(child, move);
        return child;
    }

    public void setCanEnpassant(Boolean logic) {
        this.canEnpassant = logic;
    }

}
