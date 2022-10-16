package eapli.base.ordermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testToString() {
        Payment payment = new Payment("PayPal", "Success.");
        String toString = "Payment{method='PayPal', confirmation='Success.'}";
        assertEquals(toString, payment.toString());
    }
}