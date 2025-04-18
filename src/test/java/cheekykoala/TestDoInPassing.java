package cheekykoala;

import cheekykoala.pieces.Piece;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDoInPassing {
    @Test
    void testDoInPassing() {
        Board board = new Board();
        board.importBoard("rnbqkbnr/pppp1ppp/8/P3p3/8/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1");
        board.doMove(new Move(board, 9, 25));
        Piece pawn = board.getPieceAt(24);
        board.printBoard(24);
        assertTrue(pawn.isPawn());
        List<Move> moves = pawn.getMoves(board, 24);
        for (Move move : moves) {
            System.out.println(move);
        }
        assertEquals(2, moves.size());
        board.doMove(new Move(board, 10, 18));
        assertEquals(1, pawn.getMoves(board, 24).size());
        board.printBoard(24);
    }

}
