package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.Optional;

public class JpaCustomerRepository extends JpaAutoTxRepository<Customer, Long, Long> implements CustomerRepository {
    public JpaCustomerRepository(TransactionalContext tx) {
        super(tx, "id");
    }

    public JpaCustomerRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }

    @Override
    public Customer findByEmail(String email) {
        TypedQuery<Customer> query = super.createQuery(
                "SELECT c FROM Customer c WHERE c.emailAddress = '"+ email+"'",
                Customer.class);
        return query.getSingleResult();
    }

    @Override
    public Customer findByVAT(String vat) {
         TypedQuery<Customer> query = super.createQuery(
                "SELECT c FROM Customer c WHERE vat = '" + vat + "'",
                Customer.class);
        return query.getSingleResult();
    }

    @Override
    public Iterable<Customer> findAllCustomers() {
        return null;
    }
}