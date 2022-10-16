package eapli.base.infrastructure.warehouse;

import eapli.base.Application;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.warehouse.application.WarehouseSetupController;
import eapli.base.warehouse.domain.Agv;
import eapli.base.warehouse.domain.AgvDock;
import eapli.base.warehouse.domain.Warehouse;
import eapli.base.warehouse.repositories.AgvRepository;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public final class WarehouseContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseContainer.class);

    private static Warehouse warehouse;

    private WarehouseContainer() {
        // ensure utility
    }

    public static Warehouse activeWarehouse() {
        if (warehouse == null) {
            final String jsonFileLocation = Application.settings().getProperty("warehouse.jsonFileLocation");
            final AgvRepository agvRepo = PersistenceContext.repositories().agvs();
            try {
                Optional<Warehouse> warehouseOptional = new WarehouseSetupController().processJson(jsonFileLocation);
                if(warehouseOptional.isPresent()) {
                    warehouse = warehouseOptional.get();
                    Iterable<Agv> agvs = agvRepo.findAllAgv();
                    for(Agv agv : agvs){
                        for (AgvDock dock : warehouse.docks()) {
                            if(dock.hasIdentity(agv.agvDockId()))
                                dock.assignAGV();
                        }
                    }
                }
            } catch (IOException | ParseException e) {
                LOGGER.error("ERROR: Could not load the warehouse file");
                throw new IllegalStateException("Unable to load the warehouse JSON file");
            }
        }
        return warehouse;
    }
}
