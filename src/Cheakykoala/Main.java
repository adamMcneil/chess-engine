package Cheakykoala;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        for (Move m : board.get(0, 0).getMoves(board)) {
            System.out.println(m.end);
        }
    }
}