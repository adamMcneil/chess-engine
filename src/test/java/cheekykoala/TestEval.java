package cheekykoala;

import java.util.Random;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEval {
    @Test
    public void testStartPosition() {
        Board board = new Board();
        assertEquals(0, board.recomputeEval());
    }

    @Test
    public void testRandomMoves() {
        Random random = new Random();
        Board board = new Board();
        for (int i = 0; i < 100; i++) {
            Color color = i % 2 == 0 ? Color.w : Color.b;
            List<Move> moves = board.getAllMoves(color);
            int randomIndex = random.nextInt(moves.size()); // Generate a random index within the range of the list size
            board.doMove(moves.get(randomIndex)); // Perform the move at the random index
            double testEval = board.getEval();
            assertEquals(testEval, board.recomputeEval());
        }
        board.printBoard();
    }

    @Test
    public void testCastling() {
        Board board = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        board.doMove(new Move(4, 2, MoveType.castling));
        double testEval = board.getEval();
        assertEquals(testEval, board.recomputeEval());

        board.doMove(new Move(60, 58, MoveType.castling));
        testEval = board.getEval();
        assertEquals(testEval, board.recomputeEval());
        board.printBoard();

        board = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R b KQkq - 0 1");
        board.doMove(new Move(4, 6, MoveType.castling));
        testEval = board.getEval();
        assertEquals(testEval, board.recomputeEval());

        board.doMove(new Move(60, 62, MoveType.castling));
        testEval = board.getEval();
        assertEquals(testEval, board.recomputeEval());
        board.printBoard();
    }

    @Test
    public void testPromotions() {
        Random random = new Random();
        Board board = new Board("8/PPPPPPPP/8/8/8/8/pppppppp/8 w HAha - 0 1");
        for (int i = 0; i < 10; i++) {
            Color color = i % 2 == 0 ? Color.w : Color.b;
            List<Move> moves = board.getAllMoves(color);
            int randomIndex = random.nextInt(moves.size()); // Generate a random index within the range of the list size
            board.doMove(moves.get(randomIndex)); // Perform the move at the random index
            double testEval = board.getEval();
            assertEquals(testEval, board.recomputeEval());
        }
        board.printBoard();
    }

    @Test
    public void testInPassing() {
        Board board = new Board("rnbqkbnr/ppp1pppp/8/2Pp4/8/8/PP1PPPPP/RNBQKBNR w KQkq d6 0 1");
        board.doMove(new Move(26, 19, MoveType.inPassing));
        double testEval = board.getEval();
        assertEquals(testEval, board.recomputeEval());
        board.printBoard();
    }
}
