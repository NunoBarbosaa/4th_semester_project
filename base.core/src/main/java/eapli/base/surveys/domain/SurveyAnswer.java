package eapli.base.surveys.domain;

import eapli.base.surveys.dto.SurveyAnswerDto;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;

@Entity
public class SurveyAnswer implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long pk;

    private String systemUser;
    private String idSurvey;

    private int idSection;

    private int idQuestion;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private String rawAnswer;

    public SurveyAnswer(String systemUser, String idSurvey, int idSection, int idQuestion, QuestionType type, String rawAnswer) {
        this.systemUser = systemUser;
        this.idSurvey = idSurvey;
        this.idSection = idSection;
        this.idQuestion = idQuestion;
        this.type = type;
        this.rawAnswer = rawAnswer;
    }

    protected SurveyAnswer() {}

    public SurveyAnswer(SurveyAnswerDto answerDto) {
        this.systemUser = answerDto.answeredBy();
        this.idSurvey = answerDto.surveyId();
        this.idSection = answerDto.sectionId();
        this.idQuestion = answerDto.questionId();
        this.type = answerDto.questionType();
        this.rawAnswer = answerDto.rawAnswer();
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
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

    public String rawAnswer() {
        return rawAnswer;
    }

    public String answeredBy() {
        return systemUser;
    }

    @Override
    public Long identity() {
        return null;
    }
}
