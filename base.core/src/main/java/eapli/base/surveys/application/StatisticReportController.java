package eapli.base.surveys.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.surveys.domain.Survey;
import eapli.base.surveys.dto.SurveyDto;
import eapli.base.surveys.repositories.SurveyAnswersRepository;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.base.surveys.repositories.SurveyTargetRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.ArrayList;
import java.util.List;

public class StatisticReportController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final SurveyRepository surveyRepository = PersistenceContext.repositories().surveys();

    private final SurveyTargetRepository surveyTargetRepository = PersistenceContext.repositories().surveyTargets();

    private final SurveyAnswersRepository surveyAnswersRepository = PersistenceContext.repositories().surveyAnswers();

    public List<SurveyDto> allSurveys() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        Iterable<Survey> iterable = surveyRepository.findAll();
        List<SurveyDto> surveyDtoList = new ArrayList<>();
        for(Survey survey : iterable){
            surveyDtoList.add(survey.toDTO());
        }
        return surveyDtoList;
    }

    public int numberCustomersRequestedToAnswer(String id) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        return surveyTargetRepository.numberCustomersRequestedToAnswer(id);
    }

    public int numberCustomersAnswered(String id) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        return surveyTargetRepository.numberCustomersAnswered(id);
    }

    public double getPercentageAnswerQuestion(String surveyID, int sectionID, int questionID, String answer) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        int answers = surveyAnswersRepository.numberAnswerQuestion(surveyID, sectionID, questionID, answer);
        int total = surveyAnswersRepository.totalAnswersQuestion(surveyID, sectionID, questionID);
        if(total != 0) return (double) answers / total * 100;
        else return 0;
    }

    public int numberCustomersAnswerQuestion(String surveyID, int sectionID, int questionID) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        return surveyAnswersRepository.numberCustomersAnswerQuestion(surveyID, sectionID, questionID);
    }

    public int averageAnswer(String surveyID, int sectionID, int questionID) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        return surveyAnswersRepository.averageAnswer(surveyID, sectionID, questionID);
    }

    public List<String> combinations(String surveyID, int sectionID, int questionID) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        return surveyAnswersRepository.combinations(surveyID, sectionID, questionID);
    }

    public List<String> allAnswers(String surveyID, int sectionID, int questionID) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_MANAGER);
        return surveyAnswersRepository.allAnswers(surveyID, sectionID, questionID);
    }
}
