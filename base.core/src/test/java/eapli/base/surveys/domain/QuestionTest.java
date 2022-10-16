package eapli.base.surveys.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThatFreeTextQuestionsCannotAcceptDifferentPossibleAnswers(){
        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add("Answer1");
        possibleAnswers.add("Other");
        new Question(1, "Question?", "No instructions", new Obligatoriness("m"),
                "Free-text", possibleAnswers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatNumericQuestionsCannotAcceptDifferentPossibleAnswers(){
        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add("Answer1");
        possibleAnswers.add("Other");
        new Question(1, "Question?", "No instructions", new Obligatoriness("m"),
                "Numeric", possibleAnswers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatYesNoQuestionsCannotAcceptDifferentPossibleAnswers(){
        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add("Answer1");
        possibleAnswers.add("Other");
        new Question(1, "Question?", "No instructions", new Obligatoriness("m"),
                "Yes/No", possibleAnswers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidQuestionTypeIsNotAccepted(){
        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add("Answer1");
        possibleAnswers.add("Other");
        new Question(1, "Question?", "No instructions", new Obligatoriness("m"),
                "Whatever", possibleAnswers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionPromptCannotBeNull(){
        new Question(1, null, "No instructions", new Obligatoriness("m"),
                "Numeric");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionPromptCannotBeEmpty(){
        new Question(1, "", "No instructions", new Obligatoriness("m"),
                "Numeric");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionTypeCannotBeNull(){
        new Question(1, "Prompt", "No instructions", new Obligatoriness("m"),
                null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionTypeScalingRequiresPossibleAnswers(){
        new Question(1, "Prompt", "No instructions", new Obligatoriness("m"),
                "Scaling", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionTypeScalingRequiresPossibleAnswers2(){
        new Question(1, "Prompt", "No instructions", new Obligatoriness("m"),
                "Scaling");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionTypeMultipleAnswersRequiresPossibleAnswers(){
        new Question(1, "Prompt", "No instructions", new Obligatoriness("m"),
                "Multiple-choice", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionTypeMultipleAnswersRequiresPossibleAnswers2(){
        new Question(1, "Prompt", "No instructions", new Obligatoriness("m"),
                "Multiple-choice");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionTypeSingleChoiceRequiresPossibleAnswers(){
        new Question(1, "Prompt", "No instructions", new Obligatoriness("m"),
                "Single-choice", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatQuestionTypeSingleChoiceRequiresPossibleAnswers2(){
        new Question(1, "Prompt", "No instructions", new Obligatoriness("m"),
                "Single-choice");
    }

}