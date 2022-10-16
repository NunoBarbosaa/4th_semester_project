package eapli.base.app.backoffice.console.presentation.customer;

import eapli.base.customermanagement.application.RegisterCustomerController;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.customermanagement.dto.CustomerDTO;
import eapli.base.customermanagement.domain.Gender;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.util.*;

public class RegisterCustomerUI extends AbstractUI {

    private final RegisterCustomerController registerCustomerController = new RegisterCustomerController();

    public static final String BILLING_ADDRESS = "Billing Address";
    public static final String DELIVERING_ADDRESS = "Delivering Address";

    @Override
    protected boolean doShow() {
        boolean tryAgain = true;
        do{
            List<AddressDTO> addressDTOList;
            String firstNames = Console.readLine("First Names:");
            String lastNames = Console.readLine("Last Names:");
            String vat = Console.readLine("VAT:");
            String email = Console.readLine("Email Address:");
            String diallingCode = Console.readLine("Dialling Code:");
            long phoneNumber = Console.readLong("Phone Number:");
            addressDTOList = addAddressesToTheCustomer(BILLING_ADDRESS);
            int same;
            if(!addressDTOList.isEmpty()){
                System.out.println("\nPretend to the delivering address be the same as the billing address?");
                System.out.println("1 - Yes\n2 - No");
                same = Console.readOption(1, 2, 2);
                if(same == 1) addressDTOList.addAll(copySameAddresses(addressDTOList));
                else addressDTOList.addAll(addAddressesToTheCustomer(DELIVERING_ADDRESS));
            }
            else{
                addressDTOList.addAll(addAddressesToTheCustomer(DELIVERING_ADDRESS));
            }
            System.out.println("\nPretend to add the birthdate to the customer?");
            System.out.println("1 - Yes\n2 - No");
            int optionBirthDate = Console.readOption(1, 2, 2);
            Calendar date = null;
            if (optionBirthDate == 1) {
                date = Console.readCalendar("Birthdate: (yyyy-MM-dd)", "yyyy-MM-dd");
            }
            System.out.println("\nPretend to add the gender to the customer?");
            System.out.println("1 - Yes\n2 - No");
            int optionGender = Console.readOption(1, 2, 2);
            String gender = null;
            if (optionGender == 1) {
                for (Gender g : EnumSet.allOf(Gender.class)) {
                    System.out.println(g);
                }
                gender = Console.readLine("\nGender:");
            }
            CustomerDTO customerDTO;
            if (date != null) {
                customerDTO = new CustomerDTO(firstNames, lastNames, vat, email, diallingCode, phoneNumber,
                        addressDTOList, date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR), gender);
            } else {
                customerDTO = new CustomerDTO(firstNames, lastNames, vat, email, diallingCode, phoneNumber, addressDTOList,
                        0, 0, 0, gender);
            }
            if(registerCustomerController.registerCustomer(customerDTO)) {
                printCustomerInfo(customerDTO);
                tryAgain = false;
            }
            else {
                System.out.println("Want to try again?");
                System.out.println("1 - Yes\n2 - No");
                int again = Console.readOption(1, 2, 2);
                if (again == 2) {
                    tryAgain = false;
                }
            }
        } while (tryAgain);
        return true;
    }

    private List<AddressDTO> copySameAddresses(List<AddressDTO> addressDTOList) {
        List<AddressDTO> newList = new ArrayList<>();
        AddressDTO addressDTO;
        for (AddressDTO ad : addressDTOList){
            addressDTO = new AddressDTO(ad.postalCode(), ad.street(), ad.doorNumber(), ad.city(), ad.country(), DELIVERING_ADDRESS);
            newList.add(addressDTO);
        }
        return newList;
    }

    public List<AddressDTO> addAddressesToTheCustomer(String type) {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        System.out.println("\n" + type + ":");
        System.out.println("Pretend to add " + type +  " to the customer?");
        System.out.println("1 - Yes\n2 - No");
        int option = Console.readOption(1, 2, 2);
        while(option == 1){
            String postalCode = Console.readLine("\nPostal Code:");
            String street = Console.readLine("Street:");
            int doorNumber = Console.readInteger("Door Number:");
            String city = Console.readLine("City:");
            String country = Console.readLine("Country:");
            AddressDTO address = new AddressDTO(postalCode, street, doorNumber, city, country, type);
            addressDTOList.add(address);
            System.out.println("\nPretend to add more " + type + " to the customer?");
            System.out.println("1 - Yes\n2 - No");
            option = Console.readOption(1, 2, 2);
        }
        return addressDTOList;
    }

    private void printCustomerInfo(CustomerDTO customerDTO) {
        System.out.println("\nFirst Names: " + customerDTO.firstNames());
        System.out.println("Last Names: " + customerDTO.lastNames());
        System.out.println("VAT Number: " + customerDTO.vat());
        System.out.println("Email: " + customerDTO.email());
        System.out.println("Phone Number: " + customerDTO.diallingCode() + " " + customerDTO.phoneNumber());
        AddressPrinter.printAddressInfo(customerDTO.addressDTOList());
        if(customerDTO.dayBirthDate() != 0) System.out.println("\nBirthDate: " + customerDTO.yearBirthDate() + "-" + customerDTO.monthBirthDate() + "-" + customerDTO.dayBirthDate());
        if(customerDTO.gender() != null) System.out.println("Gender: " + customerDTO.gender().toUpperCase());
    }

    @Override
    public String headline() {
        return "Register a Customer";
    }
}

