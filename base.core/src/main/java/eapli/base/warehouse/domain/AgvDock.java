package eapli.base.warehouse.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.*;
import java.util.Objects;

public class AgvDock implements AggregateRoot<String> {

    private String id;

    private Square start;

    private Square end;

    private Square depth;

    private Accessibility access;

    private Boolean isAssigned;

    protected AgvDock(){}

    public AgvDock(String id, Square start, Square end, Square depth, Accessibility access)
    {
        if(StringPredicates.isNullOrEmpty(id))
            throw new IllegalArgumentException("AGV Dock Id should neither be null or empty");
        this.id = id;
        this.start = start;
        this.end = end;
        this.depth = depth;
        this.access = access;
        this.isAssigned = false;
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

    public Accessibility accessFrom() {
        return access;
    }

    public boolean assignAGV(){
        if(isAssigned)
            return false;
        else
            isAssigned = true;
        return true;
    }

    public boolean isFree(){
        return !isAssigned;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgvDock agvDock = (AgvDock) o;
        return id.equalsIgnoreCase(agvDock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public int compareTo(String other) {
        return AggregateRoot.super.compareTo(other);
    }


    @Override
    public String toString() {
        return String.format("AGV dock %s: %nStarts: %s%nEnds: %s%nDepth: %s%nAccess via: %s", id, start, end, depth, access);
    }

    @Override
    public String identity() {
        return id;
    }

    @Override
    public boolean hasIdentity(String id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
