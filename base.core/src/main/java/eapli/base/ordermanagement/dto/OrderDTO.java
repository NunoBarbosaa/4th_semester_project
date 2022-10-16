package eapli.base.ordermanagement.dto;

import eapli.framework.representations.dto.DTO;

import java.lang.annotation.Annotation;

@DTO
public class OrderDTO {

    public long id;

    public String date;

    public String customerName;

    public double total;

    public OrderDTO(final long id, final String date, final String customerName, final double total){
        this.id = id;
        this.date = date;
        this.customerName = customerName;
        this.total = total;
    }
}
