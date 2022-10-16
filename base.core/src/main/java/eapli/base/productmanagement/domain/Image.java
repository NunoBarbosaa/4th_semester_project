package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.awt.image.BufferedImage;
import java.util.Set;


@Embeddable
public class Image implements ValueObject {

    private Set<BufferedImage> images;

    public Image (Set<BufferedImage> images){
        this.images = images;
    }
}
