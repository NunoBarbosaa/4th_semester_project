package eapli.base.surveys.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SectionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThatSectionNameCannotBeNull(){
        new Section(1, null, "Hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatSectionNameCannotBeEmpty(){
        new Section(1, "", "Hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatChangingSectionInstructionsToNullValueDoesntWork(){
        new Section(1, "Section 1", null).changeInstructions(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatChangingSectionInstructionsToEmptyValueDoesntWork(){
        new Section(1, "Section 1", null).changeInstructions("");
    }

    @Test
    public void testCannotAddSameQuestionTwiceToTheSameSection(){
        Section s1 = new Section(1, "Section 1", null);

        Question q1 = new Question(1,"How are ya doing?", new Obligatoriness("m"), "Free-text");

        assertTrue(s1.addQuestion(q1));
        assertFalse(s1.addQuestion(q1));
    }




}