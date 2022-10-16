package eapli.base.persistence.impl.inmemory;

import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryCustomerRepository extends InMemoryDomainRepository<Customer, Long> implements CustomerRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Customer findByEmail(String email) {
        return null;
    }

    @Override
    public Customer findByVAT(String vat) {
        return null;
    }

    @Override
    public Iterable<Customer> findAllCustomers() {
        return findAll();
    }

}
