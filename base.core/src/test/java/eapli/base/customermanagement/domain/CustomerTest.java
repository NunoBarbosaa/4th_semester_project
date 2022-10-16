package eapli.base.customermanagement.domain;

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

class CustomerTest {

    private final SystemUser user = user();

    public SystemUser user(){
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with("carlitos123", "Password1", "Carlos", "Esteves", "carlosEsteves@gmail.com").withRoles(BaseRoles.ADMIN).build();
    }

    @Test
    void firstNames() {
        Customer customer = new Customer("Roberto", "Nogueira", new VAT("PT123456789"), EmailAddress.valueOf("roberto@gmail.com"),
                new PhoneNumber("+351", 913123456), new ArrayList<>(), new BirthDate(5, 8, 1999), Gender.MALE, user);
        assertEquals("Roberto", customer.firstNames());
    }

    @Test
    void lastNames() {
        Customer customer = new Customer("Roberto", "Nogueira", new VAT("PT123456789"), EmailAddress.valueOf("roberto@gmail.com"),
                new PhoneNumber("+351", 913123456), new ArrayList<>(), new BirthDate(5, 8, 1999), Gender.MALE, user);
        assertEquals("Nogueira", customer.lastNames());
    }

    @Test
    void emailAddress() {
        Customer customer = new Customer("Roberto", "Nogueira", new VAT("PT123456789"), EmailAddress.valueOf("roberto@gmail.com"),
                new PhoneNumber("+351", 913123456), new ArrayList<>(), new BirthDate(5, 8, 1999), Gender.MALE, user);
        assertEquals(EmailAddress.valueOf("roberto@gmail.com"), customer.emailAddress());
    }

    @Test
    void addressList() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address("Rua dos Mosqueteiros", "4123-123", 123, "Famalicão", "Portugal", "Billing");
        Address address2 = new Address("Rua dos Mosqueteiros", "4123-123", 123, "Famalicão", "Portugal", "Delivering");
        addressList.add(address1);
        addressList.add(address2);
        Customer customer = new Customer("Roberto", "Nogueira", new VAT("PT123456789"), EmailAddress.valueOf("roberto@gmail.com"),
                new PhoneNumber("+351", 913123456), addressList, new BirthDate(5, 8, 1999), Gender.MALE, user);
        assertEquals(addressList, customer.addressList());
    }

    @Test
    void testToString() {
        Customer customer = new Customer("Roberto", "Nogueira", new VAT("PT123456789"), EmailAddress.valueOf("roberto@gmail.com"),
                new PhoneNumber("+351", 913123456), new ArrayList<>(), new BirthDate(5, 8, 1999), Gender.MALE, user);
        String toString = "Customer{id=0, firstNames='Roberto', lastNames='Nogueira', vat=VAT{vat='PT123456789'}, emailAddress=roberto@gmail.com, " +
                "phoneNumber=PhoneNumber{diallingCode='+351', number=913123456}, addressList=[], " +
                "birthDate=BirthDate{day=5, month=8, year=1999}, gender=MALE}";
        assertEquals(toString, customer.toString());
    }
}