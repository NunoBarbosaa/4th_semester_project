package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Reference implements ValueObject {

    private String ref;

    protected Reference(){}

    public Reference(String ref){
        checkReferenceLength(ref);
        this.ref = ref;
    }

    private boolean checkReferenceLength(String ref){
        if(ref.length() >= 23)
            throw new IllegalArgumentException("Reference needs to have 23 chars maximum");
        return true;
    }

    @Override
    public String toString() {
        if(ref == null)return "no reference";
        return "Reference{" +
                "ref='" + ref + '\'' +
                '}';
    }
}
