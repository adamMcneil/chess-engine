package cheekykoala;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEval {
    @Test
    public void testStartPosition() {
        Board board = new Board();
        assertEquals(0, board.recomputeEval());

    }
}
