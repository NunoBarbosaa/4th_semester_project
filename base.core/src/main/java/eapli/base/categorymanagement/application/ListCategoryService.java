package eapli.base.categorymanagement.application;

import eapli.base.categorymanagement.domain.Category;
import eapli.base.categorymanagement.repository.CategoryRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListCategoryService {
//    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CategoryRepository categoryRepository = PersistenceContext.repositories().categories();

    public Iterable<Category> allCategories() {
//        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER);

        return categoryRepository.findAll();
    }
}
