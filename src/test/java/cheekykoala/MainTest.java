package cheekykoala;

import org.junit.jupiter.api.Test;

import static cheekykoala.Main.onGo;
import static cheekykoala.Main.onPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    public void test() {
        Board board = new Board();
        String input = "position startpos moves e2e4 e7e5 g1f3 b8c6 f1b5 a7a6 b5a4 b7b5";
        onPosition(board, input);
        board.printBoard();
        onGo(board);
    }
}
