package eapli.base.ordermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TotalPriceTest {

    @Test
    void testToString() {
        TotalPrice totalPrice = new TotalPrice(12.4, 11.4);
        String toString = "TotalPrice{priceWithTaxes=12.4, priceWithoutTaxes=11.4}";
        assertEquals(toString, totalPrice.toString());
    }
}