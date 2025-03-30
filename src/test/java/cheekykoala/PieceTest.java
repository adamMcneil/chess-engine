package cheekykoala;

import cheekykoala.pieces.Piece;
import cheekykoala.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
    @Test
    public void testCopy() {
        Piece rook = new Rook(Color.b, 0);
        Piece copy = rook.copy();


        assertNotSame(rook, copy, "Copy should be a different object");

        assertEquals(rook.getColor(), copy.getColor(), "Color should be the same");
        assertEquals(rook.getPosition(), copy.getPosition());

        rook.setPosition(7);
        assertNotEquals(rook.getPosition(), copy.getPosition());

        assertTrue(copy.isRook(), "Copy's isRook() should return true");
    }

}
