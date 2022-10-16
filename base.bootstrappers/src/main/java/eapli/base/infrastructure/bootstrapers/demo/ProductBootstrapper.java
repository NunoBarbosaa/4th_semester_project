package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.categorymanagement.application.RegisterCategoryController;
import eapli.base.categorymanagement.domain.Category;
import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.productmanagement.application.SpecifyNewProductController;
import eapli.base.productmanagement.domain.*;
import eapli.base.utils.Description;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(ProductBootstrapper.class);

    @Override
    public boolean execute() {
        final RegisterCategoryController controller1=new RegisterCategoryController();

        Category c=controller1.registerCategory(TestDataConstants.PRODUCT_CATEGORY.getCategoryCode(),TestDataConstants.PRODUCT_CATEGORY.description());
        register(TestDataConstants.INT_CODE,TestDataConstants.SHORT_DESC,TestDataConstants.EXT_DESC,TestDataConstants.BARCODE,c,TestDataConstants.PRODUCT_BRAND,TestDataConstants.PRODUCT_PRICE, new Reference(""));
        register(TestDataConstants.INT2_CODE,TestDataConstants.SHORT_DESC2,TestDataConstants.EXT_DEC2,TestDataConstants.BARCODE2,c,TestDataConstants.PRODUCT_BRAND2,TestDataConstants.PRODUCT_PRICE2,new Reference(""));
        return true;
    }

    private void register(final String intCode, final Description shortDesc, final Description extendedDesc, final Barcode barcode, final Category category1, final Brand brand, final Price price, final Reference reference){

        final SpecifyNewProductController controller = new SpecifyNewProductController();

        try {

            Product p=controller.specifyNewProduct(intCode, shortDesc,extendedDesc, barcode);

            controller.setCategory(category1,p);
            controller.setReference(reference,p);
            controller.setBrand(brand,p);
            controller.setPrice(price,p);
            controller.saveRepository(p);

        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", shortDesc);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}
