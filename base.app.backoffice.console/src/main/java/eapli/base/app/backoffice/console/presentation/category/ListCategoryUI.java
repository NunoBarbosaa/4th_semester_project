package eapli.base.app.backoffice.console.presentation.category;

import eapli.base.categorymanagement.application.ListCategoryController;
import eapli.base.categorymanagement.domain.Category;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

import java.util.AbstractList;

public class ListCategoryUI extends AbstractListUI<Category> {

    private final ListCategoryController theController = new ListCategoryController();

    @Override
    protected Iterable<Category> elements() {
        return this.theController.allCategories();
    }

    @Override
    protected Visitor<Category> elementPrinter() {
        return new CategoryPrinter();
    }

    @Override
    protected String elementName() {
        return "Category";
    }

    @Override
    protected String listHeader() {
        return "Categories";
    }

    @Override
    protected String emptyMessage() {
        return "No data.";
    }

    @Override
    public String headline() {
        return "List Categories";
    }
}
