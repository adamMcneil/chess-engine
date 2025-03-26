package cheekykoala;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
    @Test
    public void toStringTest() {
        Position position = new Position(0, 0);
        assertEquals(position.toString(), "a8");
        position = new Position(0, 1);
        assertEquals(position.toString(), "a7");
        position = new Position(1, 1);
        assertEquals(position.toString(), "b7");
    }
}
