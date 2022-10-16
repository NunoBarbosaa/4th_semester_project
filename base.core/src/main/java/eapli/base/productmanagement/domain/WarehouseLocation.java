package eapli.base.productmanagement.domain;

import eapli.base.warehouse.domain.Aisle;
import eapli.base.warehouse.domain.AisleRow;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class WarehouseLocation implements ValueObject {

    private int aisle;
    private int aisleRow;
    private int shelf;

    protected WarehouseLocation(){}

    public WarehouseLocation(int aisle, int aisleRow, int shelf){
        this.aisle = aisle;
        this.aisleRow = aisleRow;
        this.shelf = shelf;
    }

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public int getAisleRow() {
        return aisleRow;
    }

    public void setAisleRow(int aisleRow) {
        this.aisleRow = aisleRow;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }
}
