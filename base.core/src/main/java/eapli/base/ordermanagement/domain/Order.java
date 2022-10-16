package eapli.base.ordermanagement.domain;

import eapli.base.customermanagement.domain.Address;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PRODUCT_ORDER")
public class Order implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Long that contains the id of the Order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDORDER")
    private long id;

    /**
     * List of status of the Order.
     */
    @ElementCollection
    @CollectionTable(name="ORDER_STATUS", joinColumns=@JoinColumn(name="IDORDER"))
    private List<Status> status;

    /**
     * Customer associated to the Order.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "IDCUSTOMER")
    private Customer customer;

    /**
     * Email of the Sales Clerk that creates the Order.
     */
    @Embedded
    private EmailAddress salesClerkEmail;

    /**
     * DateTime that contains the date that the Order was created.
     */
    @Embedded
    private DateTime dateTime;

    /**
     * List of Order Items.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDORDER")
    private List<OrderItem> orderItem;

    /**
     * Total Price of the Order.
     */
    @Embedded
    private TotalPrice totalPrice;

    /**
     * Address of billing associated to the Order.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street",
                    column=@Column(name="streetBilling")),
            @AttributeOverride(name="postalCode",
                    column=@Column(name="postalCodeBilling")),
            @AttributeOverride(name="doorNumber",
                    column=@Column(name="doorNumberBilling")),
            @AttributeOverride(name="city",
                    column=@Column(name="cityBilling")),
            @AttributeOverride(name="country",
                    column=@Column(name="countryBilling")),
            @AttributeOverride(name="type",
                    column=@Column(name="typeBilling"))
    })
    private Address billingAddress;

    /**
     * Address of delivering associated to the order.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street",
                    column=@Column(name="streetDelivering")),
            @AttributeOverride(name="postalCode",
                    column=@Column(name="postalCodeDelivering")),
            @AttributeOverride(name="doorNumber",
                    column=@Column(name="doorNumberDelivering")),
            @AttributeOverride(name="city",
                    column=@Column(name="cityDelivering")),
            @AttributeOverride(name="country",
                    column=@Column(name="countryDelivering")),
            @AttributeOverride(name="type",
                    column=@Column(name="typeDelivering"))
    })
    private Address deliveringAddress;

    /**
     * Shipment Method of the Order.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "method",
                    column = @Column(name = "shipmentMethod")),
    })
    private Shipment shipment;

    /**
     * Payment Method of the Order.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "method",
                    column = @Column(name = "paymentMethod")),
    })
    private Payment payment;

    /**
     * Creates a new constructor of Order receiving by parameter the status, the customer, the salesClerkEmail,
     * the date, the orderItemList, the totalPrice, the billingAddress, the deliveringAddress, the shipment and the payment,
     * @param status
     * @param customer
     * @param salesClerkEmail
     * @param date
     * @param orderItemList
     * @param totalPrice
     * @param billingAddress
     * @param deliveringAddress
     * @param shipment
     * @param payment
     */
    public Order(final Status status, final Customer customer, final EmailAddress salesClerkEmail, final DateTime date,
                 final List<OrderItem> orderItemList, final TotalPrice totalPrice, final Address billingAddress, final Address deliveringAddress,
                 final Shipment shipment, final Payment payment) {
        this.customer = customer;
        this.salesClerkEmail = salesClerkEmail;
        this.dateTime = date;
        this.orderItem = orderItemList;
        this.totalPrice = totalPrice;
        this.billingAddress = billingAddress;
        this.deliveringAddress = deliveringAddress;
        this.shipment = shipment;
        this.payment = payment;
        addStatusToOrder(status);
    }

    public Order() {

    }

    public DateTime getDateTime(){
        return dateTime;
    }
    /**
     * Add a new status to the order.
     * @param status
     */
    public void addStatusToOrder(final Status status){
        if(this.status == null) this.status = new ArrayList<>();
        List<Status> statusList = this.status;
        statusList.add(status);
        this.status = statusList;
    }

    /**
     * Change the status of the order receiving by parameter the date that was the change and the new status.
     * @param date
     * @param orderStatus
     */
    public void changeStatus(final Date date, final OrderStatus orderStatus) {
        addStatusToOrder(new Status(orderStatus, date));
    }

    /**
     * Calculate the total weight of the Order.
     * @return total weight.
     */
    public double totalWeight(){
        double weight = 0;
        for (OrderItem orderItem : this.orderItem){
            weight += orderItem.quantity() * orderItem.product().getWeight();
        }
        return weight;
    }

    /**
     * Calculate the total volume of the Order.
     * @return total volume.
     */
    public double totalVolume(){
        double volume = 0;
        for (OrderItem orderItem : this.orderItem){
            volume += orderItem.quantity() * orderItem.product().getVolume();
        }
        return volume;
    }

    /**
     * Returns all the status of the Order.
     * @return list of status.
     */
    public List<Status> statusList() { return status; }

    public OrderStatus getOrderStatus(){
       Date recent = new Date();
       Status st = null;
       int i = 0;
        for (Status s : this.status){
            if(i==0){
                recent = s.date();
                st = s;
            }
            else{
                if(s.date().compareTo(recent)>=0){
                    recent = s.date();
                    st = s;
                }
            }
            i++;
        }
        return st.orderStatus();
    }

    /**
     * Returns the customer associated to the Order.
     * @return customer.
     */
    public Customer customer() {
        return customer;
    }

    /**
     * Returns the total price of the Order.
     * @return total price.
     */
    public TotalPrice totalPrice() { return totalPrice; }

    /**
     * Method toString that returns all characteristics of Order.
     * @return all characteristics of Order.
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", customer=" + customer +
                ", salesClerkEmail=" + salesClerkEmail +
                ", dateTime=" + dateTime +
                ", orderItem=" + orderItem +
                ", totalPrice=" + totalPrice +
                ", billingAddress=" + billingAddress +
                ", deliveringAddress=" + deliveringAddress +
                ", shipment=" + shipment +
                ", payment=" + payment +
                '}';
    }

    @Override
    public boolean sameAs(final Object other) {
        final Order order = (Order) other;
        return this.equals(order) && status.equals(order.status) && customer.equals(order.customer)
                && salesClerkEmail.equals(order.salesClerkEmail) && dateTime.equals(order.dateTime) && orderItem.equals(order.orderItem)
                && totalPrice.equals(order.totalPrice) && billingAddress.equals(order.billingAddress) && deliveringAddress.equals(order.deliveringAddress)
                && shipment.equals(order.shipment) && payment.equals(order.payment);
    }

    /**
     * Returns the identity of the Order.
     * @return the identity of the Order.
     */
    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean hasIdentity(final Long id) {
        return AggregateRoot.super.hasIdentity(id);
    }

}
