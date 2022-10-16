package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.base.warehouse.domain.Agv;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.*;

public class JpaSurveyRepository extends JpaAutoTxRepository<Survey, String, String> implements SurveyRepository {

    public JpaSurveyRepository(TransactionalContext tx) {
        super(tx, "surveyid");
    }

    public JpaSurveyRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "surveyid");
    }

    @Override
    public Optional<Survey> findBySurveyId(String id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return matchOne("e.id =: id", params);
    }

    @Override
    public List<String> findTargetCustomers(String query) {
        Class<Customer> class1 = Customer.class;
        List<Customer>  customers = super.createNativeQuery(query, class1).getResultList();

        //Remove Duplicates
        Set<Customer> set = new LinkedHashSet<>(customers);
        customers.clear();
        customers.addAll(set);

        List<String> usernames = new ArrayList<>();
        for(Customer customer : customers){
            usernames.add(customer.systemUser());
        }
        return usernames;
    }
}
