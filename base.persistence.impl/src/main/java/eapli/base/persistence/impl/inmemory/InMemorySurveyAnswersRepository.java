package eapli.base.persistence.impl.inmemory;

import eapli.base.surveys.domain.SurveyAnswer;
import eapli.base.surveys.repositories.SurveyAnswersRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemorySurveyAnswersRepository extends InMemoryDomainRepository<SurveyAnswer, Long> implements SurveyAnswersRepository {

    @Override
    public int numberAnswerQuestion(String surveyID, int sectionID, int questionID, String answer) {
        Iterable<SurveyAnswer> surveyAnswers = match(e -> e.surveyId().equals(surveyID)
                && e.sectionId() == sectionID && e.questionId() == questionID && e.rawAnswer().equals(answer));
        int count = 0;
        while(surveyAnswers.iterator().hasNext()){
            count++;
        }
        return count;
    }

    @Override
    public int totalAnswersQuestion(String surveyID, int sectionID, int questionID) {
        return numberQuestion(surveyID, sectionID, questionID);
    }

    @Override
    public int numberCustomersAnswerQuestion(String surveyID, int sectionID, int questionID) {
        return numberQuestion(surveyID, sectionID, questionID);
    }

    private int numberQuestion(String surveyID, int sectionID, int questionID) {
        Iterable<SurveyAnswer> surveyAnswers = match(e -> e.surveyId().equals(surveyID)
                && e.sectionId() == sectionID && e.questionId() == questionID);
        int count = 0;
        while(surveyAnswers.iterator().hasNext()){
            count++;
        }
        return count;
    }

    @Override
    public int averageAnswer(String surveyID, int sectionID, int questionID) {
        Iterable<SurveyAnswer> surveyAnswers = match(e -> e.surveyId().equals(surveyID) && e.sectionId() == sectionID && e.questionId() == questionID);
        Iterator<SurveyAnswer> iterator = surveyAnswers.iterator();
        int avg = 0, count = 0;
        SurveyAnswer surveyAnswer;
        while(iterator.hasNext()){
            surveyAnswer = iterator.next();
            avg = Integer.parseInt(surveyAnswer.rawAnswer());
            count++;
        }
        if(avg == 0) return 0;
        return avg / count;
    }

    @Override
    public List<String> combinations(String surveyID, int sectionID, int questionID) {
        Iterable<SurveyAnswer> surveyAnswers = answers(surveyID, sectionID, questionID);
        Iterator<SurveyAnswer> iterator = surveyAnswers.iterator();
        List<String> stringList = new ArrayList<>();
        SurveyAnswer surveyAnswer;
        while(iterator.hasNext()){
            surveyAnswer = iterator.next();
            if(!stringList.contains(surveyAnswer.rawAnswer())) stringList.add(surveyAnswer.rawAnswer());
        }
        return stringList;
    }

    @Override
    public List<String> allAnswers(String surveyID, int sectionID, int questionID) {
        Iterable<SurveyAnswer> surveyAnswers = answers(surveyID, sectionID, questionID);
        Iterator<SurveyAnswer> iterator = surveyAnswers.iterator();
        List<String> stringList = new ArrayList<>();
        while(iterator.hasNext()){
            stringList.add(iterator.next().rawAnswer());
        }
        return stringList;
    }

    private Iterable<SurveyAnswer> answers(String surveyID, int sectionID, int questionID){
        return match(e -> e.surveyId().equals(surveyID) && e.sectionId() == sectionID && e.questionId() == questionID);
    }
}
