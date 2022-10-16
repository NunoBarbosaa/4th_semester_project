package eapli.base.warehouse.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.warehouse.WarehouseContainer;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehouse.domain.Agv;
import eapli.base.warehouse.domain.AgvDock;
import eapli.base.warehouse.domain.Warehouse;
import eapli.base.warehouse.dto.AgvDto;
import eapli.base.warehouse.repositories.AgvRepository;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class ConfigureAgvController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AgvRepository agvRepo = PersistenceContext.repositories().agvs();

    private final Warehouse warehouse = WarehouseContainer.activeWarehouse();
    public Iterable<AgvDock> getAvailableAgvDocks(){
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.WAREHOUSE_EMPLOYEE);
        return WarehouseContainer.activeWarehouse().freeDocks();
    }

    public boolean configureAgv(AgvDto dto){
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.WAREHOUSE_EMPLOYEE);
        Agv agv = new Agv(dto);

        agvRepo.save(agv);
        return warehouse.assignDock(agv.agvDockId());
    }
}
