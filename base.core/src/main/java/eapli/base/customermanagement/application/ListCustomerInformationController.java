package eapli.base.customermanagement.application;

import eapli.base.customermanagement.domain.Address;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.customermanagement.dto.CustomerDTO;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListCustomerInformationController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CustomerRepository customerRepository = PersistenceContext.repositories().customers();

    public List<AddressDTO> customerAddresses(String clientVat){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_CLERK, BaseRoles.ADMIN);
        List<Address> addressList = customerRepository.findByVAT(clientVat).addressList();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        AddressDTO addressDTO;
        for (Address address : addressList){
            addressDTO = new AddressDTO(address.postalCode(), address.street(), address.doorNumber(), address.city(),
                    address.country(), address.type().toString());
            addressDTOList.add(addressDTO);
        }
        return addressDTOList;
    }

    public List<CustomerDTO> customers(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_CLERK, BaseRoles.ADMIN);
        Iterable<Customer> iterable = customerRepository.findAllCustomers();
        Iterator<Customer> iterator = iterable.iterator();
        if(!iterator.hasNext()) return new ArrayList<>();
        List<CustomerDTO> customers = new ArrayList<>();
        CustomerDTO customerDTO = null;
        Customer customer;
        while (iterator.hasNext()){
            customer = iterator.next();
            customerDTO = new CustomerDTO(customer.firstNames(), customer.lastNames(), customer.emailAddress().toString());
        }
        customers.add(customerDTO);
        return customers;
    }

}
