package cheekykoala;

import cheekykoala.pieces.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PieceMovementTest {

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
        Piece rook = board.getPieceAt(32);
        assertTrue(rook.isRook());
        assertSame(Color.w, rook.getColor());
        List<Move> moves = rook.getMoves(board);
        assertEquals(9, moves.size());
    }

    @Test
    void testBishopMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/3b4/8/8/8/8 b - - 0 1");
        Piece bishop = board.getPieceAt(27);
        assertTrue(bishop.isBishop());
        assertSame(Color.b, bishop.getColor());
        int moves = bishop.getMoves(board).size();
        assertEquals(13, moves);
    }

    @Test
    void testKnightMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/3N4/8/8/8 w - - 0 1");
        board.printBoard(35);
        Piece knight = board.getPieceAt(35);
        assertTrue(knight.isKnight());
        assertSame(Color.w, knight.getColor());
        int moves = knight.getMoves(board).size();
        assertEquals(8, moves);
    }

    @Test
    void testKnightMovesEdge() {
        Board board = new Board();
        board.importBoard("8/8/8/8/N7/8/8/8 w - - 0 1");
        Piece knight = board.getPieceAt(32);
        assertTrue(knight.isKnight());
        assertSame(Color.w, knight.getColor());
        int moves = knight.getMoves(board).size();
        assertEquals(4, moves);
    }

    @Test
    void testQueenMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/4Q3/8/8/8 w - - 0 1");
        Piece queen = board.getPieceAt(36);
        assertTrue(queen.isQueen());
        assertSame(Color.w, queen.getColor());
        List<Move> moves = queen.getMoves(board);
        assertEquals(27, moves.size());
    }

    @Test
    void testKingMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/4K3/8/8/8 w - - 0 1");
        Piece king = board.getPieceAt(36);
        assertTrue(king.isKing());
        assertSame(Color.w, king.getColor());
        int moves = king.getMoves(board).size();
        assertEquals(8, moves);
    }

    @Test
    void testPawnMoves() {
        Board board = new Board();
        board.importBoard("8/8/8/8/3P4/8/8/8 w - - 0 1");
        Piece pawn = board.getPieceAt(35);
        assertTrue(pawn.isPawn());
        assertSame(Color.w, pawn.getColor());
        int moves = pawn.getMoves(board).size();
        assertEquals(1, moves);
    }

    @Test
    void testPawnForwardTwo() {
        Board board = new Board();
        Piece pawn = board.getPieceAt(49);
        assertTrue(pawn.isPawn());
        assertSame(Color.w, pawn.getColor());
        int moves = pawn.getMoves(board).size();
        assertEquals(2, moves);

    }

    @Test
    void testPromotionMoves() {
        Board board = new Board();
        board.importBoard("8/P7/8/8/8/8/p7/8 w - - 0 1");
        Piece whitePawn = board.getPieceAt(8);
        Piece blackPawn = board.getPieceAt(48);
        assertTrue(whitePawn.isPawn());
        assertTrue(blackPawn.isPawn());
        List<Move> whiteMoves = whitePawn.getMoves(board);
        int blackMoves = blackPawn.getMoves(board).size();
        assertEquals(4, whiteMoves.size());
        assertEquals(4, blackMoves);
    }

    @Test
    void testCastling() {
        Board board = new Board();
        board.importBoard("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");
        Piece whiteKing = board.getPieceAt(60);
        Piece blackKing = board.getPieceAt(4);
        assertTrue(whiteKing.isKing());
        assertTrue(blackKing.isKing());
        int whiteMoves = whiteKing.getMoves(board).size();
        int blackMoves = blackKing.getMoves(board).size();
        assertEquals(4, whiteMoves);
        assertEquals(4, blackMoves);
    }

    @Test
    void testCastlingMoved() {
        Board board = new Board();
        board.importBoard("r3k2r/ppppp1Rp/8/8/8/8/PPPPPPPP/R3K2R w - - 0 1");
        Piece whiteKing = board.getPieceAt(60);
        Piece blackKing = board.getPieceAt(4);
        assertTrue(whiteKing.isKing());
        assertTrue(blackKing.isKing());
        int whiteMoves = whiteKing.getMoves(board).size();
        int blackMoves = blackKing.getMoves(board).size();
        assertEquals(2, whiteMoves);
        assertEquals(2, blackMoves);
    }

    @Test
    void testCastlingBlock() {
        Board board = new Board();
        board.importBoard("r3k2r/ppRpppRp/8/8/8/8/PPrPPPrP/R3K2R w KQkq - 0 1");
        Piece whiteKing = board.getPieceAt(60);
        Piece blackKing = board.getPieceAt(4);
        assertTrue(whiteKing.isKing());
        assertTrue(blackKing.isKing());
        int whiteMoves = whiteKing.getMoves(board).size();
        int blackMoves = blackKing.getMoves(board).size();
        assertEquals(2, whiteMoves);
        assertEquals(2, blackMoves);
    }

    @Test
    void inPassingBoard() {
        Board board = new Board();
        board.importBoard("8/8/8/2k5/2pP4/8/B7/4K3 b - d3 0 3");
        assertTrue(board.isColorInCheck(Color.b));
        Piece pawn = board.getPieceAt(34);
        List<Move> moves = pawn.getMoves(board);
        for (Move move : moves) {
            System.out.println(move);
        }
        assertEquals(1, moves.size());
    }

    @Test
    void inPassingPin() {
        Board board = new Board();
        board.importBoard("3k4/8/8/K1Pp3r/8/8/8/8 w - - 0 1");
        Piece pawn = board.getPieceAt(26);
        List<Move> moves = pawn.getMoves(board);
        for (Move move : moves) {
            System.out.println(move);
        }
        assertEquals(1, moves.size());
    }

    @Test
    void queenInCorner() {
        Board board = new Board();
        board.importBoard("rnb1kbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNq w Qkq - 0 1");
        Piece queen = board.getPieceAt(63);
        assertTrue(queen.isQueen());
        List<Move> moves = queen.getMoves(board);
        for (Move move : moves) {
            System.out.println(move);
        }
        assertEquals(3, moves.size());

    }

    @Test
    void kingInCorner() {
        Board board = new Board();
        board.importBoard("rnb2bnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNk w Q - 0 1");
        Piece king = board.getPieceAt(63);
        assertTrue(king.isKing());
        List<Move> moves = king.getMoves(board);
        for (Move move : moves) {
            System.out.println(move);
        }
        assertEquals(3, moves.size());

    }
    @Test
    void testFen() {
        Board board = new Board();
        board.importBoard("2kr3r/p1ppqpb1/bn2Qnp1/3PN3/1p2P3/2N5/PPPBBPPP/R3K2R b KQ - 3 2");
        board.printBoard(22);
        board.printBoard();
        MoveCounter counter = board.countNodes(1, Color.b);
        System.out.println(counter);
        int position = 11;
        board.printBoard(position);
        List<Move> moves = board.getPieceAt(position).getMoves(board);
        List<Move> allmoves = board.getAllMoves(Color.w);

        for (Move move : moves) {
            System.out.println(move);
        }
        System.out.println(moves.size());
        assertEquals(4, moves.size());
    }
}
