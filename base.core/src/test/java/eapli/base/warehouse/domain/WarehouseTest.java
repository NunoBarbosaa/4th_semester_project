package eapli.base.warehouse.domain;

import eapli.base.utils.Description;
import eapli.base.utils.Measurement;
import eapli.base.utils.Unit;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkSquareUnitCannotBeBiggerThanWarehouse(){
        new Warehouse(new Description("Warehouse test"), new Measurement(100, Unit.METER),
                new Measurement(100, Unit.METER), new Measurement(200, Unit.METER));
    }

}