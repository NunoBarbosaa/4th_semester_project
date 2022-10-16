package eapli.base.surveys.repositories;

import eapli.base.surveys.domain.SurveyAnswer;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;


public interface SurveyAnswersRepository extends DomainRepository<Long, SurveyAnswer> {

    public int numberAnswerQuestion(String surveyID, int sectionID, int questionID, String answer);

    public int totalAnswersQuestion(String surveyID, int sectionID, int questionID);

    public int numberCustomersAnswerQuestion(String surveyID, int sectionID, int questionID);

    public int averageAnswer(String surveyID, int sectionID, int questionID);

    public List<String> combinations(String surveyID, int sectionID, int questionID);

    public List<String> allAnswers(String surveyID, int sectionID, int questionID);
}
