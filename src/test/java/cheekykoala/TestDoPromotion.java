package cheekykoala;

import cheekykoala.pieces.Piece;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cheekykoala.Main.onGo;
import static cheekykoala.Main.onPosition;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDoPromotion {
    @Test
    void testDoPromotion() {
        Board board = new Board();
        board.importBoard("8/P7/8/8/8/8/p7/8 w HAha - 0 1");

        List<Move> whiteMoves = board.getPieceAt(8).getMoves(board);
        assertSame(4, whiteMoves.size());
        for (Move move : whiteMoves) {
            assertSame(Color.w, move.getPiece().getColor());
            Board childBoard = board.getChild(move);
            Piece piece = childBoard.getPieceAt(0);
            childBoard.printBoard();
            assertSame(Color.w, piece.getColor());
        }

        List<Move> blackMoves = board.getPieceAt(48).getMoves(board);
        assertSame(4, blackMoves.size());
        for (Move move : blackMoves) {
            assertSame(Color.b, move.getPiece().getColor());
            Board childBoard = board.getChild(move);
            Piece piece = childBoard.getPieceAt(56);
            childBoard.printBoard();
            assertSame(Color.b, piece.getColor());
        }
    }

    @Test
    public void testDoPromotionMain() {
        Board board = new Board();
        String input = "position startpos moves d2d4 d7d5 c1f4 e7e6 g1f3 g8f6 e2e3 c7c5 c2c3 b8c6 b1d2 f8d6 f4d6 d8d6 f1b5 a8b8 d2b3 b7b6 f3e5 c8b7 e1g1 b8a8 d1c2 a7a6 b5e2 c6e5 d4e5 d6e5 a1d1 a8a7 b3d2 e8e7 d2f3 e5e4 e2d3 e4g4 h2h3 g4h5 f1e1 h8g8 d3e2 g8f8 c2a4 f8d8 f3h2 h5h6 c3c4 d5c4 a4c4 h6g6 e2f3 d8d1 e1d1 b7f3 h2f3 a7d7 d1d7 f6d7 c4f4 g6b1 g1h2 b1b2 a2a4 b2f2 f4g5 e7e8 g5g7 f2e3 g7h7 d7f8 h7h5 e3f4 h2h1 f4f5 h5h8 e8e7 h8b2 f8d7 a4a5 b6b5 b2c3 f5d5 h3h4 d5c4 c3d2 c4f1 h1h2 f1b1 h4h5 b1f5 h5h6 d7e5 d2g5 f5g5 f3g5 f7f6 h6h7 f6g5 h7h8q";
        onPosition(board, input);
        Piece piece = board.getPieceAt(7);
        assertTrue(piece.isQueen());
        assertSame(Color.w, piece.getColor());
        board.printBoard();
        System.out.println(onGo(board));
    }
}
