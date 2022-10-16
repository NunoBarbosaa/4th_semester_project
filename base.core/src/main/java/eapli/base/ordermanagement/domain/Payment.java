package eapli.base.ordermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Payment implements ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * String that contains the method of payment.
     */
    private String method;

    /**
     * String that contains the message of confirmation.
     */
    private String confirmation;

    /**
     * Creates a constructor of payment receiving by parameter the method and the message of confirmation.
     * @param method
     * @param confirmation
     */
    public Payment(String method, String confirmation) {
        this.method = method;
        this.confirmation = confirmation;
    }

    public Payment() {

    }

    public String method() {
        return method;
    }

    /**
     * Method toString that return all characteristics of the payment.
     * @return all characteristics of the payment.
     */
    @Override
    public String toString() {
        return "Payment{" +
                "method='" + method + '\'' +
                ", confirmation='" + confirmation + '\'' +
                '}';
    }
}
