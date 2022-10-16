package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Barcode implements ValueObject {

    /**
     * barcode format : EAN13
     */

    private String data;

    protected Barcode() {
    }

    public Barcode(String data) {
        setData(data);
    }

    private boolean setData(String data) {
        this.data = data;
        return true;
    }


    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        if (data == null) return "no barcode";

        return "Barcode{" +
                "data='" + data + '\'' +
                '}';
    }
}
