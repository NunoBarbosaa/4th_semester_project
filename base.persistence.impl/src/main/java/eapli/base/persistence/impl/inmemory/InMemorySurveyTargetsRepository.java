package eapli.base.persistence.impl.inmemory;

import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.domain.SurveyTargetCustomer;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.base.surveys.repositories.SurveyTargetRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemorySurveyTargetsRepository extends InMemoryDomainRepository<SurveyTargetCustomer, Long> implements SurveyTargetRepository {

    @Override
    public List<Survey> surveysForCustomer(String userId) {
        Iterable<SurveyTargetCustomer> targets = match(e -> e.idSystemUser().equals(userId));
        List<Survey> surveys = new ArrayList<>();
        for(SurveyTargetCustomer target : targets)
            surveys.add(target.survey());
        return surveys;
    }

    @Override
    public int nrSurveysForCustomer(String userId) {
        Iterable<SurveyTargetCustomer> targets = match(e -> e.idSystemUser().equals(userId));
        int count = 0;

        while(targets.iterator().hasNext()){
            count++;
        }
        return count;
    }

    @Override
    public boolean updateTargetedCustomer(String surveyId, String userName) {
        Optional<SurveyTargetCustomer> target = matchOne(e -> e.idSystemUser().equals(userName) && e.survey().identity().equals(surveyId));
        if(target.isPresent()){
            target.get().updateAnsweredStatusToTrue();
            return this.save(target.get()) != null;
        } else return false;
    }

    @Override
    public int numberCustomersRequestedToAnswer(String id) {
        Iterable<SurveyTargetCustomer> targets = match(e -> e.survey().identity().equals(id));
        int count = 0;
        while(targets.iterator().hasNext()){
            count++;
        }
        return count;
    }

    @Override
    public int numberCustomersAnswered(String id) {
        Iterable<SurveyTargetCustomer> targets = match(e -> e.survey().identity().equals(id) && e.isAnswered());
        int count = 0;
        while(targets.iterator().hasNext()){
            count++;
        }
        return count;
    }

}
