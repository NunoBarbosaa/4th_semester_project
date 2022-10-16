package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.categorymanagement.application.RegisterCategoryController;
import eapli.base.customermanagement.application.RegisterCustomerController;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.domain.PhoneNumber;
import eapli.base.customermanagement.domain.VAT;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.customermanagement.dto.CustomerDTO;
import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.EmailAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(CustomerBootstrapper.class);
    @Override
    public boolean execute() {
        ArrayList< AddressDTO> addressDTOS=new ArrayList<>();
        addressDTOS.add(TestDataConstants.addressDTO);
        register(TestDataConstants.FIRST_NAME,TestDataConstants.LAST_NAME,TestDataConstants.VAT,TestDataConstants.EMAIL,TestDataConstants.DIALCODE,TestDataConstants.PHONENUMBER,addressDTOS,TestDataConstants.BIRTHDAY,TestDataConstants.MONTHBIRTHDAY,TestDataConstants.YEARBIRTHDAY,TestDataConstants.gender);
        register("Laverne", "Garrick", "FR123233434", "garricklaverne@gmail.com", "+044", 933099873, new ArrayList<>(), 12, 2, 1999,"MALE");
        register("Tracy", "Virgil", "EN098978678", "tracy@hotmail.com", "+212", 454489344, new ArrayList<>(), 2, 7, 1979,"FEMALE");
        register("Rian", "Kevyn", "EN923722632", "kevyn.rian@yahoo.com", "+212", 344566234, new ArrayList<>(), 6, 12, 2000,"MALE");

        return true;
    }

    private void register(final String firstname, final String lastName, final String vat, final String email, final String dialcode, long phoneNumber, List<AddressDTO> addressDTOS,int birthDay, int monthBirthday, int yearBirtday,String gender){

        final RegisterCustomerController controller = new RegisterCustomerController();
        try {
           CustomerDTO customerDTO=new CustomerDTO(firstname,lastName,vat,email,dialcode,phoneNumber,addressDTOS,birthDay,monthBirthday,yearBirtday,gender);
           controller.registerCustomer(customerDTO);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", firstname);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}
