package eapli.base.warehouse.domain;

import eapli.framework.domain.model.ValueObject;


import java.util.Objects;


public class AisleRow implements ValueObject{

    private Integer id;

    private Square start;

    private Square end;
    private Integer numShelves;

    protected AisleRow(){}

    public AisleRow(int id, Square start, Square end, int numShelves){
        this.id = id;
        this.start = start;
        this.end = end;
        if(numShelves > 0)
            this.numShelves = numShelves;
        else
            throw new IllegalArgumentException("A row needs at least one shelf");
    }

    public Square endsOn() {
        return end;
    }

    public Square startsOn() {
        return start;
    }

    public int shelfCount (){
        return numShelves;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AisleRow row = (AisleRow) o;
        return Objects.equals(id, row.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Row %d:%nStarts: %s%nEnds: %s%nNr. of Shelves: %d%n", this.id, this.start, this.end, this.numShelves);
    }
}
