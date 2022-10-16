package eapli.base.customermanagement.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class BirthDate implements ValueObject {

    private static final long serialVersionUID = 1L;

    public static final int MAX_YEARS = 130;

    /**
     * Integer that contains the day of the birthDate.
     */
    private int day;

    /**
     * Integer that contains the month of the birthDate.
     */
    private int month;

    /**
     * Integer that contains the year of the birthDate.
     */
    private int year;

    /**
     * Creates a constructor BirthDate receiving by parameter the day, the day month and the year.
     * @param day
     * @param month
     * @param year
     */
    public BirthDate(final int day, final int month, final int year) {
        String concatenate;

        // concatenate zeros to day or month if this are less than 10

        if(month < 10 && day < 10) concatenate = year + "-0" + month + "-0" + day;
        else {
            if (day < 10) concatenate = year + "-" + month + "-0" + day;
            else if (month < 10) concatenate = year + "-0" + month + "-" + day;
            else concatenate = year + "-" + month + "-" + day;
        }

        // if customer has birthDate

        if(day != 0 && month != 0 && year != 0) {
            try {
                YearMonth.parse(concatenate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atDay(1);
            } catch (Exception e1) {
                throw new IllegalArgumentException(
                        "Invalid date.");
            }
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    protected BirthDate() {
        // for ORM
    }

    /**
     * Method toString that returns all characteristics of the BirthDate
     * @return all characteristics of the BirthDate
     */
    @Override
    public String toString() {
        return "BirthDate{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}

