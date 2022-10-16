package eapli.base.surveys.dto;

import eapli.base.surveys.domain.QuestionType;

public class SurveyAnswerDto {

    private final String systemUser;
    private final String idSurvey;

    private final int idSection;

    private final int idQuestion;

    private final QuestionType type;

    private final String rawAnswer;

    public SurveyAnswerDto(String systemUser, String idSurvey, int idSection, int idQuestion, QuestionType type, String rawAnswer) {
        this.systemUser = systemUser;
        this.idSurvey = idSurvey;
        this.idSection = idSection;
        this.idQuestion = idQuestion;
        this.type = type;
        this.rawAnswer = rawAnswer;
    }

    public String answeredBy() {
        return systemUser;
    }

    public String surveyId() {
        return idSurvey;
    }

    public int sectionId() {
        return idSection;
    }

    public int questionId() {
        return idQuestion;
    }

    public QuestionType questionType() {
        return type;
    }

    public String rawAnswer() {
        return rawAnswer;
    }
}
