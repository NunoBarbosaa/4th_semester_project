package eapli.base.customermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirthDateTest {

    @Test
    public void dateFormatInvalid(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            BirthDate birthDate = new BirthDate(40, 7, 2002);
        });
        assertEquals("Invalid date.", exception.getMessage());
    }

    @Test
    void testToString() {
        BirthDate birthDate = new BirthDate(5, 7, 2002);
        String toString = "BirthDate{day=5, month=7, year=2002}";
        assertEquals(toString, birthDate.toString());
    }
}