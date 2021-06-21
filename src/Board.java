public class Board {
    int[][] board = new int[8][8];

    public Board() {
        makeBoard();
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(Pieces.getPiece(board[i][j]) + " ");
            }
            System.out.println();
        }
    }

    private void makeBoard() {
        board = new int[][]{
                {3, 5, 4, 1, 2, 4, 5, 3},
                {6, 6, 6, 6, 6, 6, 6, 6},
                {0, 0, 0, 0, 0, 0, 0, 6},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {12, 12, 12, 12, 12, 12, 12, 12},
                {9, 11, 10, 8, 7, 10, 11, 9}
        };
    }
}
