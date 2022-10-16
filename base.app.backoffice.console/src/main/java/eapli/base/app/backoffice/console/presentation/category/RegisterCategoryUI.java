package eapli.base.app.backoffice.console.presentation.category;

import eapli.base.categorymanagement.application.RegisterCategoryController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

public class RegisterCategoryUI extends AbstractUI{

    private final RegisterCategoryController theController = new RegisterCategoryController();

    @Override
    protected boolean doShow() {
        final String alphaNumericCode = Console.readLine("Category Acronym:");
        final String description = Console.readLine("Category Description:");

        try {
            this.theController.registerCategory(alphaNumericCode, description);
            System.out.println("Category created successfully!");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {

            System.out.println("That code is already in use.");
        }catch (IllegalArgumentException e2){
            System.out.printf("%s\n",e2.getMessage());
        }
        return false;
    }

    @Override
    public String headline() {
        return "Register Category";
    }
}
