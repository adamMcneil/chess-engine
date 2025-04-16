package cheekykoala;

import org.junit.jupiter.api.Test;

import static cheekykoala.Main.*;

public class MainTest {
    @Test
    public void test() {
        Board board = new Board();
        String input = "position startpos moves e2e4 g7g6 d2d4 g8f6 d4d5 d7d6 d1d4 c7c5 d4c3 f8g7 a2a3 f6h5 c3f3 b8d7 f1b5 d8a5 b1c3 e8g8 f3e2 g7c3 e1d1 c3g7 g2g4 h5f6 c1d2 a5c7 g4g5 f6h5 a1a2 d7e5 d2c3 h5f4 e2e3 f4g2 e3g3 c7b6 b5a4 b6a6 a4b5 g2e3 g3e3 a6b5 a3a4 b5d7 e3e2 d7c7 b2b4 c5b4 c3b4 f8e8 a4a5 b7b6";
        onPosition(board, input);
        board.printBoard();
        onGo(board, Color.w);
    }

    @Test
    public void timeMinimax() {
        Board board = new Board();
        Main.depthSearch(board, 6, Color.w);
    }

    @Test
    public void timeOnGo() {
        Board board = new Board();
        Main.onGo(board, Color.w);
    }
    @Test
    public void test1() {
        Board board = new Board();
        board.importBoard("2kr3r/p1ppqpb1/bn2Qnp1/3PN3/1p2P3/2N5/PPPBBPPP/R3K2R b KQ - 3 2");
        board.printBoard();
        Main.depthSearch(board, 6, Color.b);
    }

    @Test
    public void testIterativeDeepening() {
        Board board = new Board();
        System.out.println(iterativeDeepening(board, Color.w, 10000));
    }

}
