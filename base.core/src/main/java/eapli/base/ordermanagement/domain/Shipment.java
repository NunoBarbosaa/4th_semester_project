package eapli.base.ordermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Shipment implements ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * Enumerated that contains the method of the shipment method.
     */
    @Enumerated(EnumType.STRING)
    private ShipmentMethod method;

    /**
     * Double that contains the cost of the shipment.
     */
    private double cost;

    /**
     * Creates a constructor of shipment that receives by parameter the method and the cost.
     * @param method
     * @param cost
     */
    public Shipment(ShipmentMethod method, double cost) {
        this.method = method;
        this.cost = cost;
    }

    public Shipment() {

    }

    public ShipmentMethod method() {
        return method;
    }

    /**
     * Method toString that return all characteristics of the shipment.
     * @return all characteristics of the shipment.
     */
    @Override
    public String toString() {
        return "Shipment{" +
                "method='" + method + '\'' +
                ", cost=" + cost +
                '}';
    }
}
