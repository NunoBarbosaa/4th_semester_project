package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.categorymanagement.domain.Category;
import eapli.base.categorymanagement.repository.CategoryRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

public class JpaCategoryRepository extends JpaAutoTxRepository<Category,String,String> implements CategoryRepository {
    public JpaCategoryRepository(TransactionalContext tx) {
        super(tx, "id");
    }

    public JpaCategoryRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }


    @Override
    public Iterable<Category> activeCategories() {
        return super.findAll();
    }
}
