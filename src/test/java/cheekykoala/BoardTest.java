package cheekykoala;

import cheekykoala.pieces.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testStartMoves() {
        Board board = new Board();
        board.importBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        int whiteMoves = board.getAllMoves(Color.w).size();
        int blackMoves = board.getAllMoves(Color.b).size();
        assertEquals(20, whiteMoves);
        assertEquals(20, blackMoves);
    }

    @Test
    void testRookMoves() {
        Board board = new Board();
        board.importBoard("8/8/P7/8/R4p2/8/8/8 w - - 0 1");
        Piece rook = board.get(0,4 );
        assertTrue(rook.isRook());
        assertSame(rook.getColor(), Color.w);
        int moves = rook.getMoves(board).size();
        assertEquals(9, moves);
    }

    @Test
    void testBishopMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/3b4/8/8/8/8 b - - 0 1");
        Piece bishop = board.get(3, 3);
        assertTrue(bishop.isBishop());
        assertSame(bishop.getColor(), Color.b);
        int moves = bishop.getMoves(board).size();
        assertEquals(13, moves);
    }

    @Test
    void testKnightMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/3N4/8/8/8 w - - 0 1");
        Piece knight = board.get(3, 4);
        assertTrue(knight.isKnight());
        assertSame(knight.getColor(), Color.w);
        int moves = knight.getMoves(board).size();
        assertEquals(8, moves);
    }

    @Test
    void testQueenMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/4Q3/8/8/8 w - - 0 1");
        Piece queen = board.get(4, 4);
        assertTrue(queen.isQueen());
        assertSame(queen.getColor(), Color.w);
        int moves = queen.getMoves(board).size();
        assertEquals(27, moves);
    }

    @Test
    void testKingMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/4K3/8/8/8 w - - 0 1");
        Piece king = board.get(4, 4);
        assertTrue(king.isKing());
        assertSame(king.getColor(), Color.w);
        int moves = king.getMoves(board).size();
        assertEquals(8, moves);
    }

    @Test
    void testPawnMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/3P4/8/8/8 w - - 0 1");
        Piece pawn = board.get(3, 4);
        assertTrue(pawn.isPawn());
        assertSame(pawn.getColor(), Color.w);
        int moves = pawn.getMoves(board).size();
        assertEquals(1, moves); // Assuming no double move or captures available
    }

    @Test
    void testPromotionMoves() {
        Board board = new Board();
        board.importBoard("8/P7/8/8/8/8/p7/8 w - - 0 1");
        Piece whitePawn = board.get(0, 1);
        Piece blackPawn = board.get(0, 6);
        assertTrue(whitePawn.isPawn());
        assertTrue(blackPawn.isPawn());
        int whiteMoves = whitePawn.getMoves(board).size();
        int blackMoves = blackPawn.getMoves(board).size();
        assertEquals(4, whiteMoves);
        assertEquals(4, blackMoves);
    }


}
