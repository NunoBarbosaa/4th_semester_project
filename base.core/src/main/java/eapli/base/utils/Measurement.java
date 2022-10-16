package eapli.base.utils;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Measurement implements ValueObject, Comparable<Measurement> {

    private Double quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    protected Measurement(){}

    public Measurement (double quantity, Unit unit){
        Preconditions.nonNull(unit, "Measurement unit cannot be null");
        if(quantity < 0)
            throw new IllegalArgumentException("Measurement cannot be negative");
        this.quantity = quantity;
        this.unit = unit;
    }
    public double toMeters(){
        return toMeters(this);
    }
    private double toMeters(Measurement measurement){
        if(measurement.unit == Unit.METER)
            return measurement.quantity;
        else if(measurement.unit == Unit.CENTIMETER)
            return measurement.quantity / 100;
        else if(measurement.unit == Unit.MILLIMETER)
            return measurement.quantity / 1000;
        else if(measurement.unit == Unit.KILOMETER)
            return measurement.quantity * 1000;
        else
            throw new IllegalArgumentException("Not a distance measurement, cannot be converted");
    }

    public Double quantity() {
        return quantity;
    }

    public Unit unit() {
        return unit;
    }

    @Override
    public int compareTo(Measurement o) {
        return Double.compare(toMeters(this), toMeters(o));
    }

    @Override
    public String toString() {
        return quantity + " " + unit.toString();
    }
}
