package eapli.base.warehouse.domain;

import java.util.*;


public class Aisle {


    private Integer id;

    private Square start;

    private Square end;

    private Square depth;

    private Accessibility access;

    private List<AisleRow> rows;


    protected Aisle() {
    }

    public Aisle(int id, Square start, Square end, Square depth, Accessibility access) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.depth = depth;
        this.access = access;
    }

    public Square extendsTo() {
        return depth;
    }

    public Square endsOn() {
        return end;
    }

    public Square startsFrom() {
        return start;
    }

    public Square depth() { return depth; }

    public Accessibility isAccessibleVia() {
        return access;
    }

    public boolean addRow(AisleRow row) {
        if (rows == null)
            rows = new ArrayList<>();
        return rows.add(row);
    }

    public Integer getId() {
        return id;
    }

    public List<AisleRow> rows(){ return rows; }

    public Map<Integer, Integer> getRowsIdAndRespectiveNumberOfShelves() {
        Map<Integer, Integer> mp = new HashMap<>();
        Iterator<AisleRow> it = rows.iterator();
        while (it.hasNext()) {
            AisleRow a = it.next();
            mp.put(a.getId(),a.shelfCount());
        }
        return mp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aisle aisle = (Aisle) o;
        return Objects.equals(id, aisle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
