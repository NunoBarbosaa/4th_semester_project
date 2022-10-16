package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.categorymanagement.application.RegisterCategoryController;
import eapli.base.categorymanagement.domain.Category;
import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CategoryBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(CategoryBootstrapper.class);
    @Override
    public boolean execute() {
        register("CPA", "Cell Phones & Accessories");
        register("APA", "Automotive Parts & Suppliers");
        register("VGC", "Video Games & Consoles");
        return true;
    }

    private void register(final String code, final String description){
        final RegisterCategoryController controller = new RegisterCategoryController();
        try {
            controller.registerCategory(code, description);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", code);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}
