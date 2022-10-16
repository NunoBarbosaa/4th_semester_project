package eapli.base.productmanagement.domain;


import eapli.base.categorymanagement.domain.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.base.utils.Description;
import eapli.framework.domain.model.AggregateRoot;

import eapli.framework.validations.Preconditions;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

@Entity

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "IDPRODUCT")})
public class Product implements AggregateRoot<String> {


    //ORM primary key
    @Id
    @GeneratedValue
    private long pk;

    @Version
    private long version;

    //business id
    @Column(name = "IDPRODUCT")
    private String internalCode;

    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "shortDescription")),
            @AttributeOverride(name = "maxLength", column = @Column(name = "maxLengthShort"))
    })
    @XmlElement
    @JsonProperty
    @Embedded
    private Description shortDescription;

    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "extendedDescription")),
            @AttributeOverride(name = "maxLength", column = @Column(name = "maxLengthExtended"))
    })
    @XmlElement
    @JsonProperty
    @Embedded
    private Description extendedDescription;

    @XmlElement
    @JsonProperty
    private String productionCode;

    @XmlElement
    @JsonProperty
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "technicalDescription")),
            @AttributeOverride(name = "maxLength", column = @Column(name = "maxLengthTech"))
    })
    @Embedded
    private Description technicalDescription;

    @XmlElement
    @JsonProperty
    private double volume;

    @XmlElement
    @JsonProperty
    private double weight;

    @XmlElement
    @JsonProperty
    //@Type( type = "pictures_array", parameters = @org.hibernate.annotations.Parameter(name = "sql_array_type", value = "pic_status"))
    //@Column ( name = "pictures_grid", columnDefinition = "pictures_grid[][]")
    @ElementCollection
    private List<byte[]> images;

    @XmlElement
    @JsonProperty
    @Embedded
    private Barcode barcode;

    @XmlElement
    @JsonProperty
    @Embedded
    private Reference reference;

    @XmlElement
    @JsonProperty
    @Embedded
    private Brand brand;


    @XmlElement
    @JsonProperty
    @Embedded
    @Column(name = "PRICE", nullable = false)
    private Price price;

    @XmlElement
    @JsonProperty
    private boolean active;

    @ManyToOne
    private Category category;

    @XmlElement
    @JsonProperty
    @Embedded
    @Column(name = "LOCATION", nullable = false)
    @AttributeOverrides({
            @AttributeOverride(name = "shelf", column = @Column(name = "shelfN")),
            @AttributeOverride(name = "aisleRow", column = @Column(name = "aislerowN")),
            @AttributeOverride(name = "aisle", column = @Column(name = "aisleN"))
    })
    private WarehouseLocation warehouseLocation;

    @Transient
    private static final Description TECHNICAL_DESCRIPTION_OMISSION = new Description("No technical description");

    @Transient
    private static final double VOLUME_OMISSION = 0;

    @Transient
    private static final double WEIGHT_OMISSION = 0;


    protected Product() {
    }


    public Product(String intC, Description shortDescription, Description extendedDescription, Barcode barcode) {
        if(!setInternalCode(intC)){
            throw new IllegalArgumentException("Invalid internal Code");
        }
        if(!setShortDescription(shortDescription)){
            throw new IllegalArgumentException("Invalid short description");

        }
        if(!setExtendedDescription(extendedDescription)){
            throw new IllegalArgumentException("Invalid extended description");
        }
        setBarcode(barcode);
        this.internalCode = intC;
        this.shortDescription = shortDescription;
        this.extendedDescription = extendedDescription;
        this.images = null;
        this.weight = WEIGHT_OMISSION;
        this.volume = VOLUME_OMISSION;
        this.technicalDescription = TECHNICAL_DESCRIPTION_OMISSION;
        this.active = true;
        //this.version = 0L;
        this.price = new Price(0, 0);
        this.warehouseLocation = new WarehouseLocation(0, 0, 0);
    }

    public Product(String intC, Description shortDescription, Description extendedDescription, Barcode barcode, double weight, double volume, double priceNo, double price) {
        if(!setInternalCode(intC)){
            throw new IllegalArgumentException("Invalid internal Code");
        }
        if(!setShortDescription(shortDescription)){
            throw new IllegalArgumentException("Invalid short description");

        }
        if(!setExtendedDescription(extendedDescription)){
            throw new IllegalArgumentException("Invalid extended description");
        }
        setBarcode(barcode);
        this.internalCode = intC;
        this.shortDescription = shortDescription;
        this.extendedDescription = extendedDescription;
        this.images = null;
        this.weight = weight;
        this.volume = volume;
        this.technicalDescription = TECHNICAL_DESCRIPTION_OMISSION;
        this.active = true;
        //this.version = 0L;
        this.price = new Price(price, priceNo);
        this.warehouseLocation = new WarehouseLocation(0, 0, 0);
    }

    public boolean checkInternalCode(String code) {
        if (code == null ||code.length() >= 23 || code.isEmpty() )
            // throw new IllegalArgumentException("Short description needs to have 30 chars maximum");
            return false;
        return true;
    }

    public boolean checkShortDescription(Description shortD) {
        if (shortD == null ||shortD.description().isEmpty()|| shortD.description().length() > 30)
            //throw new IllegalArgumentException("Short description needs to have 30 chars maximum");
            return false;
        return true;
    }

    public boolean checkExtendedDescription(Description shortD) {
        if (shortD == null || shortD.description().isEmpty() || !(shortD.length() > 20 && shortD.length() <= 100) )
            //throw new IllegalArgumentException("Extended description needs to have 100 chars maximum and 20 chars minimmum");
            return false;
        return true;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public boolean setInternalCode(String internalCode) {
        if (checkInternalCode(internalCode)) {
            this.internalCode = internalCode;
            return true;
        }
        return false;
    }

    public Description getShortDescription() {
        return shortDescription;
    }

    public boolean setShortDescription(Description shortDescription) {
        if (checkShortDescription(shortDescription)) {
            this.shortDescription = shortDescription;
            return true;
        }
        return false;
    }

    public Description getExtendedDescription() {
        return extendedDescription;
    }

    public boolean setExtendedDescription(Description extendedDescription) {
        if (checkExtendedDescription(extendedDescription)) {
            this.extendedDescription = extendedDescription;
            return true;
        }
        return false;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public Description getTechnicalDescription() {
        return technicalDescription;
    }

    public void setTechnicalDescription(Description technicalDescription) {
        this.technicalDescription = technicalDescription;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        Preconditions.nonNull(images);
        this.images = images;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        if(barcode == null || barcode.getData() == null)
            throw new IllegalArgumentException("Invalid Barcode");
        this.barcode = barcode;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        Preconditions.nonNull(reference);
        this.reference = reference;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        Preconditions.nonNull(brand);
        this.brand = brand;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        Preconditions.nonNull(price);
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean state) {
        this.active = state;
    }

    public WarehouseLocation getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(WarehouseLocation warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    @Override
    public String identity() {
        return internalCode;
    }


    @Override
    public boolean sameAs(Object other) {
        if (this == null || other == null)
            return false;

        if (this.getClass() == other.getClass()) {
            Product p = (Product) other;
            return (this.getInternalCode() == p.getInternalCode());
        }
        return false;
    }

    @Override
    public String toString() {
        return " Internal Code: '" + internalCode + '\'' +
                " || Short Description: " + shortDescription +
                " || Brand: " + (brand == null ? "Unknown" : brand) +
                " || Price: " + price +
                " || Category: " + category.description()
                ;
    }

    public String toStringDisplay() {
        StringBuilder s = new StringBuilder();
        s.append("Product - Code : " + internalCode + "\n").append("Short Description : " + shortDescription +
                "\n").append("Extended Description : " + extendedDescription + "\n").append("Technical Description : " +
                "" + technicalDescription + "\n").append("Production Code: " + productionCode + "\n").append("Volume : " +
                "" + volume + "\n").append("Weight : " + weight + "\n").append("Barcode : " + barcode + "\n" +
                "").append("Reference : " + reference + "\n").append("Brand : " + brand + "\n").append(
                        "Price : " + price + "\n").append("Is active : " + isActive() + "\n").append("Category : " + getCategory().description() + "\n");
        return s.toString();
    }
}
