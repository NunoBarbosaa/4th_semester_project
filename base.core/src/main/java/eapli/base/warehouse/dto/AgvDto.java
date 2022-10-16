package eapli.base.warehouse.dto;

import eapli.base.utils.Description;
import eapli.base.utils.Measurement;
import eapli.base.utils.Unit;
import eapli.base.warehouse.domain.AgvDock;
import eapli.base.warehouse.domain.AgvStatus;
import eapli.framework.representations.dto.DTO;

@DTO
public class AgvDto {

    private static final int DESCRIPTION_MAX_LENGTH = 30;
    private static final int MODEL_MAX_LENGTH = 50;
    private final String id;

    private final Description description;

    private final Description model;

    private final Measurement maxWeight;

    private final Measurement maxVolume;

    private final double autonomy;

    private final AgvStatus currentStatus;

    private final double lsquare;

    private final double wsquare;

    private AgvDock dock;


    public AgvDto(String id, String description, String model, double maxWeight,
                  double maxVolume, double autonomy, AgvStatus currentStatus) {
        this.id = id;
        this.description = new Description(description, DESCRIPTION_MAX_LENGTH);
        this.model = new Description(model, MODEL_MAX_LENGTH);
        this.maxWeight = new Measurement(maxWeight, Unit.KILOGRAM);
        this.maxVolume = new Measurement(maxVolume, Unit.CUBIC_METER);
        this.autonomy = autonomy;
        this.currentStatus = currentStatus;
        this.wsquare = 0;
        this.lsquare = 0;
    }

    public AgvDto(String id, double maxWeight, double maxVolume){
        this.id = id;
        this.description = new Description("", DESCRIPTION_MAX_LENGTH);
        this.model = new Description("", MODEL_MAX_LENGTH);
        this.maxWeight = new Measurement(maxWeight, Unit.KILOGRAM);
        this.maxVolume = new Measurement(maxVolume, Unit.CUBIC_METER);
        this.autonomy = 0;
        this.currentStatus = AgvStatus.AVAILABLE;
        this.wsquare = 0;
        this.lsquare = 0;
    }

    public AgvDto(String id, double maxWeight, double maxVolume, int lSquare, int wSquare, String status, int autonomy){
        this.id = id;
        this.description = new Description("", DESCRIPTION_MAX_LENGTH);
        this.model = new Description("", MODEL_MAX_LENGTH);
        this.maxWeight = new Measurement(maxWeight, Unit.KILOGRAM);
        this.maxVolume = new Measurement(maxVolume, Unit.CUBIC_METER);
        this.autonomy = autonomy;
        this.currentStatus = interpretStatus(status);
        this.wsquare = wSquare;
        this.lsquare = lSquare;
    }

    private AgvStatus interpretStatus(String status) {
        if(status.equalsIgnoreCase("Available"))
            return AgvStatus.AVAILABLE;
        if(status.equalsIgnoreCase("Occupied"))
            return AgvStatus.OCCUPIED;
        if(status.equalsIgnoreCase("Charging"))
            return AgvStatus.CHARGING;
        else
            return AgvStatus.IN_MAINTENANCE;
    }

    public void setDock(AgvDock dock) {
        this.dock = dock;
    }

    public String getId() {
        return id;
    }

    public Description getDescription() {
        return description;
    }

    public Description getModel() {
        return model;
    }

    public Measurement getMaxWeight() {
        return maxWeight;
    }

    public Measurement getMaxVolume() {
        return maxVolume;
    }

    public double getAutonomy() {
        return autonomy;
    }

    public AgvStatus getCurrentStatus() {
        return currentStatus;
    }

    public AgvDock getDock() {
        return dock;
    }

    public double getLsquare() {
        return lsquare;
    }

    public double getWsquare() {
        return wsquare;
    }
}
