package eapli.base.categorymanagement.application;

import eapli.base.categorymanagement.domain.Category;

public class ListCategoryController {
    private final ListCategoryService svc=new ListCategoryService();
    public Iterable<Category> allCategories(){
        return svc.allCategories();
    }
}
