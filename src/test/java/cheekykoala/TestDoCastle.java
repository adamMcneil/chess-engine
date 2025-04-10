package cheekykoala;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDoCastle {
    @Test
    public void blackKingSide() {
        Board board = new Board();
        board.importBoard("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        Move castle = new Move(4, 6);
        board.doCastleMove(castle);
        assertTrue(board.getPieceAt(7).isEmpty());
        assertTrue(board.getPieceAt(6).isKing());
        assertTrue(board.getPieceAt(5).isRook());
        board.printBoard();
    }

    @Test
    public void blackQueenSide() {
        Board board = new Board();
        board.importBoard("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        Move castle = new Move(4, 2);
        board.doCastleMove(castle);
        assertTrue(board.getPieceAt(0).isEmpty());
        assertTrue(board.getPieceAt(2).isKing());
        assertTrue(board.getPieceAt(3).isRook());
        board.printBoard();
    }

    @Test
    public void whiteKingSide() {
        Board board = new Board();
        board.importBoard("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        Move castle = new Move(60, 62);
        board.doCastleMove(castle);
        board.printBoard();
        assertTrue(board.getPieceAt(63).isEmpty());
        assertTrue(board.getPieceAt(62).isKing());
        assertTrue(board.getPieceAt(61).isRook());
    }

    @Test
    public void whiteQueenSide() {
        Board board = new Board();
        board.importBoard("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        Move castle = new Move(60, 58);
        board.doCastleMove(castle);
        board.printBoard();
        assertTrue(board.getPieceAt(56).isEmpty());
        assertTrue(board.getPieceAt(58).isKing());
        assertTrue(board.getPieceAt(59).isRook());
    }
}
