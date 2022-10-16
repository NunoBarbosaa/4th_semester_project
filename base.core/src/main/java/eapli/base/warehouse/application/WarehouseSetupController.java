package eapli.base.warehouse.application;

import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehouse.domain.Warehouse;
import eapli.base.warehouse.imports.JsonWarehouse;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@UseCaseController
public class WarehouseSetupController {

    //private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public Optional<Warehouse> processJson(String file) throws IOException, ParseException {
        //authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.WAREHOUSE_EMPLOYEE, BaseRoles.ADMIN);

        FileReader reader = new FileReader(file);

        Warehouse warehouse;

        JSONObject jsonObject = JsonWarehouse.parseJsonFile(reader);
        if(jsonObject != null) {
            warehouse = JsonWarehouse.createWarehouse(jsonObject);
            JsonWarehouse.createAisles(jsonObject, warehouse);
            JsonWarehouse.createAgvDock(jsonObject, warehouse);
        }else {
            return Optional.empty();
        }

        return Optional.of(warehouse);
    }

}
