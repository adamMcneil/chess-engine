package cheekykoala;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
    @Test
    public void toStringTest() {
        int position = 0;
        assertEquals("a8", Position.toString(position));
        position = 8;
        assertEquals("a7", Position.toString(position));
        position = 9;
        assertEquals("b7", Position.toString(position));
    }

    @Test
    public void getRowTest() {
        assertEquals(0, Position.getRow(0));
        assertEquals(7, Position.getRow(63));
    }
}
