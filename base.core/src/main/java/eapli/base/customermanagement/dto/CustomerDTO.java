package eapli.base.customermanagement.dto;

import eapli.base.customermanagement.dto.AddressDTO;
import eapli.framework.general.domain.model.EmailAddress;

import java.util.List;

public class CustomerDTO {

    /**
     * String that contains the firstNames of the CustomerDTO.
     */
    String firstNames;

    /**
     * String that contains the lastNames of the CustomerDTO.
     */
    String lastNames;

    /**
     * String that vat the firstName of the CustomerDTO.
     */
    String vat;

    /**
     * String that contains the email of the CustomerDTO.
     */
    String email;

    /**
     * String that contains the diallingCode of the CustomerDTO.
     */
    String diallingCode;

    /**
     * Long that contains the phoneNumber of the CustomerDTO.
     */
    long phoneNumber;

    /**
     * List that contains the list of addressesDTO of the CustomerDTO.
     */
    List<AddressDTO> addressDTOList;

    /**
     * Integer that contains the day of birthDate of the CustomerDTO.
     */
    int dayBirthDate;

    /**
     * Integer that contains the month of birthDate of the CustomerDTO.
     */
    int monthBirthDate;

    /**
     * Integer that contains the year of birthDate of the CustomerDTO.
     */
    int yearBirthDate;

    /**
     * String that contains the gender of the CustomerDTO.
     */
    String gender;

    /**
     * Creates a constructor of CustomerDTO that receives by parameter the firstNames, the lastNames, the vat, the email, the diallingCode,
     * the phoneNumber, the addressesDTOList, the dayBirthDate, the monthBirthDate, the yearBirthDate and the gender.
     * @param firstNames
     * @param lastNames
     * @param vat
     * @param email
     * @param diallingCode
     * @param phoneNumber
     * @param addressDTOList
     * @param dayBirthDate
     * @param monthBirthDate
     * @param yearBirthDate
     * @param gender
     */
    public CustomerDTO(String firstNames, String lastNames, String vat, String email, String diallingCode, long phoneNumber,
                       List<AddressDTO> addressDTOList, int dayBirthDate, int monthBirthDate, int yearBirthDate, String gender) {
        this.firstNames = firstNames;
        this.lastNames = lastNames;
        this.vat = vat;
        this.email = email;
        this.diallingCode = diallingCode;
        this.phoneNumber = phoneNumber;
        this.addressDTOList = addressDTOList;
        this.dayBirthDate = dayBirthDate;
        this.monthBirthDate = monthBirthDate;
        this.yearBirthDate = yearBirthDate;
        this.gender = gender;
    }

    public CustomerDTO(String firstNames, String lastNames, String email) {
        this.firstNames = firstNames;
        this.lastNames = lastNames;
        this.email = email;
    }

    /**
     * Returns a string of the firstNames of the CustomerDTO.
     * @return the firstNames of the CustomerDTO.
     */
    public String firstNames() {
        return firstNames;
    }

    /**
     * Returns a string of the lastNames of the CustomerDTO.
     * @return the lastNames of the CustomerDTO.
     */
    public String lastNames() {
        return lastNames;
    }

    /**
     * Returns a string of the vat of the CustomerDTO.
     * @return the vat of the CustomerDTO.
     */
    public String vat() {
        return vat;
    }

    /**
     * Returns a string of the email of the CustomerDTO.
     * @return the email of the CustomerDTO.
     */
    public String email() {
        return email;
    }

    /**
     * Returns a string of the diallingCode of the CustomerDTO.
     * @return the diallingCode of the CustomerDTO.
     */
    public String diallingCode() {
        return diallingCode;
    }

    /**
     * Returns a long of the phoneNumber of the CustomerDTO.
     * @return the phoneNumber of the CustomerDTO.
     */
    public long phoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns a list of addressesDTO of the CustomerDTO.
     * @return the addressesDTO of the CustomerDTO.
     */
    public List<AddressDTO> addressDTOList() { return addressDTOList; }

    /**
     * Returns an integer of the day of birthDate of the CustomerDTO.
     * @return the day of birthDate of the CustomerDTO.
     */
    public int dayBirthDate() {
        return dayBirthDate;
    }

    /**
     * Returns an integer of the month of birthDate of the CustomerDTO.
     * @return the month of birthDate of the CustomerDTO.
     */
    public int monthBirthDate() {
        return monthBirthDate;
    }

    /**
     * Returns an integer of the year of birthDate of the CustomerDTO.
     * @return the year of birthDate of the CustomerDTO.
     */
    public int yearBirthDate() {
        return yearBirthDate;
    }

    /**
     * Returns a string of the gender of the CustomerDTO.
     * @return the gender of the CustomerDTO.
     */
    public String gender() {
        return gender;
    }

    /**
     * Method toString that returns all characteristics of the CustomerDTO
     * @return all characteristics of the CustomerDTO
     */
    @Override
    public String toString() {
        return "CustomerDTO{" +
                "firstNames='" + firstNames + '\'' +
                ", lastNames='" + lastNames + '\'' +
                ", vat='" + vat + '\'' +
                ", email='" + email + '\'' +
                ", diallingCode='" + diallingCode + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", addressDTOList=" + addressDTOList +
                ", dayBirthDate=" + dayBirthDate +
                ", monthBirthDate=" + monthBirthDate +
                ", yearBirthDate=" + yearBirthDate +
                ", gender='" + gender + '\'' +
                '}';
    }
}
