package eapli.base.surveys.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Embeddable
public class Obligatoriness implements ValueObject {

    @Enumerated(EnumType.STRING)
    private ObligatorinessType type;

    private int sectionIdDependent;

    private int questionIdDependent;

    private String answerDependent;

    protected Obligatoriness(){}
    public Obligatoriness(String type){
        if((this.type = interpretType(type)).equals(ObligatorinessType.CONDITIONAL))
            throw new IllegalStateException("Obligatoriness Type Conditional needs a set of conditions");
    }
    public Obligatoriness(String type, int sectionIdDependent, int questionIdDependent, String answerDependent){
        this.type = interpretType(type);
        if(questionIdDependent == 0){
            throw new IllegalArgumentException("Question Id cannot be 0");
        }
        this.questionIdDependent = questionIdDependent;
        if(sectionIdDependent == 0)
            throw new IllegalArgumentException("Section Id cannot be 0");
        this.sectionIdDependent = sectionIdDependent;
        if (answerDependent == null || answerDependent.isEmpty() || answerDependent.isBlank())
            throw new IllegalArgumentException("Answer dependent should not be null or empty");
        this.answerDependent = answerDependent;
    }

    public ObligatorinessType type() {
        return type;
    }

    public int sectionIdDependency() {
        return sectionIdDependent;
    }

    public int questionIdDependency() {
        return questionIdDependent;
    }

    public String answerDependency() {
        return answerDependent;
    }

    private ObligatorinessType interpretType(String type) {
        if(type.equals("m"))
            return ObligatorinessType.MUST_ANSWER;
        if(type.equals("c"))
            return ObligatorinessType.CONDITIONAL;
        if(type.equals("o"))
            return ObligatorinessType.OPTIONAL;
        throw new IllegalArgumentException("Obligatoriness Type is invalid");


    }
}
