package cheekykoala;

import cheekykoala.pieces.Pawn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testAddPiece() {
        Board board = new Board();
        Position position = new Position(0, 0);
        Pawn pawn = new Pawn(Color.w, position);
        board.addPiece(position, pawn);
        assertEquals(pawn.getLetter(), board.getPieceAt(position).getLetter());
    }

    @Test
    void testWhiteInCheckmate() {
        Board board = new Board();
        board.importBoard("8/8/8/8/8/8/8/RK6 b - - 0 1");
        assertFalse(board.whiteInCheckmate());
        board.importBoard("8/8/8/8/8/8/8/k7 b - - 0 1");
        assertFalse(board.whiteInCheckmate());
    }

    @Test
    void testBlackInCheckmate() {
        Board board = new Board();
        board.importBoard("8/8/8/8/8/8/8/RK6 w - - 0 1");
        assertFalse(board.blackInCheckmate());
        board.importBoard("8/8/8/8/8/8/8/k7 w - - 0 1");
        assertTrue(board.blackInCheckmate());
    }

    @Test
    void testCheckStalemate() {
        Board board = new Board();
        board.importBoard("8/8/8/8/8/8/8/RK6 w - - 0 1");
        assertFalse(board.checkStalemate(Color.w));
        board.importBoard("8/8/8/8/8/8/8/k7 w - - 0 1");
        assertTrue(board.checkStalemate(Color.b));
    }

    @Test
    void testImportBoard() {
        Board board = new Board();
        board.importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        assertEquals('r', board.getPieceAt(new Position(0, 0)).getLetter());
        assertEquals('R', board.getPieceAt(new Position(0, 7)).getLetter());
        assertEquals('k', board.getPieceAt(new Position(4, 0)).getLetter());
        assertEquals('K', board.getPieceAt(new Position(4, 7)).getLetter());
    }
}