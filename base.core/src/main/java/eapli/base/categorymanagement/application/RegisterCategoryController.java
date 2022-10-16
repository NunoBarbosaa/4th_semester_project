package eapli.base.categorymanagement.application;

import eapli.base.categorymanagement.domain.Category;
import eapli.base.categorymanagement.repository.CategoryRepository;
import eapli.base.usermanagement.domain.SalesRoles;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class RegisterCategoryController {
        private final AuthorizationService authz = AuthzRegistry.authorizationService();
        private final CategoryRepository repository = PersistenceContext.repositories().categories();

        public Category registerCategory(final String alphaNumericCode, final String description) {
            authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK);
            final Category newCategory = new Category(alphaNumericCode, description);
            return repository.save(newCategory);
        }
    }

