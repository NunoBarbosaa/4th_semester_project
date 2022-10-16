package eapli.base.customermanagement.application;

import eapli.base.customermanagement.builder.CustomerBuilder;
import eapli.base.customermanagement.dto.CustomerDTO;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.base.customermanagement.domain.*;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.usermanagement.domain.UserBuilderHelper;
import eapli.framework.application.UseCaseController;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import java.util.Locale;

@UseCaseController
public class RegisterCustomerController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CustomerRepository customerRepository = PersistenceContext.repositories().customers();
    private final UserRepository userRepository = PersistenceContext.repositories().users();
    private final TransactionalContext txCtx = PersistenceContext.repositories().newTransactionalContext();
    private final static String PASSWORD_DEFAULT = "DEFAULT1";

    public boolean registerCustomer(CustomerDTO customerDTO) {
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK);

        CustomerBuilder customerBuilder = new CustomerBuilder();
        SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        SystemUser newUser;

        txCtx.beginTransaction();

        String userName;
        userName = customerDTO.firstNames().toLowerCase(Locale.ROOT) + customerDTO.lastNames();

        try {
            userBuilder.withUsername(userName)
                    .withPassword(PASSWORD_DEFAULT)
                    .withName(customerDTO.firstNames(), customerDTO.lastNames())
                    .withEmail(customerDTO.email())
                    .withRoles(BaseRoles.CUSTOMER_USER);
            newUser = saveUser(userBuilder.build());

            customerBuilder.changeFirstNames(customerDTO.firstNames())
                    .changeLastNames(customerDTO.lastNames())
                    .changeVAT(customerDTO.vat())
                    .changeEmail(customerDTO.email())
                    .changePhoneNumber(customerDTO.diallingCode(), customerDTO.phoneNumber())
                    .changeAddress(customerDTO.addressDTOList())
                    .changeBirthDate(customerDTO.dayBirthDate(), customerDTO.monthBirthDate(), customerDTO.yearBirthDate())
                    .changeGender(customerDTO.gender())
                    .changeSystemUser(newUser);
            saveCustomer(customerBuilder.build());

            txCtx.commit();
        }
        catch (Exception e){
            txCtx.rollback();
            return false;
        }

        return true;
    }

    private Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    private SystemUser saveUser(SystemUser systemUser) { return userRepository.save(systemUser); }

}
