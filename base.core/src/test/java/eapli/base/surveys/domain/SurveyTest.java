package eapli.base.surveys.domain;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

public class SurveyTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullNameIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", "30-09-2022", null, "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyNameIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", "30-09-2022", "", "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullWelcomeMessageIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", "30-09-2022", "Name", null, "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyWelcomeMessageIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", "30-09-2022", "", "", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullEndMessageIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", "30-09-2022", "Name", "Welcome", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyEndMessageIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", "30-09-2022", "", "Welcome", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullIdIsNotAccepted() throws ParseException {
        new Survey(null, "22-08-2022", "30-09-2022", "Name", "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyIdIsNotAccepted() throws ParseException {
        new Survey("", "22-08-2022", "30-09-2022", "", "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDateIntervalIsValid() throws ParseException {
        new Survey("34123", "30-09-2022", "22-08-2022", "Name", "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBeginDateNullIsNotAccepted() throws ParseException {
        new Survey("34123", null, "22-08-2022", "Name", "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBeginDateEmptyIsNotAccepted() throws ParseException {
        new Survey("34123", "", "22-08-2022", "Name", "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndDateNullIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", null, "Name", "Welcome", "End");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndDateEmptyIsNotAccepted() throws ParseException {
        new Survey("34123", "22-08-2022", "", "Name", "Welcome", "End");
    }

    @Test
    public void testSameSectionCannotBeAddedTwice() throws ParseException {
        Survey su1 = new Survey("34123", "30-07-2022", "22-08-2022", "Name", "Welcome", "End");

        Section s1 = new Section(1,"Section", "");

        assertTrue(su1.addSection(s1));
        assertFalse(su1.addSection(s1));
    }
}