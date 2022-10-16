package eapli.base.categorymanagement.repository;


import eapli.base.categorymanagement.domain.Category;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.domain.repositories.LockableDomainRepository;

public interface CategoryRepository
        extends DomainRepository<String, Category>, LockableDomainRepository<String, Category> {

    /**
     * Returns the active dish types.
     *
     * @return An iterable for DishType.
     */
    Iterable<Category> activeCategories();
}


