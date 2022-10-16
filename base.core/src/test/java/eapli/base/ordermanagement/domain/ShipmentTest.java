package eapli.base.ordermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    @Test
    void testToString() {
        Shipment shipment = new Shipment(ShipmentMethod.GREEN, 10);
        String toString = "Shipment{method='GREEN', cost=10.0}";
        assertEquals(toString, shipment.toString());
    }
}