package cheekykoala;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testImportBoard() {
        Board board = new Board();
        board.printBoard();

        assertTrue(board.getPieceAt(0).isRook() && board.getPieceAt(0).getColor() == Color.b);
        assertTrue(board.getPieceAt(1).isKnight() && board.getPieceAt(1).getColor() == Color.b);
        assertTrue(board.getPieceAt(2).isBishop() && board.getPieceAt(2).getColor() == Color.b);
        assertTrue(board.getPieceAt(3).isQueen() && board.getPieceAt(3).getColor() == Color.b);
        assertTrue(board.getPieceAt(4).isKing() && board.getPieceAt(4).getColor() == Color.b);
        assertTrue(board.getPieceAt(5).isBishop() && board.getPieceAt(5).getColor() == Color.b);
        assertTrue(board.getPieceAt(6).isKnight() && board.getPieceAt(6).getColor() == Color.b);
        assertTrue(board.getPieceAt(7).isRook() && board.getPieceAt(7).getColor() == Color.b);

        for (int i = 8; i < 16; i++) {
            assertTrue(board.getPieceAt(i).isPawn() && board.getPieceAt(i).getColor() == Color.b);
        }

        for (int i = 16; i < 48; i++) {
            assertTrue(board.getPieceAt(i).isEmpty());
        }

        for (int i = 48; i < 56; i++) {
            assertTrue(board.getPieceAt(i).isPawn() && board.getPieceAt(i).getColor() == Color.w);
        }

        assertTrue(board.getPieceAt(56).isRook() && board.getPieceAt(56).getColor() == Color.w);
        assertTrue(board.getPieceAt(57).isKnight() && board.getPieceAt(57).getColor() == Color.w);
        assertTrue(board.getPieceAt(58).isBishop() && board.getPieceAt(58).getColor() == Color.w);
        assertTrue(board.getPieceAt(59).isQueen() && board.getPieceAt(59).getColor() == Color.w);
        assertTrue(board.getPieceAt(60).isKing() && board.getPieceAt(60).getColor() == Color.w);
        assertTrue(board.getPieceAt(61).isBishop() && board.getPieceAt(61).getColor() == Color.w);
        assertTrue(board.getPieceAt(62).isKnight() && board.getPieceAt(62).getColor() == Color.w);
        assertTrue(board.getPieceAt(63).isRook() && board.getPieceAt(63).getColor() == Color.w);
    }

    @Test
    public void testIsOnBoard() {
        for (int i = 0; i < 64; i++) {
            assertTrue(Position.isOnBoard(i), "Position " + i + " should be on the board");
        }

        assertFalse(Position.isOnBoard(-1), "Position -1 should not be on the board");
        assertTrue(Position.isOnBoard(0), "Position 0 should be on the board");

        assertTrue(Position.isOnBoard(63), "Position 63 should be on the board");
        assertFalse(Position.isOnBoard(64), "Position 64 should not be on the board");

        assertFalse(Position.isOnBoard(-100), "Position -100 should not be on the board");
        assertFalse(Position.isOnBoard(100), "Position 100 should not be on the board");
    }
}
