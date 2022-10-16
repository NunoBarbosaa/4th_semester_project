package eapli.base.utils;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class Description implements ValueObject {

    private String description;

    private int maxLength;

    protected Description(){}

    public Description (String description){
        Preconditions.nonNull(description);
        this.description = description;
        this.maxLength = 0;
    }

    public Description (String description, int maxLength){
        Preconditions.nonNull(description);
        if(description.length() > maxLength)
            throw new IllegalArgumentException(String.format("Description cannot have more than %d", maxLength));
        this.description = description;
        this.maxLength = maxLength;
    }

    public Description (String description, int maxLength, int minLength){
        Preconditions.nonNull(description);
        if(description.length() > maxLength)
            throw new IllegalArgumentException(String.format("Description cannot have more than %d", maxLength));
        if(description.length() < minLength)
            throw new IllegalArgumentException(String.format("Description cannot have less than %d", minLength));

        this.description = description;
        this.maxLength = maxLength;
    }

    public int length(){
        return this.description.length();
    }

    public String description(){
        return this.description;
    }

    public int maxLength(){
        return this.maxLength;
    }

    @Override
    public String toString() {
        return description;
    }
}
