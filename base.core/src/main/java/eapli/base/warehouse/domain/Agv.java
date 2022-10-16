package eapli.base.warehouse.domain;

import eapli.base.utils.Description;
import eapli.base.utils.Measurement;
import eapli.base.warehouse.dto.AgvDto;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Agv implements AggregateRoot<String> {

    private static final int ID_MAX_LENGTH = 8;
    @Id
    private String id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="maxLength",
                    column=@Column(name="descriptionMaxLength"))
    })
    private Description description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="description",
                    column=@Column(name="model")),
            @AttributeOverride(name="maxLength",
                    column=@Column(name="modelNameMaxLength"))
    })
    private Description model;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="quantity",
                    column=@Column(name="maxWeight")),
            @AttributeOverride(name="unit",
                    column=@Column(name="maxWeightUnit"))
    })
    private Measurement maxWeight;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="quantity",
                    column=@Column(name="maxVolume")),
            @AttributeOverride(name="unit",
                    column=@Column(name="maxVolumeUnit"))
    })
    private Measurement maxVolume;

    private double autonomy;

    private String dockId;

    @Enumerated(EnumType.STRING)
    private AgvStatus currentStatus;

    @Embedded
    private Square position;



    protected Agv(){}

    public Agv(AgvDto dto){
        this(dto.getId(), dto.getDescription(), dto.getModel(), dto.getMaxWeight(), dto.getMaxVolume(),
                dto.getAutonomy(), dto.getDock(), dto.getCurrentStatus());
    }

    public Agv(String id, Description description, Description model, Measurement maxWeight,
               Measurement maxVolume, double autonomy, AgvDock dock, AgvStatus currentStatus){
        Preconditions.nonNull(id, "Id cannot be null");
        if(id.length() > ID_MAX_LENGTH)
            throw new IllegalArgumentException("Id cannot have more than " + ID_MAX_LENGTH + " chars");
        this.id = id;
        this.description = description;
        this.model = model;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        if(autonomy > 0)
            this.autonomy = autonomy;
        else
            throw new IllegalArgumentException("The AGV autonomy has to be more than 0 hours");
        this.dockId = dock.identity();
        this.currentStatus = currentStatus;
        this.position = dock.startsFrom();
    }

    public void changeStatusTo(AgvStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void changeStatusTo(String currentStatus){
        if(currentStatus.equalsIgnoreCase(AgvStatus.AVAILABLE.toString()))
            this.currentStatus = AgvStatus.AVAILABLE;
        else if(currentStatus.equalsIgnoreCase(AgvStatus.CHARGING.toString()))
            this.currentStatus = AgvStatus.CHARGING;
        else if(currentStatus.equalsIgnoreCase(AgvStatus.IN_MAINTENANCE.toString()))
            this.currentStatus = AgvStatus.IN_MAINTENANCE;
        else if(currentStatus.equalsIgnoreCase(AgvStatus.OCCUPIED.toString()))
            this.currentStatus = AgvStatus.OCCUPIED;
        else
            throw new IllegalArgumentException("ERROR: Invalid status");

    }
    public String agvDockId() {
        return dockId;
    }

    public AgvStatus currentStatus() {
        return currentStatus;
    }

    public String agvId (){
        return id;
    }

    public Square currentPosition(){return position;}

    public double batterySOC() {
        return autonomy;
    }

    public void changePosition(Square position) {
        this.position = position;
    }

    public void updateBatteryCharge(double autonomy) {
        this.autonomy = autonomy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agv agv = (Agv) o;
        return id.equals(agv.id);
    }

    public Measurement maxVolume(){
        return maxVolume;
    }

    public Measurement maxWeight() {
        return maxWeight;
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
    public String identity() {
        return id;
    }

    @Override
    public boolean hasIdentity(String id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
