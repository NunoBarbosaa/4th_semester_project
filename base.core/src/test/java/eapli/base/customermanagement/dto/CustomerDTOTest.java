package eapli.base.customermanagement.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void getFirstNames() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
        "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        String firstNames = "Roberto";
        assertEquals(firstNames, customerDTO.firstNames());
    }

    @Test
    void getLastNames() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        String lastNames = "Teixeira Nogueira";
        assertEquals(lastNames, customerDTO.lastNames());
    }

    @Test
    void getVat() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        String vat = "PT123456789";
        assertEquals(vat, customerDTO.vat());
    }

    @Test
    void getEmail() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        String email = "roberto@gmail.com";
        assertEquals(email, customerDTO.email());
    }

    @Test
    void getDiallingCode() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        String diallingCode = "+351";
        assertEquals(diallingCode, customerDTO.diallingCode());
    }

    @Test
    void getPhoneNumber() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        int phoneNumber = 912356123;
        assertEquals(phoneNumber, customerDTO.phoneNumber());
    }

    @Test
    void getAddressDTOList() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        assertEquals(addressDTOList, customerDTO.addressDTOList());
    }

    @Test
    void getDayBirthDate() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        int day = 10;
        assertEquals(day, customerDTO.dayBirthDate());
    }

    @Test
    void getMonthBirthDate() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        int month = 12;
        assertEquals(month, customerDTO.monthBirthDate());
    }

    @Test
    void getYearBirthDate() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        int year = 2001;
        assertEquals(year, customerDTO.yearBirthDate());
    }

    @Test
    void getGender() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        String gender = "male";
        assertEquals(gender, customerDTO.gender());
    }

    @Test
    void testToString() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Billing Address");
        AddressDTO addressDTO2 = new AddressDTO("4550-128", "Rua da Cedofeita", 137, "Paiva", "Portugal", "Delivering Address");
        addressDTOList.add(addressDTO1);
        addressDTOList.add(addressDTO2);
        CustomerDTO customerDTO = new CustomerDTO("Roberto", "Teixeira Nogueira", "PT123456789",
                "roberto@gmail.com", "+351", 912356123, addressDTOList,10, 12, 2001, "male");
        String toString = "CustomerDTO{firstNames='Roberto', lastNames='Teixeira Nogueira', vat='PT123456789', " +
                "email='roberto@gmail.com', diallingCode='+351', phoneNumber=912356123, " +
                "addressDTOList=[AddressDTO{postalCode='4550-128', street='Rua da Cedofeita', doorNumber=137, city='Paiva', " +
                "country='Portugal', type='Billing Address'}, AddressDTO{postalCode='4550-128', street='Rua da Cedofeita', doorNumber=137, " +
                "city='Paiva', country='Portugal', type='Delivering Address'}], dayBirthDate=10, monthBirthDate=12, yearBirthDate=2001, gender='male'}";
        assertEquals(toString, customerDTO.toString());
    }
}