package eapli.base.customermanagement.domain;

import eapli.base.categorymanagement.domain.Category;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDCUSTOMER")
    private long id;

    private String firstNames;

    private String lastNames;

    @Column(unique = true)
    @Embedded
    private VAT vat;

    @Column(unique = true)
    @Embedded
    private EmailAddress emailAddress;

    @Column(unique = true)
    @Embedded
    private PhoneNumber phoneNumber;

    @ElementCollection
    @CollectionTable(name="address", joinColumns=@JoinColumn(name="IDCUSTOMER"))
    private List<Address> addressList;

    @Embedded
    private BirthDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "IDSYSTEMUSER")
    private SystemUser systemUser;

    public Customer(final String firstNames, final String lastNames, final VAT vat, final EmailAddress emailAddress,
                    final PhoneNumber phoneNumber, final List<Address> addressList, final BirthDate birthDate, final Gender gender, final SystemUser systemUser) {
        this.firstNames = firstNames;
        this.lastNames = lastNames;
        this.vat = vat;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.addressList = addressList;
        this.birthDate = birthDate;
        this.gender = gender;
        this.systemUser = systemUser;
    }

    public Customer() {

    }

    /**
     * Returns the firstNames of the Customer.
     * @return firstNames.
     */
    public String firstNames() {
        return firstNames;
    }

    /**
     * Returns the lastNames of the Customer.
     * @return lastNames.
     */
    public String lastNames() {
        return lastNames;
    }

    /**
     * Returns the email of the Customer.
     * @return email.
     */
    public EmailAddress emailAddress() {
        return emailAddress;
    }

    /**
     * Returns the list of addresses of the Customer.
     * @return list of addresses.
     */
    public List<Address> addressList() {
        return addressList;
    }

    public String systemUser() {
        return systemUser.identity().toString();
    }

    /**
     * Method toString that returns all characteristics of the Customer
     * @return all characteristics of the Customer
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstNames='" + firstNames + '\'' +
                ", lastNames='" + lastNames + '\'' +
                ", vat=" + vat +
                ", emailAddress=" + emailAddress +
                ", phoneNumber=" + phoneNumber +
                ", addressList=" + addressList +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                '}';
    }

    @Override
    public boolean sameAs(final Object other) {
        final Customer customer = (Customer) other;
        return this.equals(customer) && firstNames.equals(customer.firstNames) && lastNames.equals(customer.lastNames)
                && vat == customer.vat && emailAddress.equals(customer.emailAddress) && phoneNumber == customer.phoneNumber
                && addressList.equals(customer.addressList) && birthDate.equals(customer.birthDate) && gender.equals(customer.gender) && systemUser.equals(customer.systemUser);
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean hasIdentity(final Long id) {
        return AggregateRoot.super.hasIdentity(id);
    }

}
