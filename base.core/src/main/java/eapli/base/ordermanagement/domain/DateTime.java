package eapli.base.ordermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class DateTime implements ValueObject {

    /**
     * String that contains the time.
     */
    private String time;

    /**
     * String that contains the date.
     */
    private String date;

    /**
     * Creates a constructor DateTime receiving by parameter the time and the date.
     * @param time
     * @param date
     */
    public DateTime(String time, String date) {
        this.time = time;
        this.date = date;
    }

    public DateTime() {

    }

    /**
     * Method toString that returns all characteristics of TotalPrice.
     * @return returns all characteristics of TotalPrice.
     */
    @Override
    public String toString() {
        return "DateTime{" +
                "time=" + time +
                ", date=" + date +
                '}';
    }
}
