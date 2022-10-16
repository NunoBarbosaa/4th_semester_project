package eapli.base.warehouse.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AisleTest {

    @Test
    public void extendsTo() {
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertEquals(aisle.extendsTo(), new Square(1,6));
    }

    @Test
    public void endsOn() {
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertEquals(aisle.endsOn(), new Square(1,6));
    }

    @Test
    public void startsFrom() {
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertEquals(aisle.startsFrom(), new Square(1,3));
    }

    @Test
    public void depth() {
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertEquals(aisle.depth(), new Square(1,6));
    }

    @Test
    public void rows(){
        AisleRow row1 = new AisleRow(1, new Square(1,3), new Square(1,4), 2);
        AisleRow row2 = new AisleRow(1, new Square(1,5), new Square(1,6), 2);
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        aisle.addRow(row1);
        aisle.addRow(row2);
        List<AisleRow> list = new ArrayList<>();
        list.add(row1);
        list.add(row2);
        assertEquals(list, aisle.rows());
    }

    @Test
    public void isAccessibleVia() {
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertEquals(aisle.isAccessibleVia(), Accessibility.W_MINUS);
    }

    @Test
    public void addRow() {
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertTrue(aisle.addRow(new AisleRow(1, new Square(1,3), new Square(1,4), 3)));

    }

    @Test
    public void equals() {
        Aisle aisle = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertTrue(aisle.equals(aisle));
        Aisle aisle1 = new Aisle(1, new Square(1, 3), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertEquals(aisle, aisle1);
        Aisle aisle2 = new Aisle(2, new Square(1, 4), new Square(1, 6), new Square(1, 6), Accessibility.W_MINUS);
        assertNotEquals(aisle1, aisle2);

    }
}