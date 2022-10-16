package eapli.base.customermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void testPostalCode(){
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            Address address = new Address("Rua da Cedofeita", "45501128", 317, "Paiva", "Portugal", "Billing Address");
        });
        assertEquals(exception1.getMessage(), "Postal Code invalid.");
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Address address = new Address("Rua da Cedofeita", "455-128", 317, "Paiva", "Portugal", "Billing Address");
        });
        assertEquals(exception2.getMessage(), "Postal Code invalid.");
        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
            Address address = new Address("Rua da Cedofeita", "455-0128", 317, "Paiva", "Portugal", "Billing Address");
        });
        assertEquals(exception3.getMessage(), "Postal Code invalid.");
    }

    @Test
    void testDoorNumber(){
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            Address address = new Address("Rua da Cedofeita", "4550-128", 0, "Paiva", "Portugal", "Billing Address");
        });
        assertEquals(exception1.getMessage(), "Door Number invalid.");
    }

    @Test
    void street() {
        Address address = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Billing Address");
        assertEquals("Rua da Cedofeita", address.street());
    }

    @Test
    void postalCode() {
        Address address = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Billing Address");
        assertEquals("4550-128", address.postalCode());
    }

    @Test
    void doorNumber() {
        Address address = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Billing Address");
        assertEquals(317, address.doorNumber());
    }

    @Test
    void city() {
        Address address = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Billing Address");
        assertEquals("Paiva", address.city());
    }

    @Test
    void country() {
        Address address = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Billing Address");
        assertEquals("Portugal", address.country());
    }

    @Test
    void type() {
        Address address1 = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Billing Address");
        assertEquals(TypeAddress.BILLING, address1.type());
        Address address2 = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Delivering Address");
        System.out.println(address2.type());
        assertEquals(TypeAddress.DELIVERING, address2.type());
    }

    @Test
    void testToString() {
        Address address = new Address("Rua da Cedofeita", "4550-128", 317, "Paiva", "Portugal", "Billing Address");
        String toString = "Address{street='Rua da Cedofeita', postalCode=4550-128, doorNumber=317, city='Paiva', country='Portugal', type=BILLING}";
        assertEquals(address.toString(), toString);
    }
}