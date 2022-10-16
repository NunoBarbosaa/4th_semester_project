package eapli.base.customermanagement.builder;

import eapli.base.customermanagement.domain.*;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.util.ArrayList;
import java.util.List;

public class CustomerBuilder implements DomainFactory<Customer> {

    public static final int MAX_LENGTH_FIRST_NAMES = 50;

    public static final int MAX_LENGTH_LAST_NAMES = 100;

    private Customer customer;

    private String firstNames;

    private String lastNames;

    private VAT vat;

    private EmailAddress emailAddress;

    private PhoneNumber phoneNumber;

    private List<Address> addressList;

    private BirthDate birthDate;

    private Gender gender;

    private SystemUser systemUser;

    public CustomerBuilder changeFirstNames(String firstNames) {
        if (firstNames.length() > MAX_LENGTH_FIRST_NAMES) {
            throw new IllegalArgumentException(
                    "First Names must have less than 50 characters.");
        }
        this.firstNames = firstNames;
        return this;
    }

    public CustomerBuilder changeLastNames(String lastNames) {
        if (lastNames.length() > MAX_LENGTH_LAST_NAMES) {
            throw new IllegalArgumentException(
                    "Last Names must have less than 100 characters.");
        }
        this.lastNames = lastNames;
        return this;
    }

    public CustomerBuilder changeVAT(String vat) {
        this.vat = new VAT(vat);
        return this;
    }

    public CustomerBuilder changeEmail(String emailAddress) {
        this.emailAddress = EmailAddress.valueOf(emailAddress);
        return this;
    }

    public CustomerBuilder changePhoneNumber(String diallingCode, long phoneNumber) {
        this.phoneNumber = new PhoneNumber(diallingCode, phoneNumber);
        return this;
    }

    public CustomerBuilder changeAddress(List<AddressDTO> addressDTOList){
        Address address;
        List<Address> addressList = new ArrayList<>();
        for (AddressDTO addressDTO : addressDTOList){
            address = new Address(addressDTO.street(), addressDTO.postalCode(), addressDTO.doorNumber(),
                    addressDTO.city(), addressDTO.country(), addressDTO.type());
            addressList.add(address);
        }
        this.addressList = addressList;
        return this;
    }

    public CustomerBuilder changeBirthDate(int dayBirthDate, int monthBirthDate, int yearBirthDate) {
        this.birthDate = new BirthDate(dayBirthDate, monthBirthDate, yearBirthDate);
        return this;
    }

    public CustomerBuilder changeGender(String gender) {
        if (gender == null) {
            this.gender = null;
        } else {
            if (gender.equalsIgnoreCase("male")) {
                this.gender = Gender.MALE;
            } else if (gender.equalsIgnoreCase("female")) {
                this.gender = Gender.FEMALE;
            } else if (gender.equalsIgnoreCase("undefined")) {
                this.gender = Gender.UNDEFINED;
            } else {
                throw new IllegalArgumentException("Gender not found.");
            }
        }
        return this;
    }

    public CustomerBuilder changeSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    private Customer buildOrThrow() {
        if(customer != null){
            return customer;
        }
        else if(firstNames != null && lastNames != null && vat != null && emailAddress != null && phoneNumber != null){
            customer = new Customer(firstNames, lastNames, vat, emailAddress, phoneNumber, addressList, birthDate, gender, systemUser);
            return customer;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public Customer build() {
        return buildOrThrow();
    }

}