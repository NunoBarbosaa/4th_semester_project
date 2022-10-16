package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.surveys.domain.SurveyAnswer;
import eapli.base.surveys.repositories.SurveyAnswersRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.List;

public class JpaSurveyAnswersRepository extends JpaAutoTxRepository<SurveyAnswer, Long, Long> implements SurveyAnswersRepository {

    public JpaSurveyAnswersRepository(TransactionalContext tx) {
        super(tx, "pk");
    }

    public JpaSurveyAnswersRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "pk");
    }

    @Override
    public int numberAnswerQuestion(String surveyID, int sectionID, int questionID, String answer) {
        TypedQuery<Long> query = super.createQuery(
                "SELECT COUNT(rawAnswer) FROM SurveyAnswer WHERE IDSURVEY = '" + surveyID + "' AND IDSECTION = " + sectionID + " AND IDQUESTION = " + questionID + " AND RAWANSWER = '" + answer + "'"
        , Long.class);
        return Math.toIntExact(query.getSingleResult());
    }

    @Override
    public int totalAnswersQuestion(String surveyID, int sectionID, int questionID) {
        TypedQuery<Long> query = super.createQuery(
                "SELECT COUNT(rawAnswer) FROM SurveyAnswer WHERE IDSURVEY = '" + surveyID + "' AND IDSECTION = " + sectionID + " AND IDQUESTION = " + questionID
                , Long.class);
        return Math.toIntExact(query.getSingleResult());
    }

    @Override
    public int numberCustomersAnswerQuestion(String surveyID, int sectionID, int questionID) {
        TypedQuery<Long> query = super.createQuery(
                "SELECT COUNT(*) FROM SurveyAnswer WHERE IDSURVEY = '" + surveyID + "' AND IDSECTION = " + sectionID + " AND IDQUESTION = " + questionID
                , Long.class);
        return Math.toIntExact(query.getSingleResult());
    }

    @Override
    public int averageAnswer(String surveyID, int sectionID, int questionID){
        TypedQuery<Double> query = super.createQuery(
                "SELECT AVG(CAST(RAWANSWER AS int)) FROM SurveyAnswer WHERE IDSURVEY = '" + surveyID + "' AND IDSECTION = " + sectionID + " AND IDQUESTION = " + questionID,
                Double.class);
        if(query.getSingleResult() == null) return 0;
        else return (int) Double.parseDouble(String.valueOf(query.getSingleResult()));
    }

    @Override
    public List<String> combinations(String surveyID, int sectionID, int questionID){
        TypedQuery<String> query = super.createQuery(
                "SELECT DISTINCT rawAnswer FROM SurveyAnswer WHERE IDSURVEY = '" + surveyID + "' AND IDSECTION = " + sectionID + " AND IDQUESTION = " + questionID,
                String.class);
        return query.getResultList();
    }

    @Override
    public List<String> allAnswers(String surveyID, int sectionID, int questionID){
        TypedQuery<String> query = super.createQuery(
                "SELECT rawAnswer FROM SurveyAnswer WHERE IDSURVEY = '" + surveyID + "' AND IDSECTION = " + sectionID + " AND IDQUESTION = " + questionID,
                String.class);
        return query.getResultList();
    }
}
