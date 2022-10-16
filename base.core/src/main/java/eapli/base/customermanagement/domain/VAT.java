package eapli.base.customermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public class VAT implements ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * String that contains the vat.
     */
    private String vat;

    /**
     * Constructor that creates a VAT receiving by parameter the vat.
     * @param vat
     */
    public VAT(final String vat) {
        Pattern pattern = Pattern.compile("([A-Za-z]{2,4})([a-zA-Z0-9\\-\\_ ]{2,12})");
        if(!vat.matches(String.valueOf(pattern))){
            throw new IllegalArgumentException(
                    "VAT number has an incorrect format.");
        }
        this.vat = vat;
    }

    public VAT() {

    }

    /**
     * Method toString that returns all characteristics of the VAT
     * @return all characteristics of the VAT
     */
    @Override
    public String toString() {
        return "VAT{" +
                "vat='" + vat + '\'' +
                '}';
    }
}
