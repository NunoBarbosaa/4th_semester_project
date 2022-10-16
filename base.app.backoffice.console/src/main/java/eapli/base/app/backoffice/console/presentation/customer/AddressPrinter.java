package eapli.base.app.backoffice.console.presentation.customer;

import eapli.base.customermanagement.dto.AddressDTO;

import java.util.List;

public class AddressPrinter {
    public static void printAddressInfo(List<AddressDTO> addressDTOList) {
        for (AddressDTO addressDTO : addressDTOList) {
            System.out.println("\nType: " + addressDTO.type());
            System.out.println("Street: " + addressDTO.street());
            System.out.println("Postal Code: " + addressDTO.postalCode());
            System.out.println("Door Number: " + addressDTO.doorNumber());
            System.out.println("City: " + addressDTO.city());
            System.out.println("Country: " + addressDTO.country());
        }
    }
}
