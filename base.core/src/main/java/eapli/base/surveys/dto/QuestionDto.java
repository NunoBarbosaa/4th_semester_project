package eapli.base.surveys.dto;

import eapli.base.surveys.domain.Obligatoriness;
import eapli.base.surveys.domain.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class QuestionDto implements Comparable<QuestionDto>{

    private final int id;

    private final String questionPrompt;

    private final String instructions;

    private final Obligatoriness obligatoriness;

    private final QuestionType type;

    private final List<String> possibleAnswers;

    public QuestionDto(int id, String questionPrompt, String instructions, Obligatoriness obligatoriness,
                       QuestionType type, List<String> possibleAnswers){

        this.id = id;
        this.questionPrompt = questionPrompt;
        this.instructions = instructions;
        this.obligatoriness = obligatoriness;
        this.type = type;
        this.possibleAnswers = new ArrayList<>(possibleAnswers);
    }

    public int id() {
        return id;
    }

    public String prompt() {
        return questionPrompt;
    }

    public String instructions() {
        return instructions;
    }

    public Obligatoriness obligatoriness() {
        return obligatoriness;
    }

    public QuestionType type() {
        return type;
    }

    public List<String> possibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public int compareTo(QuestionDto o) {
        return this.id()-o.id();
    }
}
