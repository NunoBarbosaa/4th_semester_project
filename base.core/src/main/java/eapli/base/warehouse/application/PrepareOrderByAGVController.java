package eapli.base.warehouse.application;

import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehouse.application.services.AgvOrderAssignmentService;
import eapli.base.warehouse.dto.AgvDto;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.*;
import java.util.List;


public class PrepareOrderByAGVController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AgvOrderAssignmentService service = new AgvOrderAssignmentService();

    public boolean startsCommunication() throws IOException {
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.WAREHOUSE_EMPLOYEE);
        return service.startsCommunication();
    }

    public List<OrderDTO> getOrders() {
        return service.getOrders();
    }

    public boolean checkIfOrderIsValid(long id) {
        return service.checkIfOrderIsValid(id) != null;
    }

    public List<AgvDto> getAGVS() throws IOException {
        return service.getAGVS();
    }

    public boolean send(Long orderId, String agvID) throws IOException {
        return service.send(orderId, agvID);
    }

    public boolean disconnect() throws IOException {
        return service.disconnect();
    }
}
