package eapli.base.surveys.repositories;

import eapli.base.surveys.domain.Survey;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends DomainRepository<String, Survey> {

    public Optional<Survey> findBySurveyId(String id);

    public List<String> findTargetCustomers(String query);

}
