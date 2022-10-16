package eapli.base.surveys.repositories;

import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.domain.SurveyTargetCustomer;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyTargetRepository extends DomainRepository<Long, SurveyTargetCustomer> {

    public List<Survey> surveysForCustomer(String userId);

    public int nrSurveysForCustomer(String userId);

    public boolean updateTargetedCustomer(String surveyPk, String userName);

    public int numberCustomersRequestedToAnswer(String id);

    public int numberCustomersAnswered(String id);

}
