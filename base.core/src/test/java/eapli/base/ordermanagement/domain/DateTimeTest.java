package eapli.base.ordermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeTest {

    @Test
    void testToString() {
        DateTime dateTime = new DateTime("12:35", "2002-07-05");
        String toString = "DateTime{time=12:35, date=2002-07-05}";
        assertEquals(toString, dateTime.toString());
    }
}