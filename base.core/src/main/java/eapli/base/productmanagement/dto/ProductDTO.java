package eapli.base.productmanagement.dto;

import eapli.framework.representations.dto.DTO;

import java.lang.annotation.Annotation;

public class ProductDTO implements DTO {
    public String internalCode;
    public String shortDescription;
    public String longDescription;
    public double price;

    public ProductDTO(String internalCode, String shortDescription, String longDescription, double price) {
        this.internalCode = internalCode;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
