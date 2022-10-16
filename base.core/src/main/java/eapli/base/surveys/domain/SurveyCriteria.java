package eapli.base.surveys.domain;

import eapli.base.customermanagement.domain.Gender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class SurveyCriteria {

    private final String PRODUCT_BOUGHT = "Bought_Product";
    private final String BOUGHT_FROM_BRAND = "Brand";
    private final String AGE_LESS_THAN = "Age_Less";
    private final String AGE_MORE_THAN = "Age_More";
    private final String AGE_INTERVAL = "Age_Interval";
    private final String IS_GENDER = "Gender";
    private List<String> productID;
    private String brand;
    private String start;
    private String end;

    private Gender gender;

    private int ageMin;
    private int ageMax;

    private String type;

    public SurveyCriteria(List<String> pIDs, String dateStart, String dateEnd) throws ParseException {
        if(pIDs == null || pIDs.isEmpty())
            throw new IllegalArgumentException("Product IDs cannot be null or empty");
        this.productID = new ArrayList<>(pIDs);
        if(new Date(new SimpleDateFormat("dd-MM-yyyy").parse(dateStart).getTime()).after(new Date(new SimpleDateFormat("dd-MM-yyyy").parse(dateEnd).getTime())))
            throw new IllegalArgumentException("Invalid Criteria date interval");
        this.start = parseDate(dateStart);
        this.end = parseDate(dateEnd);
        this.type = PRODUCT_BOUGHT;
    }

    public SurveyCriteria(String brand, String dateStart, String dateEnd) throws ParseException {
        if(brand == null || brand.isEmpty() || brand.isBlank())
            throw new IllegalArgumentException("Brand name cannot be null or empty");
        this.brand = brand;
        if(new Date(new SimpleDateFormat("dd-MM-yyyy").parse(dateStart).getTime()).after(new Date(new SimpleDateFormat("dd-MM-yyyy").parse(dateEnd).getTime())))
            throw new IllegalArgumentException("Invalid Criteria date interval");
        this.start = parseDate(dateStart);
        this.end = parseDate(dateEnd);
        this.type = BOUGHT_FROM_BRAND;
    }

    public SurveyCriteria(String gender){
        this.gender = interpretGender(gender);
        this.type = IS_GENDER;
    }

    public SurveyCriteria(int age, String type){
        if(age < 0)
            throw new IllegalArgumentException("Age cannot be negative");
        if(type == null || type.isEmpty() || type.isBlank())
            throw new IllegalArgumentException("Age type cannot be null or empty");
        if(type.equalsIgnoreCase("Higher")) {
            this.ageMin = age;
            this.type = AGE_MORE_THAN;
        }else if(type.equalsIgnoreCase("Less")) {
            this.ageMax = age;
            this.type = AGE_LESS_THAN;
        }else{
            throw new IllegalArgumentException("Unrecognized type of criteria");
        }
    }

    public SurveyCriteria(int ageMin, int ageMax){
        if(ageMin < 0 || ageMax < 0)
            throw new IllegalArgumentException("Age cannot be negative");
        if(ageMin > ageMax)
            throw new IllegalArgumentException("Minimum age cannot be higher than the maximum age");
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.type = AGE_INTERVAL;
    }

    private Gender interpretGender(String gender) {
        if(gender == null || gender.isEmpty() || gender.isBlank())
            throw new IllegalArgumentException("Gender cannot be null or empty");
        if (gender.equalsIgnoreCase("Male"))
            return Gender.MALE;
        if (gender.equalsIgnoreCase("Female"))
            return Gender.FEMALE;
        if (gender.equalsIgnoreCase("Undefined"))
            return Gender.UNDEFINED;
        throw new IllegalArgumentException("Gender not found.");
    }

    private String parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty() || dateString.isBlank())
            throw new IllegalArgumentException("Date should not be null or empty");

        String []dateParsed = dateString.trim().split("-");
        return dateParsed[2] + "-" + dateParsed[1] + "-" + dateParsed[0];
    }

    public String query(){
        StringBuilder query = new StringBuilder();
        if(this.type.equals(PRODUCT_BOUGHT)){
            query.append("select distinct c.*\n" +
                    "from CUSTOMER c\n" +
                    "         join PRODUCT_ORDER o on c.IDCUSTOMER = o.IDCUSTOMER\n" +
                    "         join ORDER_ITEM oi ON oi.IDORDER = o.IDORDER\n" +
                    "         join PRODUCT P2 on oi.IDPRODUCT = P2.PK\n" +
                    "         join ORDER_STATUS OS on o.IDORDER = OS.IDORDER\n" +
                    "where ");
            for(String id : productID)
                query.append("P2.IDPRODUCT = '").append(id.trim()).append("' OR ");
            query.delete(query.length()-4, query.length()-1);
            query.append("AND (os.ORDERSTATUS = 'CREATED' AND os.DATE BETWEEN DATE '").append(start).append("' AND '").append(end).append("')");
            return query.toString();
        }

        if(this.type.equals(BOUGHT_FROM_BRAND)){
            return "select distinct c.*\n" +
                    "from Customer c\n" +
                    "       join PRODUCT_ORDER o on o.IDCUSTOMER = c.IDCUSTOMER\n" +
                    "         join ORDER_ITEM oi ON oi.IDORDER = o.IDORDER\n" +
                    "         join PRODUCT P2 on oi.IDPRODUCT = P2.PK\n" +
                    "         join ORDER_STATUS OS on o.IDORDER = OS.IDORDER\n" +
                            "where P2.NAME = '" + brand + "' AND (os.ORDERSTATUS = 'CREATED' AND os.DATE BETWEEN DATE '" + start + "' AND '" + end + "')";
        }

        if(this.type.equals(AGE_INTERVAL)){
            query.append("select distinct *\n" +
                    "from CUSTOMER C\n" +
                    "where (year(CURRENT_DATE) - C.YEAR) < ").append(ageMax).append("AND (year(CURRENT_DATE) - C.YEAR) > ").
                    append(ageMin);
            return query.toString();
        }

        if(this.type.equals(AGE_LESS_THAN)){
            query.append("select distinct *\n" +
                    "from CUSTOMER C\n" +
                    "where (year(CURRENT_DATE) - C.YEAR) < ").append(ageMax);
            return query.toString();
        }

        if(this.type.equals(AGE_MORE_THAN)){
            query.append("select distinct *\n" +
                    "from CUSTOMER C\n" +
                    "where (year(CURRENT_DATE) - C.YEAR) > ").append(ageMin);
            return query.toString();
        }


        if(this.type.equals(IS_GENDER)){
            query.append("select distinct *\n" +
                    "from CUSTOMER C\n" +
                    "where C.GENDER = '").append(gender).append("'");
            return query.toString();
        }
        throw new IllegalArgumentException("Unrecognized Criteria");
    }

    public CombinationType convertCombination(String value) {
        if(value.trim().equalsIgnoreCase("[AND]"))
            return CombinationType.INTERSECT;
        if(value.trim().equalsIgnoreCase("[OR]"))
            return CombinationType.UNION;
        throw new IllegalArgumentException("Invalid logical operator");
    }
}
