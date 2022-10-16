package eapli.base.warehouse.domain;



import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {

    @Test(expected = IllegalArgumentException.class)
    public void testLSquareCannotBeNegative() {
        new Square(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWSquareCannotBeNegative() {
        new Square(4, -1);
    }

    @Test
    public void testValueGetters() {
        Square square = new Square(1, 2);
        assertEquals(1, square.lSquare());
        assertEquals(2, square.wSquare());
    }

    @Test
    public void testEquals() {
        Square square1 = new Square(1,2);
        Square square2 = new Square(1,2);
        Square square3 = new Square(3,2);
        assertEquals(square1, square2);
        assertNotEquals(square1, square3);
    }

    @Test
    public void testToString() {
        Square square1 = new Square(2,1);
        assertEquals("Square -> x: 2, y: 1", square1.toString());
    }
}