package eapli.base.customermanagement.domain;

import javax.persistence.Embeddable;

@Embeddable
public class PhoneNumber {

    private static final long serialVersionUID = 1L;

    private static final int MAX_LENGTH = 13;

    /**
     * String that contains the diallingCode of the PhoneNumber.
     */
    private String diallingCode;

    /**
     * Long that contains the number of the PhoneNumber.
     */
    private long number;

    /**
     * Creates a constructor of PhoneNumber receiving by parameter the diallingCode and the number.
     * @param diallingCode
     * @param number
     */
    public PhoneNumber(String diallingCode, long number) {
        String concatenate = diallingCode + number;
        if(!concatenate.matches("[+][0-9]+")){
            throw new IllegalArgumentException(
                    "Invalid format for Phone Number. (eg. +351911221345).");
        }
        if(concatenate.length() != MAX_LENGTH){
            throw new IllegalArgumentException(
                    "Number should have " + (MAX_LENGTH - 1) + " numbers.");
        }
        this.diallingCode = diallingCode;
        this.number = number;
    }

    public PhoneNumber() {

    }

    /**
     * Method toString that returns all characteristics of the PhoneNumber
     * @return all characteristics of the PhoneNumber
     */
    @Override
    public String toString() {
        return "PhoneNumber{" +
                "diallingCode='" + diallingCode + '\'' +
                ", number=" + number +
                '}';
    }
}
