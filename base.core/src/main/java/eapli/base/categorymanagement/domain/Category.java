package eapli.base.categorymanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.base.utils.Description;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "alphaNumericCode")})
public class Category implements AggregateRoot<String> {
    private static final Long serialVersionUID = 1L;
    private final int MAX_CHARS = 50;
    private final int MIN_CHARS = 20;
    private final int CODE_MAX_CHARS =10;

    //ORM primaryKey
    @Id
    @GeneratedValue
    private Long pk;

    //business ID
    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private String alphaNumericCode;

    @Embedded
    @XmlElement
    @JsonProperty
    private Description description;

    @XmlElement
    @JsonProperty
    private boolean active;

    public Description getDescription() {
        return description;
    }

    protected Category(){
    }

    public Category(final String alphaNumericCode, final String description) {
        setCode(alphaNumericCode);
        setDescription(description);
        this.active = true;
    }

    public String getCategoryCode(){
        return alphaNumericCode;
    }
    private void setDescription(final String newDescription) {
        if (descriptionMeetsMinimumRequirements(newDescription)) {
            this.description = new Description(newDescription,MAX_CHARS,MIN_CHARS);
        } else {
            throw new IllegalArgumentException("Invalid Description");
        }
    }

    /**
     * Sets and validates newName.
     *
     * @param newCode
     *            The new DishType name.
     */
    private void setCode(final String newCode) {
        if (codeMeetsMinimumRequirements(newCode) && newCode.length()<=CODE_MAX_CHARS) {
            this.alphaNumericCode = newCode;
        } else {
            throw new IllegalArgumentException("Invalid Code");
        }
    }

    private static boolean codeMeetsMinimumRequirements(final String name) {
        return !StringPredicates.isNullOrEmpty(name);
    }

    private static boolean descriptionMeetsMinimumRequirements(final String description) {
        return !StringPredicates.isNullOrEmpty(description);
    }

    public String description() {
        return this.description.description();
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean toogleState() {

        this.active = !this.active;
        return isActive();
    }

    public void changeDescriptionTo(final String newDescription) {
        if (!descriptionMeetsMinimumRequirements(newDescription)) {
            throw new IllegalArgumentException();
        }
        this.description = new Description(newDescription);
    }


    @Override
    public boolean sameAs(final Object other) {
        final Category category = (Category) other;
        return this.equals(category) && description().equals(category.description())
                && isActive() == category.isActive();
    }


    @Override
    public String identity() {
        return this.alphaNumericCode;
    }

    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.alphaNumericCode);
    }

    @Override
    public String toString() {
        return "Category{" +
                "alphaNumericCode='" + alphaNumericCode + '\'' +
                ", description=" + description +
                ", active=" + active +
                '}';
    }
}
