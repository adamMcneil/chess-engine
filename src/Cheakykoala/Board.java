package Cheakykoala;

import Cheakykoala.Pieces.*;

public class Board {
    Piece[][] board = new Piece[8][8];

    public Board() {
        makeBoard();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j].getPiece() + "  ");
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
                if (p.getColor().getOppositeColor() == color) {
                    for (Move m : p.getMoves(this)) {
                        if (board[m.getEnd().getX()][m.getEnd().getY()].isKing())
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
        board = new Piece[][]{
                {new Rook(Color.b, new Position(0, 0)), new Knight(Color.b, new Position(1, 0)), new Bishop(Color.b, new Position(2, 0)), new Queen(Color.b, new Position(3, 0)), new King(Color.b, new Position(4, 0)), new Bishop(Color.b, new Position(5, 0)), new Knight(Color.b, new Position(6, 0)), new Rook(Color.b, new Position(7, 0))},
                {new Pawn(Color.b, new Position(0, 1)), new Pawn(Color.b, new Position(1, 1)), new Pawn(Color.b, new Position(2, 1)), new Pawn(Color.b, new Position(3, 1)), new Pawn(Color.b, new Position(4, 1)), new Pawn(Color.b, new Position(5, 1)), new Pawn(Color.b, new Position(6, 1)), new Pawn(Color.b, new Position(7, 1))},
                {new Empty(new Position(0, 2)), new Empty(new Position(1, 2)), new Empty(new Position(2, 2)), new Empty(new Position(3, 2)), new Empty(new Position(4, 2)), new Empty(new Position(5, 2)), new Empty(new Position(6, 2)), new Empty(new Position(7, 2))},
                {new Empty(new Position(0, 3)), new Empty(new Position(1, 3)), new Empty(new Position(2, 3)), new Empty(new Position(3, 3)), new Empty(new Position(4, 3)), new Empty(new Position(5, 3)), new Empty(new Position(6, 3)), new Empty(new Position(7, 3))},
                {new Empty(new Position(0, 4)), new Empty(new Position(1, 4)), new Empty(new Position(2, 4)), new Empty(new Position(3, 4)), new Empty(new Position(4, 4)), new Empty(new Position(5, 4)), new Empty(new Position(6, 4)), new Empty(new Position(7, 4))},
                {new Empty(new Position(0, 5)), new Empty(new Position(1, 5)), new Empty(new Position(2, 5)), new Empty(new Position(3, 5)), new Empty(new Position(4, 5)), new Empty(new Position(5, 5)), new Empty(new Position(6, 5)), new Empty(new Position(7, 5))},
                {new Pawn(Color.w, new Position(0, 6)), new Pawn(Color.w, new Position(1, 6)), new Pawn(Color.w, new Position(2, 6)), new Pawn(Color.w, new Position(3, 6)), new Pawn(Color.w, new Position(4, 6)), new Pawn(Color.w, new Position(5, 6)), new Pawn(Color.w, new Position(6, 6)), new Pawn(Color.w, new Position(7, 6))},
                {new Rook(Color.w, new Position(0, 7)), new Knight(Color.w, new Position(1, 7)), new Bishop(Color.w, new Position(2, 7)), new Queen(Color.w, new Position(3, 7)), new King(Color.w, new Position(4, 7)), new Bishop(Color.w, new Position(5, 7)), new Knight(Color.w, new Position(6, 7)), new Rook(Color.w, new Position(7, 7))},
        };
    }

    public void importBoard(String fenBoard) {
        int x = 0;
        int y = 0;
        String[] splitFen = fenBoard.split(" ");
        fenBoard = splitFen[0];
        System.out.println(fenBoard);
        for (int i = 0; i < fenBoard.length(); i++) {
            if (fenBoard.charAt(i) != '/' && !Character.isDigit(fenBoard.charAt(i))) {
                board[y][x] = getPiece(fenBoard.charAt(i), x, y);
                x++;
            } else if (Character.isDigit(fenBoard.charAt(i))) {
                System.out.println("Number: " + fenBoard.charAt(i));
                for (int j = 0; j < Character.getNumericValue(fenBoard.charAt(i)); j++) {
                    System.out.println("   " + j);
                    System.out.println(j + " " + fenBoard.charAt(i));
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
}
