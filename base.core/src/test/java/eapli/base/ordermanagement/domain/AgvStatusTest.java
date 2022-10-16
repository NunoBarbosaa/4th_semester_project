package eapli.base.ordermanagement.domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AgvStatusTest {

    @Test
    void testToString() {
        Date date = new Date();
        Status status = new Status(OrderStatus.CREATED, date);
        String toString = "Status{orderStatus=CREATED, date=" + date + "}";
        assertEquals(toString, status.toString());
    }
}