package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.warehouse.application.ConfigureAgvController;
import eapli.base.warehouse.domain.Accessibility;
import eapli.base.warehouse.domain.AgvDock;
import eapli.base.warehouse.domain.Square;
import eapli.base.warehouse.domain.AgvStatus;
import eapli.base.warehouse.dto.AgvDto;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AGVBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(AGVBootstrapper.class);
    @Override
    public boolean execute() {
        register(TestDataConstants.AGVID,TestDataConstants.AGV_DESCRICAO,TestDataConstants.AGV_MODEL,TestDataConstants.AGV_MAX_WEIGHT,TestDataConstants.AGV_MAX_VOLUME,TestDataConstants.AGV_AUTONOMY,TestDataConstants.AGV_STATUS,
            new AgvDock("D1",new Square(1,3),new Square(1,3),new Square(1,3), Accessibility.L_PLUS));
        register("AGV2", "AGV2 Warehouse 1", TestDataConstants.AGV_MODEL, TestDataConstants.AGV_MAX_WEIGHT, TestDataConstants.AGV_MAX_VOLUME, TestDataConstants.AGV_AUTONOMY, TestDataConstants.AGV_STATUS,
                new AgvDock("D2",new Square(1,5),new Square(1,5),new Square(1,5), Accessibility.L_PLUS));
        register("AGV3", "AGV3 Warehouse 1 (Big Boy)", "AGV MDL-2 Heavy", 25, 1.3, 3.5, TestDataConstants.AGV_STATUS,
                new AgvDock("D3",new Square(1,13),new Square(1,13),new Square(1,13), Accessibility.L_PLUS));

        return true;
    }

    private void register(final String id, final String desc, final String model, final double maxWeight, final double maxVolume, final double autonomy, final AgvStatus status, final AgvDock dock){

        final ConfigureAgvController controller = new ConfigureAgvController();
        try {
            AgvDto dto =new AgvDto(id,desc,model,maxWeight,maxVolume,autonomy,status);
            dto.setDock(new AgvDock("D1",new Square(1,3),new Square(1,3),new Square(1,3), Accessibility.L_MINUS));
            controller.configureAgv(dto);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", id);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}
