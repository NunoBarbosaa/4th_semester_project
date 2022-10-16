package eapli.base.app.backoffice.console.presentation.category;

import eapli.base.categorymanagement.domain.Category;
import eapli.framework.visitor.Visitor;

public class CategoryPrinter implements Visitor<Category> {
    @Override
    public void visit(final Category visitee) {
        System.out.printf("Category code: %s%nCategory description: %s%nActive: %s", visitee.getCategoryCode(), visitee.description(), visitee.isActive());
    }
}
