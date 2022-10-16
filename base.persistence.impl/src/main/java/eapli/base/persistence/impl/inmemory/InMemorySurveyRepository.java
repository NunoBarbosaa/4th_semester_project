package eapli.base.persistence.impl.inmemory;

import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemorySurveyRepository extends InMemoryDomainRepository<Survey, String> implements SurveyRepository {

    @Override
    public Optional<Survey> findBySurveyId(String id){
        return matchOne(e -> e.identity().equals(id));
    }

    public List<String> findTargetCustomers(String query){
        return null;
        //TODO in memory query to find target customers
    }
}
