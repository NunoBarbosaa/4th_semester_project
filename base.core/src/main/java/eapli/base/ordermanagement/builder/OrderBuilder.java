package eapli.base.ordermanagement.builder;

import eapli.base.customermanagement.domain.Address;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.ordermanagement.domain.*;
import eapli.base.ordermanagement.dto.OrderItemDTO;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.general.domain.model.EmailAddress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderBuilder implements DomainFactory<Order> {

    /**
     * Order to be builded.
     */
    private Order order;

    /**
     * Status of the Order.
     */
    private Status status;

    /**
     * Customer associated to the Order.
     */
    private Customer customer;

    /**
     * Email of the Sales Clerk that creates the Order.
     */
    private EmailAddress salesClerkEmail;

    /**
     * Date that the Order was created.
     */
    private DateTime date;

    /**
     * List of Order Items.
     */
    private List<OrderItem> orderItemList;

    /**
     * Total Price of the Order.
     */
    private TotalPrice totalPrice;

    /**
     * Billing Address of the Order.
     */
    private Address billingAddress;

    /**
     * Delivering Address of the Order.
     */
    private Address deliveringAddress;

    /**
     * Shipment Method of the Order.
     */
    private Shipment shipment;

    /**
     * Payment Method of the Order.
     */
    private Payment payment;

    /**
     * Creates a new Status receiving by parameter the OrderStatus and the Date that was the change.
     * @param orderStatus
     * @param date
     */
    public void status(final OrderStatus orderStatus, final Date date){
        this.status = new Status(orderStatus, date);
    }

    /**
     * Receiving the customer associated to the order.
     * @param customer
     */
    public void customer(final Customer customer) {
        this.customer = customer;
    }

    /**
     * Receiving the Sales Clerk email.
     */
    public void salesClerkEmail(final EmailAddress salesClerkEmail) {
        this.salesClerkEmail = salesClerkEmail;
    }

    /**
     * Receives a Date and separate into time and date to create a new DateTime object.
     */
    public OrderBuilder date(final Date date) {
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm");
        String time = simpleTimeFormat.format(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dt = simpleDateFormat.format(date);
        this.date = new DateTime(time, dt);
        return this;
    }

    /**
     * Receiving the List of Order Items.
     * @param orderItemList
     */
    public void orderItems(final List<OrderItem> orderItemList){
        this.orderItemList = orderItemList;
    }

    /**
     * Receiving the price with and without taxes to creates a new Object TotalPrice.
     * @param priceWithTaxes
     * @param priceWithoutTaxes
     */
    public void totalPrice(final double priceWithTaxes, final double priceWithoutTaxes) {
        this.totalPrice = new TotalPrice(priceWithTaxes, priceWithoutTaxes);
    }

    /**
     * Receiving the Billing Address DTO to create a new Address.
     * @param billingAddress
     */
    public void billingAddress(final AddressDTO billingAddress) {
        this.billingAddress = new Address(billingAddress.street(), billingAddress.postalCode(), billingAddress.doorNumber(),
                billingAddress.city(), billingAddress.country(), billingAddress.type());
    }

    /**
     * Receiving the Delivering Address DTO to create a new Address.
     * @param delivering
     */
    public void deliveringAddress(final AddressDTO delivering) {
        this.deliveringAddress = new Address(delivering.street(), delivering.postalCode(), delivering.doorNumber(),
                delivering.city(), delivering.country(), delivering.type());
    }

    /**
     * Receives the option to create a new Object Shipment.
     */
    public void shipment(final int shipment) {
        if(shipment == 1){
            this.shipment = new Shipment(ShipmentMethod.STANDARD, 10);
        }
        else if(shipment == 2){
            this.shipment = new Shipment(ShipmentMethod.BLUE, 20);
        }
        else{
            this.shipment = new Shipment(ShipmentMethod.GREEN, 30);
        }
    }

    /**
     * Receives the option to create a new Object Payment.
     */
    public OrderBuilder payment(final int payment) {
        if(payment == 1) {
            this.payment = new Payment("PayPal", "Success.");
        }
        else{
            this.payment = new Payment("Apple Play", "Success.");
        }
        return this;
    }

    private Order buildOrThrow() {
        if(order != null){
            return order;
        }
        else if(status != null && customer != null && salesClerkEmail != null && date != null
            && orderItemList != null && totalPrice != null && billingAddress != null &&
                deliveringAddress != null && shipment != null && payment != null){
            order = new Order(status, customer, salesClerkEmail, date, orderItemList, totalPrice,
                    billingAddress, deliveringAddress, shipment, payment);
            return order;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public Order build() {
        return buildOrThrow();
    }
}
