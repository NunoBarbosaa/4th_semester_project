package eapli.base.customermanagement.dto;

public class AddressDTO {

    /**
     * String that contains the postalCode of the AddressDTO.
     */
    String postalCode;

    /**
     * String that contains the street of the AddressDTO.
     */
    String street;

    /**
     * Integer that contains the doorNumber of the AddressDTO.
     */
    int doorNumber;

    /**
     * String that contains the city of the AddressDTO.
     */
    String city;

    /**
     * String that contains the country of the AddressDTO.
     */
    String country;

    /**
     * String that contains the type of the AddressDTO.
     */
    String type;

    /**
     * Creates a constructor of AddressDTO and receives by parameter the postalCode, the street, the doorNumber,
     * the city, the country and the type.
     * @param postalCode
     * @param street
     * @param doorNumber
     * @param city
     * @param country
     * @param type
     */
    public AddressDTO(String postalCode, String street, int doorNumber, String city, String country, String type) {
        this.postalCode = postalCode;
        this.street = street;
        this.doorNumber = doorNumber;
        this.city = city;
        this.country = country;
        this.type = type;
    }

    /**
     * Returns a string of the postalCode of the AddressDTO
     * @return postalCode of the AddressDTO
     */
    public String postalCode() {
        return postalCode;
    }

    /**
     * Returns a string of the street of the AddressDTO
     * @return street of the AddressDTO
     */
    public String street() {
        return street;
    }

    /**
     * Returns an integer of the doorNumber of the AddressDTO
     * @return doorNumber of the AddressDTO
     */
    public int doorNumber() {
        return doorNumber;
    }

    /**
     * Returns a string of the city of the AddressDTO
     * @return city of the AddressDTO
     */
    public String city() {
        return city;
    }

    /**
     * Returns a string of the country of the AddressDTO
     * @return country of the AddressDTO
     */
    public String country() {
        return country;
    }

    /**
     * Returns a string of the type of the AddressDTO
     * @return type of the AddressDTO
     */
    public String type() {
        return type;
    }

    /**
     * Method toString that returns all characteristics of the AddressDTO
     * @return all characteristics of the AddressDTO
     */
    @Override
    public String toString() {
        return "AddressDTO{" +
                "postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", doorNumber=" + doorNumber +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
