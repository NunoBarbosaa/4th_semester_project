/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.customermanagement.domain;

import javax.persistence.*;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

@Embeddable
public class Address implements ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * String that contains the street of the Address.
     */
    private String street;

    /**
     * String that contains the postalCode of the Address.
     */
    private String postalCode;

    /**
     * Integer that contains the doorNumber of the Address.
     */
    private int doorNumber;

    /**
     * String that contains the city of the Address.
     */
    private String city;

    /**
     * String that contains the country of the Address.
     */
    private String country;

    /**
     * TypeAddress that contains the type of the Address.
     */
    @Enumerated(EnumType.STRING)
    private TypeAddress type;

    /**
     * Creates a constructor Address receiving by parameter the street, the postalCode, the doorNumber, the city, the country and the type.
     * @param street
     * @param postalCode
     * @param doorNumber
     * @param city
     * @param country
     * @param type
     */
    public Address(final String street, final String postalCode, final int doorNumber, final String city, final String country, final String type) {
        modifyPostalCode(postalCode);
        this.street = street;
        modifyDoorNumber(doorNumber);
        this.city = city;
        this.country = country;
        modifyType(type);
    }

    public Address() {

    }

    public void modifyPostalCode(String postalCode) {
        if(!postalCode.matches("[0-9]{4}[-][0-9]{3}")){
            throw new IllegalArgumentException(
                    "Postal Code invalid.");
        }
        this.postalCode = postalCode;
    }

    /**
     * Verify if the doorNumber is valid.
     * @param doorNumber
     */
    private void modifyDoorNumber(int doorNumber){
        if(!String.valueOf(doorNumber).matches("[1-9][0-9]*")){
            throw new IllegalArgumentException(
                    "Door Number invalid.");
        }
        this.doorNumber = doorNumber;
    }

    /**
     * Verify what is the type of the address.
     * @param type
     */
    private void modifyType(String type){
        if(type.contains("Billing")) this.type = TypeAddress.BILLING;
        else this.type = TypeAddress.DELIVERING;
    }

    public String street() {
        return street;
    }

    public String postalCode() {
        return postalCode;
    }

    public int doorNumber() {
        return doorNumber;
    }

    public String city() {
        return city;
    }

    public String country() {
        return country;
    }

    public TypeAddress type() {
        return type;
    }

    /**
     * Method toString that returns all characteristics of the Address
     * @return all characteristics of the Address
     */
    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", postalCode=" + postalCode +
                ", doorNumber=" + doorNumber +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", type=" + type +
                '}';
    }
}
