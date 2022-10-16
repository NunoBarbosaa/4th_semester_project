package eapli.base.ordermanagement.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemDTOTest {

    @Test
    void testToString() {
        OrderItemDTO orderItemDTO = new OrderItemDTO("P8", 10, 1.8, 2);
        String toString = "OrderItemDTO{productID='P8', quantity=10, priceWithoutTaxes=1.8, priceWithTaxes=2.0}";
        assertEquals(toString, orderItemDTO.toString());
    }

    @Test
    void productID() {
        OrderItemDTO orderItemDTO = new OrderItemDTO("P8", 10, 1.8, 2);
        String productID = "P8";
        assertEquals(productID, orderItemDTO.productID());
    }

    @Test
    void quantity() {
        OrderItemDTO orderItemDTO = new OrderItemDTO("P8", 10, 1.8, 2);
        int quantity = 10;
        assertEquals(quantity, orderItemDTO.quantity());
    }

    @Test
    void priceWithoutTaxes() {
        OrderItemDTO orderItemDTO = new OrderItemDTO("P8", 10, 1.8, 2);
        double priceWithoutTaxes = 1.8;
        assertEquals(priceWithoutTaxes, orderItemDTO.priceWithoutTaxes());
    }

    @Test
    void priceWithTaxes() {
        OrderItemDTO orderItemDTO = new OrderItemDTO("P8", 10, 1.8, 2);
        double priceWithTaxes = 2;
        assertEquals(priceWithTaxes, orderItemDTO.priceWithTaxes());
    }
}