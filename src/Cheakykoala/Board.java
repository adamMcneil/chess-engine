package Cheakykoala;

import Cheakykoala.Pieces.*;

public class Board {
    Piece[][] board = new Piece[8][8];

    public Board() {
        makeBoard();
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
        return board[position.getX()][position.getY()];
    }

    public Piece get(int x, int y) {
        return board[x][y];
    }


    private void makeBoard() {
        board = new Piece[][]{
                {new Rook(Color.w, new Position(0, 0)), new Knight(Color.w, new Position(0, 1)), new Bishop(Color.w, new Position(0, 2)), new King(Color.w, new Position(0, 3)), new Queen(Color.w, new Position(0, 4)), new Bishop(Color.w, new Position(0, 5)), new Knight(Color.w, new Position(0, 6)), new Rook(Color.w, new Position(0, 7))},


                {new Empty(Color.g, new Position(2, 0)), new Empty(Color.g, new Position(2, 1)), new Empty(Color.g, new Position(2, 2)), new Empty(Color.g, new Position(2, 3)), new Empty(Color.g, new Position(2, 4)), new Empty(Color.g, new Position(2, 5)), new Empty(Color.g, new Position(2, 6)), new Empty(Color.g, new Position(2, 7))},
                {new Pawn(Color.b, new Position(6, 0)), new Pawn(Color.b, new Position(6, 1)), new Pawn(Color.b, new Position(6, 2)), new Pawn(Color.b, new Position(6, 3)), new Pawn(Color.b, new Position(6, 4)), new Pawn(Color.b, new Position(6, 5)), new Pawn(Color.b, new Position(6, 6)), new Pawn(Color.b, new Position(6, 7))},
                {new Pawn(Color.w, new Position(1, 0)), new Pawn(Color.w, new Position(1, 1)), new Pawn(Color.w, new Position(1, 2)), new Pawn(Color.w, new Position(1, 3)), new Pawn(Color.w, new Position(1, 4)), new Pawn(Color.w, new Position(1, 5)), new Pawn(Color.w, new Position(1, 6)), new Pawn(Color.w, new Position(1, 7))},

                {new Empty(Color.g, new Position(3, 0)), new Empty(Color.g, new Position(3, 1)), new Empty(Color.g, new Position(3, 2)), new Empty(Color.g, new Position(3, 3)), new Empty(Color.g, new Position(3, 4)), new Empty(Color.g, new Position(3, 5)), new Empty(Color.g, new Position(3, 6)), new Empty(Color.g, new Position(3, 7))},
                {new Empty(Color.g, new Position(4, 0)), new Empty(Color.g, new Position(4, 1)), new Knight(Color.w, new Position(4, 2)), new Empty(Color.g, new Position(4, 3)), new Empty(Color.g, new Position(4, 4)), new Empty(Color.g, new Position(4, 5)), new Empty(Color.g, new Position(4, 6)), new Empty(Color.g, new Position(4, 7))},
                {new Knight(Color.w, new Position(5, 0)), new Empty(Color.g, new Position(5, 1)), new Empty(Color.g, new Position(5, 2)), new Empty(Color.g, new Position(5, 3)), new Empty(Color.g, new Position(5, 4)), new Empty(Color.g, new Position(5, 5)), new Empty(Color.g, new Position(5, 6)), new Empty(Color.g, new Position(5, 7))},

                {new Rook(Color.b, new Position(7, 0)), new Knight(Color.b, new Position(7, 1)), new Bishop(Color.b, new Position(7, 2)), new King(Color.b, new Position(7, 3)), new Queen(Color.b, new Position(7, 4)), new Bishop(Color.b, new Position(7, 5)), new Knight(Color.b, new Position(7, 6)), new Rook(Color.b, new Position(7, 7))}
        };
    }
}
