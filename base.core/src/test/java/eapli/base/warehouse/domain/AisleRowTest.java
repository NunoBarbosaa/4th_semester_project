package eapli.base.warehouse.domain;



import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AisleRowTest {

    @Test
    public void endsOn() {
        AisleRow row = new AisleRow(1, new Square(1,2), new Square(1,2), 2);
        assertEquals(row.endsOn(), new Square(1,2));
    }

    @Test
    public void startsOn() {
        AisleRow row = new AisleRow(1, new Square(1,2), new Square(1,2), 2);
        assertEquals(row.startsOn(), new Square(1,2));
    }

    @Test
    public void shelfCount() {
        AisleRow row = new AisleRow(1, new Square(1,2), new Square(1,2), 2);
        assertEquals(row.shelfCount(), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shelfCountInvalid() {
        AisleRow row = new AisleRow(1, new Square(1,2), new Square(1,2), 0);
    }

    @Test
    public void testEquals() {
        AisleRow row = new AisleRow(1, new Square(1,2), new Square(1,2), 2);
        AisleRow row1 = new AisleRow(1, new Square(1,2), new Square(1,2), 2);
        assertEquals(row1, row);
        AisleRow row2 = new AisleRow(2, new Square(1,2), new Square(1,2), 2);
        assertNotEquals(row1, row2);
    }
}