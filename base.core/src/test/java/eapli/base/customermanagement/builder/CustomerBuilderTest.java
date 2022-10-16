package eapli.base.customermanagement.builder;

import eapli.base.customermanagement.domain.*;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.NilPasswordPolicy;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBuilderTest {

    private final SystemUser user = user();

    public SystemUser user(){
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with("carlitos123", "Password1", "Carlos", "Esteves", "carlosEsteves@gmail.com").withRoles(BaseRoles.ADMIN).build();
    }

    @Test
    void setFirstNames() {
        List<AddressDTO> addressList = new ArrayList<>();
        AddressDTO address1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO address2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto Manuel Joaquim Lopes da Silva Almeida Pinto").changeLastNames("Nogueira")
                .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changePhoneNumber("+351", 913123718)
                .changeAddress(addressList).changeBirthDate(8, 9, 1990).changeGender("male");
        });
        assertEquals("First Names must have less than 50 characters.", exception.getMessage());
    }

    @Test
    void setLastNames() {
        List<AddressDTO> addressList = new ArrayList<>();
        AddressDTO address1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO address2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto")
                .changeLastNames("Nogueira Manuel Joaquim Lopes da Silva Almeida Pinto Manuel Joaquim Lopes da Silva Almeida Pinto Manuel Joaquim Lopes da Silva Almeida Pinto")
                    .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changePhoneNumber("+351", 913123718)
                    .changeAddress(addressList).changeBirthDate(8, 9, 1990).changeGender("male");
        });
        assertEquals("Last Names must have less than 100 characters.", exception.getMessage());
    }

    @Test
    void setVAT() {
        List<AddressDTO> addressList = new ArrayList<>();
        AddressDTO address1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO address2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                    .changeVAT("P11011").changeEmail("roberto@gmail.com").changePhoneNumber("+351", 913123718)
                    .changeAddress(addressList).changeBirthDate(8, 9, 1990).changeGender("male");
        });
        assertEquals("VAT number has an incorrect format.", exception.getMessage());
    }

    @Test
    void setEmail() {
        List<AddressDTO> addressList = new ArrayList<>();
        AddressDTO address1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO address2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                    .changeVAT("PT123456789").changeEmail("roberto").changePhoneNumber("+351", 913123718)
                    .changeAddress(addressList).changeBirthDate(8, 9, 1990).changeGender("male");
        });
        assertEquals("Invalid E-mail format", exception.getMessage());
    }

    @Test
    void setPhoneNumber() {
        List<AddressDTO> addressList = new ArrayList<>();
        AddressDTO address1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO address2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                    .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changePhoneNumber("351", 913237184)
                    .changeAddress(addressList).changeBirthDate(8, 9, 1990).changeGender("male");
        });
        assertEquals("Invalid format for Phone Number. (eg. +351911221345).", exception1.getMessage());
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                    .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changePhoneNumber("+351", 937184)
                    .changeAddress(addressList).changeBirthDate(8, 9, 1990).changeGender("male");
        });
        assertEquals("Number should have 12 numbers.", exception2.getMessage());
    }

    @Test
    void setBirthDate() {
        List<AddressDTO> addressList = new ArrayList<>();
        AddressDTO address1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO address2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                    .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changePhoneNumber("+351", 913237184)
                    .changeAddress(addressList).changeBirthDate(48, 9, 1990).changeGender("male");
        });
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void setGender() {
        List<AddressDTO> addressList = new ArrayList<>();
        AddressDTO address1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO address2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                    .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changePhoneNumber("+351", 913237184)
                    .changeAddress(addressList).changeBirthDate(8, 9, 1990).changeGender("other");
        });
        assertEquals("Gender not found.", exception.getMessage());
    }

    @Test
    void build() {
        List<AddressDTO> addressList1 = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO addressDTO2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList1.add(addressDTO1);
        addressList1.add(addressDTO2);
        List<Address> addressList2 = new ArrayList<>();
        Address address1 = new Address("Rua da Loura", "4150-123", 138, "Porto", "Portugal", "Billing");
        Address address2 = new Address("Rua da Loura", "4150-123", 138, "Porto", "Portugal", "Delivering");
        addressList2.add(address1);
        addressList2.add(address2);
        CustomerBuilder customerBuilder = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                    .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changePhoneNumber("+351", 913237184)
                    .changeAddress(addressList1).changeBirthDate(8, 9, 1990).changeGender("male").changeSystemUser(user);
        Customer customer = new Customer("Roberto", "Nogueira", new VAT("PT123456789"), EmailAddress.valueOf("roberto@gmail.com"),
                new PhoneNumber("+351", 913237184), addressList2, new BirthDate(8, 9, 1990), Gender.MALE, user);
        assertEquals(customer.toString(), customerBuilder.build().toString());
    }

    @Test
    void buildWithIllegalArgumentException(){
        List<AddressDTO> addressList1 = new ArrayList<>();
        AddressDTO addressDTO1 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Billing");
        AddressDTO addressDTO2 = new AddressDTO("4150-123", "Rua da Loura", 138, "Porto", "Portugal", "Delivering");
        addressList1.add(addressDTO1);
        addressList1.add(addressDTO2);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerBuilder customerBuilder2 = new CustomerBuilder().changeFirstNames("Roberto").changeLastNames("Nogueira")
                .changeVAT("PT123456789").changeEmail("roberto@gmail.com").changeAddress(addressList1).
                    changeBirthDate(8, 9, 1990).changeGender("male");
            customerBuilder2.build();
        });
        assertNull(exception.getMessage());
    }
}