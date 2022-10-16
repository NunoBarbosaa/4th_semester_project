package eapli.base.customermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void diallingCodeWithoutPlus(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneNumber phoneNumber = new PhoneNumber("351", 914663953);
        });
        assertEquals("Invalid format for Phone Number. (eg. +351911221345).", exception.getMessage());
    }

    @Test
    void phoneNumberHasMoreThanMaxLength(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneNumber phoneNumber = new PhoneNumber("+351", 91466395);
        });
        assertEquals("Number should have 12 numbers.", exception.getMessage());
    }


    @Test
    void testToString(){
        PhoneNumber phoneNumber = new PhoneNumber("+351", 914663953);
        String toString = "PhoneNumber{diallingCode='+351', number=914663953}";
        assertEquals(toString, phoneNumber.toString());
    }
}