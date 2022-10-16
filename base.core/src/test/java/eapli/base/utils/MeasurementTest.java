package eapli.base.utils;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MeasurementTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensureUnitCannotBeNull(){
        new Measurement(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureQtyCannotBeNegative(){
        new Measurement(-1, Unit.METER);
    }

    @Test
    public void testCompareTo(){
        Measurement measurement1 = new Measurement(34, Unit.CENTIMETER);
        Measurement measurement2 = new Measurement(12, Unit.MILLIMETER);

        assertEquals(1, measurement1.compareTo(measurement2));
        assertEquals(-1, measurement2.compareTo(measurement1));
    }

    @Test
    public void testToMeterConversion(){
        Measurement measurementInMeter = new Measurement(12, Unit.METER);
        Measurement measurementInKiloM = new Measurement(1, Unit.KILOMETER);
        Measurement measurementInCM = new Measurement(100, Unit.CENTIMETER);
        Measurement measurementInMM = new Measurement(1000, Unit.MILLIMETER);
        assertEquals(12, measurementInMeter.toMeters());
        assertEquals(1000, measurementInKiloM.toMeters());
        assertEquals(1, measurementInCM.toMeters());
        assertEquals(1, measurementInMM.toMeters());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidToMeterConversion(){
        Measurement measurement = new Measurement(13, Unit.KILOGRAM);

        measurement.toMeters();
    }

    @Test
    public void testGetMethods(){
        Measurement measurement = new Measurement(12, Unit.METER);

        assertEquals(12, measurement.quantity());
        assertEquals(Unit.METER, measurement.unit());
    }

    @Test
    public void testToString(){
        assertEquals("12.0 METER", new Measurement(12, Unit.METER).toString());
    }

}