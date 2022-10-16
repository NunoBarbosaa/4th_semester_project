package eapli.base.warehouse.domain;

import eapli.base.utils.Description;
import eapli.base.utils.Measurement;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.util.*;

public class Warehouse {
    private Long id;

    private Description description;

    private Measurement width;

    private Measurement length;

    private Measurement squareDimension;

    private Set<Aisle> aisles;

    private Set<AgvDock> agvDocks;

    protected Warehouse(){}

    public Warehouse (Description description, Measurement width, Measurement length, Measurement squareDimension){
        if(squareDimension.toMeters() > width.toMeters() || squareDimension.toMeters() > length.toMeters())
            throw new IllegalArgumentException("Square unit dimension cannot be bigger than the warehouse itself");
        this.description = description;
        this.width = width;
        this.length = length;
        this.squareDimension = squareDimension;
        this.agvDocks = new HashSet<>();
        this.aisles = new HashSet<>();
    }

    public boolean addAisle(Aisle aisle){
        return aisles.add(aisle);
    }

    public boolean addDock(AgvDock dock){
        return agvDocks.add(dock);
    }

    public Measurement length() {
        return length;
    }

    public Measurement width() {
        return width;
    }

    public Description description() {
        return description;
    }

    public Measurement squareDimension() {
        return squareDimension;
    }

    public List<AgvDock> docks(){return new ArrayList<>(agvDocks);}

    public Set<Aisle> getAisles() {
        return aisles;
    }

    public List<AgvDock> freeDocks(){
        List<AgvDock> freeDocks = new ArrayList<>();
        for(AgvDock dock : docks())
            if(dock.isFree()) freeDocks.add(dock);
        return freeDocks;
    }

    public boolean assignDock(String dockId){
        for (AgvDock dock: docks())
            if(dock.identity().equals(dockId)) return dock.assignAGV();
        return false;
    }

    @Override
    public String toString() {
        return String.format("Warehouse -> %s%nWidth: %s Length: %s%nNr. of Aisles: %d, Nr. of AGV docks: %d%n",
                description, width, length, aisles.size(), agvDocks.size());
    }

}
