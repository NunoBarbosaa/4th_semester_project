package eapli.base.utils;



import org.junit.Assert;
import org.junit.Test;

public class DescriptionTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkThatDescriptionWithMoreThanNCharsIsNotAccepted(){
        new Description("This is a description", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkThatDescriptionWithMoreThanNCharsIsNotAccepted2(){
        new Description("This is a description", 5, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkThatDescriptionWithLessThanNCharsIsNotAccepted(){
        new Description("is", 5, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkThatNullDescriptionIsNotAccepted(){
        new Description(null, 5, 3);
    }

    @Test
    public void checkReturnedDescriptionIsCorrect(){
        Description description = new Description("This is a description", 25);
        Assert.assertEquals("This is a description", description.description());
        Assert.assertEquals(25, description.maxLength());
        Assert.assertEquals(21, description.length());
        Assert.assertEquals("This is a description", description.toString());
    }


}