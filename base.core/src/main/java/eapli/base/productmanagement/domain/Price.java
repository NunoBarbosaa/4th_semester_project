package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Embeddable

public class Price implements ValueObject {


    private double priceWithTaxes;

    private double priceWithoutTaxes;

    public Price(double priceTaxes, double priceNoTaxes){
        this.priceWithoutTaxes = priceNoTaxes;
        this.priceWithTaxes = priceTaxes;
    }

    public Price() {

    }

    public void setPriceWithTaxes(double priceWithTaxes) {
        this.priceWithTaxes = priceWithTaxes;
    }

    public void setPriceWithoutTaxes(double priceWithoutTaxes) {
        this.priceWithoutTaxes = priceWithoutTaxes;
    }

    public double getPriceWithTaxes() {
        return priceWithTaxes;
    }

    public double getPriceWithoutTaxes() {
        return priceWithoutTaxes;
    }

    @Override
    public String toString() {
        return String.format("%.2f€ (%.2f€ (w/o taxes))", priceWithTaxes, priceWithoutTaxes);
    }
}
