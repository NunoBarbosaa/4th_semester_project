package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Brand implements ValueObject {

    private String name;

    protected Brand(){}

    public Brand(String name) {
        checkBrandLength(name);
        this.name = name;
    }

    private boolean checkBrandLength(String name) {
        if (name.length() > 50)
            throw new IllegalArgumentException("Brand name can't have more than 50 chars");
        return true;
    }

    @Override
    public String toString() {
        if(name == null)return "no brand";

        return name;
    }
}
