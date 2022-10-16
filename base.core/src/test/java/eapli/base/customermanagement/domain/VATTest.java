package eapli.base.customermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VATTest {

    @Test
    void invalidFormat(){
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            VAT vat = new VAT("P123489");
        });
        assertEquals(exception1.getMessage(), "VAT number has an incorrect format.");
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            VAT vat = new VAT("PT1");
        });
        assertEquals(exception2.getMessage(), "VAT number has an incorrect format.");
        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
            VAT vat = new VAT("PT1234567891011");
        });
        assertEquals("VAT number has an incorrect format.", exception3.getMessage());
    }

    @Test
    void testToString() {
        VAT vat = new VAT("PT123456789");
        String toString = "VAT{vat='PT123456789'}";
        assertEquals(toString, vat.toString());
    }
}