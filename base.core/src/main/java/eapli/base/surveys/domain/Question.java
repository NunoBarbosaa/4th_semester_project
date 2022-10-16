package eapli.base.surveys.domain;

import eapli.framework.domain.model.DomainEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Question implements DomainEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long pk;

    private int id;

    @Column(length = 512)
    private String questionPrompt;

    @Column(length = 512)
    private String questionInstructions;

    @Embedded
    private Obligatoriness obligatoriness;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> possibleAnswers;

    protected Question(){}

    public Question(int id, String questionPrompt, Obligatoriness obligatoriness, String questionType, List<String> possibleAnswers){
        this(id, questionPrompt, null, obligatoriness, questionType, possibleAnswers);
    }

    public Question(int id, String questionPrompt, String questionInstructions, Obligatoriness obligatoriness,
                    String questionType, List<String> possibleAnswers){
        this.id = id;
        if (questionPrompt == null || questionPrompt.isEmpty() || questionPrompt.isBlank())
            throw new IllegalArgumentException("Question Prompt should not be null or empty");
        this.questionPrompt = questionPrompt;
        if(questionInstructions != null)
            this.questionInstructions = questionInstructions;
        this.obligatoriness = obligatoriness;
        if(questionType == null)
            throw new IllegalArgumentException("Question Type should not be null");
        this.questionType = interpretQuestionType(questionType);
        if(this.questionType.equals(QuestionType.FREE_TEXT) || this.questionType.equals(QuestionType.NUMERIC) || this.questionType.equals(QuestionType.YES_OR_NO))
            throw new IllegalArgumentException("This type of question should not have different possible answers");
        else{
            if(possibleAnswers == null || possibleAnswers.isEmpty())
                throw new IllegalArgumentException("Question type '" + questionType + "' requires a list of possible answers");
        }
        this.possibleAnswers = new ArrayList<>(possibleAnswers);
    }

    public Question(int id, String questionPrompt, Obligatoriness obligatoriness, String questionType){
        this(id, questionPrompt, null, obligatoriness, questionType);
    }

    public Question(int id, String questionPrompt, String questionInstructions, Obligatoriness obligatoriness, String questionType){
        this.id = id;
        if (questionPrompt == null || questionPrompt.isEmpty() || questionPrompt.isBlank())
            throw new IllegalArgumentException("Question Prompt should not be null or empty");
        this.questionPrompt = questionPrompt;
        if(questionInstructions != null)
            this.questionInstructions = questionInstructions;
        this.obligatoriness = obligatoriness;
        if(questionType == null)
            throw new IllegalArgumentException("Question Type should not be null");
        this.questionType = interpretQuestionType(questionType);
        if(!(this.questionType.equals(QuestionType.FREE_TEXT) || this.questionType.equals(QuestionType.NUMERIC) || this.questionType.equals(QuestionType.YES_OR_NO)))
            throw new IllegalArgumentException("This type of question should have a list of different possible answers");
    }

    private QuestionType interpretQuestionType(String questionType) {
        if(questionType.equals("Free-text"))
            return QuestionType.FREE_TEXT;
        if(questionType.equals("Single-choice"))
            return QuestionType.SINGLE_CHOICE;
        if(questionType.equals("Multiple-choice"))
            return QuestionType.MULTIPLE_CHOICE;
        if(questionType.equals("Numeric"))
            return QuestionType.NUMERIC;
        if(questionType.equals("Yes/No"))
            return QuestionType.YES_OR_NO;
        if(questionType.equals("Scaling"))
            return QuestionType.SCALING;
        if(questionType.equals("Sorting"))
            return QuestionType.SORTING;
        throw new IllegalArgumentException("Question Type '" + questionType + "' not recognized");
    }

    public String prompt() {
        return questionPrompt;
    }

    public String instructions() {
        return questionInstructions;
    }

    public Obligatoriness obligatoriness() {
        return obligatoriness;
    }

    public QuestionType type() {
        return questionType;
    }

    public List<String> possibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean sameAs(Object other) {
        if(!(other instanceof Question))
            return false;
        Question obj = (Question) other;
        return this.pk == obj.pk;
    }

    @Override
    public Integer identity() {
        return id;
    }
}
