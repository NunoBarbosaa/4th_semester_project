package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.domain.SurveyTargetCustomer;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.base.surveys.repositories.SurveyTargetRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

public class JpaSurveyTargetsRepository extends JpaAutoTxRepository<SurveyTargetCustomer, Long, Long> implements SurveyTargetRepository {
    public JpaSurveyTargetsRepository(TransactionalContext tx) {
        super(tx, "pk");
    }

    public JpaSurveyTargetsRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "pk");
    }

    @Override
    public List<Survey> surveysForCustomer(String userId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        List<SurveyTargetCustomer> list = match("e.idSystemUser =: id AND e.answered = 0", params);
        List<Survey> surveys = new ArrayList<>();
        for (SurveyTargetCustomer target : list)
            surveys.add(target.survey());
        return surveys;
    }

    @Override
    public int nrSurveysForCustomer(String userId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        List<SurveyTargetCustomer> list = match("e.idSystemUser =: id AND e.answered = 0", params);
        return list.size();
    }

    @Override
    public boolean updateTargetedCustomer(String surveyId, String userName) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userName);
        params.put("sId", surveyId);
        Optional<SurveyTargetCustomer> target = matchOne("e.idSystemUser =: id AND e.survey.id =: sId", params);

        if(target.isPresent()){
            target.get().updateAnsweredStatusToTrue();
            return this.save(target.get()) != null;
        } else return false;
    }

    @Override
    public int numberCustomersRequestedToAnswer(String id){
        Query query = super.createNativeQuery(
                "SELECT * FROM SURVEYTARGETCUSTOMER WHERE SURVEY_PK = (SELECT PK FROM SURVEY WHERE SURVEYID = '" + id + "')",
                SurveyTargetCustomer.class);
        return query.getResultList().size();
    }

    @Override
    public int numberCustomersAnswered(String id){
        Query query = super.createNativeQuery(
                "SELECT * FROM SURVEYTARGETCUSTOMER WHERE ANSWERED = 1 AND SURVEY_PK = (SELECT PK FROM SURVEY WHERE SURVEYID = '" + id + "')",
                SurveyTargetCustomer.class);
        return query.getResultList().size();
    }
}
