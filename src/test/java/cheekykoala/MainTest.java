package cheekykoala;

import org.junit.jupiter.api.Test;

import static cheekykoala.Main.*;

public class MainTest {
    @Test
    public void test() {
        Board board = new Board();
        String input = "'position startpos moves e2e4 e7e5 g1f3 g8f6 f3e5 b8c6 e5c6 d7c6 d1f3 c8g4 f3f4 f8d6 f4g5 h7h6 g5e3 f6d7 h2h3 g4e6 e3d4 d6e5 d4b4 d7b6 c2c3 e5d6 b4d4 e8g8 f1e2 c6c5 d4e3 d8g5 e3g5 h6g5 d2d4 f7f5 e4e5 d6e7 d4c5 b6d7 e2f3 a8b8 f3b7 d7c5 b7f3 c5d3 e1e2 e6c4 e2d2 d3f2 h1g1 b8d8 d2c2 c4d3 c2b3 d8b8";
        onPosition(board, input);
        board.printBoard(41);
        Move bestMove = iterativeDeepeningPath(board, Color.w, THINK_TIME).move;
        System.out.println(bestMove);
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
        // Board board = new Board("2r3k1/p4p2/3Rp2p/1p2P1pK/8/1P4P1/P3Q2P/1q6 b - - 0 1");
        // board.importBoard("2r3k1/p4p2/3Rp1qp/1p2P1p1/6K1/1P4P1/P3Q2P/8 b - - 2 2");
        // board.importBoard("2r3k1/p4p2/3Rp2p/1p2PqpK/8/1P4P1/P3Q2P/8 b - - 4 3");
        board.printBoard();
        System.out.println(iterativeDeepening(board, Color.b, 10000));
    }

    @Test
    public void testPath() {
        Board board = new Board();
        Path p = iterativeDeepeningPath(board, Color.w, 10000);
        System.out.println(p);
        p.board.printBoard();
    }

}
