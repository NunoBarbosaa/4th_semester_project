package eapli.base.ordermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class Status implements ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * Enumerated OrderStatus that contains the status of the order.
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /**
     * Date that contains the change of the status of the order.
     */
    private Date date;

    /**
     * Creates a constructor Status receiving by parameter the OrderStatus and the Date.
     * @param orderStatus
     * @param date
     */
    public Status(OrderStatus orderStatus, Date date) {
        this.orderStatus = orderStatus;
        this.date = date;
    }

    public Status() {

    }

    /**
     * Returns the date.
     * @return date.
     */
    public Date date() {
        return date;
    }

    public OrderStatus orderStatus() {
        return orderStatus;
    }

    /**
     * Method toString that returns all characteristics of Status.
     * @return all characteristics of Status.
     */



    @Override
    public String toString() {
        return "Status{" +
                "orderStatus=" + orderStatus +
                ", date=" + date +
                '}';
    }
}
