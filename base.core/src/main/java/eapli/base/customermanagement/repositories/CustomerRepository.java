package eapli.base.customermanagement.repositories;

import eapli.base.customermanagement.domain.Customer;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface CustomerRepository extends DomainRepository<Long, Customer> {

    /**
     * Returns the customer with the id given if it exists
     * @param id to use in search
     * @return customer if it exists
     */
    public Optional<Customer> findById(Long id);

    /**
     * Returns the customer with the email given if it exists
     * @param email to use in search
     * @return customer if it exists
     */
    public Customer findByEmail(String email);

    /**
     * Returns the customer with the vat given if it exists
     * @param vat
     * @return customer if it exists
     */
    Customer findByVAT(String vat);


    /**
     * Finds all the customers available and returns them
     * @return customers in the database
     */
    public Iterable<Customer> findAllCustomers();


}

