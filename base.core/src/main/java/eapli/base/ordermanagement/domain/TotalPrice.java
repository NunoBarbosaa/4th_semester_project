package eapli.base.ordermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class TotalPrice implements ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * Double that contains the priceWithTaxes.
     */
    private double priceWithTaxes;

    /**
     * Double that contains the priceWithouTaxes.
     */
    private double priceWithoutTaxes;

    /**
     * Creates a constructor receiving by parameter the priceWithTaxes and the priceWithoutTaxes
     * @param priceWithTaxes
     * @param priceWithoutTaxes
     */
    public TotalPrice(double priceWithTaxes, double priceWithoutTaxes) {
        this.priceWithTaxes = priceWithTaxes;
        this.priceWithoutTaxes = priceWithoutTaxes;
    }

    public TotalPrice() {

    }

    public double priceWithTaxes() {
        return priceWithTaxes;
    }

    public double priceWithoutTaxes() {
        return priceWithoutTaxes;
    }

    /**
     * Method toString that return all characteristics of the TotalPrice.
     * @return all characteristics of the TotalPrice.
     */
    @Override
    public String toString() {
        return "TotalPrice{" +
                "priceWithTaxes=" + priceWithTaxes +
                ", priceWithoutTaxes=" + priceWithoutTaxes +
                '}';
    }
}
