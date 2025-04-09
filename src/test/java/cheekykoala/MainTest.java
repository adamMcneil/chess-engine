package cheekykoala;

import org.junit.jupiter.api.Test;

import static cheekykoala.Main.onGo;
import static cheekykoala.Main.onPosition;

public class MainTest {
    @Test
    public void test() {
        Board board = new Board();
        String input = "position startpos moves e2e4 e7e5 g1f3 b8c6 f1b5 a7a6 b5a4 b7b5";
        onPosition(board, input);
        board.printBoard();
        onGo(board);
    }

    @Test
    public void timeMinimax() {
        Board board = new Board();
        Main.moveMinimax(board, 5, Color.w);
    }

    @Test
    public void test1() {
        Board board = new Board();
        board.importBoard("2kr3r/p1ppqpb1/bn2Qnp1/3PN3/1p2P3/2N5/PPPBBPPP/R3K2R b KQ - 3 2");
        board.printBoard();
        Main.moveMinimax(board, 6, Color.b);
    }

}
