package eapli.base.customermanagement.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressDTOTest {

    @Test
    void getPostalCode() {
        AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        String postalCode = "4550-128";
        assertEquals(postalCode, addressDTO.postalCode);
    }

    @Test
    void getStreet() {
        AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        String street = "Rua da Cedofeita";
        assertEquals(street, addressDTO.street);
    }

    @Test
    void getDoorNumber() {
        AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        int doorNumber = 137;
        assertEquals(doorNumber, addressDTO.doorNumber);
    }

    @Test
    void getCity() {
        AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        String city = "Paiva";
        assertEquals(city, addressDTO.city);
    }

    @Test
    void getCountry() {
        AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        String country = "Portugal";
        assertEquals(country, addressDTO.country);
    }

    @Test
    void getType() {
        AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        String type = "Billing Address";
        assertEquals(type, addressDTO.type);
    }

    @Test
    void testToString() {
        AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        String toString = "AddressDTO{postalCode='4550-128', street='Rua da Cedofeita', doorNumber=137, city='Paiva', country='Portugal', type='Billing Address'}";
        assertEquals(toString, addressDTO.toString());
    }
}