package eapli.base.surveys.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObligatorinessTest {

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidObligatorinessType(){
        new Obligatoriness("unknown");
    }

    @Test(expected = IllegalStateException.class)
    public void testMissingConditions(){
        new Obligatoriness("c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuestionId0IsNotAccepted(){
        new Obligatoriness("c", 1,0,"Yes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSectionId0IsNotAccepted(){
        new Obligatoriness("c", 0,1,"Yes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnswerDependentNullIsNotAccepted(){
        new Obligatoriness("c", 1,1,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnswerDependentBlankIsNotAccepted(){
        new Obligatoriness("c", 1,1,"");
    }

    @Test
    public void testTypeIsCorrectlyConverted(){
        //Conditional Type
        assertEquals(new Obligatoriness("c", 1,1,"Answer").type(), ObligatorinessType.CONDITIONAL);
        //Must Answer Type
        assertEquals(new Obligatoriness("m").type(), ObligatorinessType.MUST_ANSWER);
        //Optional Type
        assertEquals(new Obligatoriness("o").type(), ObligatorinessType.OPTIONAL);

    }


}