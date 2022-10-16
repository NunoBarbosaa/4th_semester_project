package eapli.base.productmanagement.application;

import eapli.base.Application;
import eapli.base.categorymanagement.domain.Category;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.utils.Description;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaBaseRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaTransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaTransactionalRepository;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.Iterator;

public class ViewProductsCatalogController {

//    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository repo = PersistenceContext.repositories().products();

    public Iterable<Product> getProductsFilter(String filter, String key) {
//        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_CLERK, BaseRoles.ADMIN);
        switch (filter) {
            case "Category":
                TypedQuery<Product> p4 = repo.query("SELECT p FROM Product p WHERE p.category.alphaNumericCode = :keyCategory", Product.class);
                p4.setParameter("keyCategory", key);
                return p4.getResultList();
            case "Description":
                String s = "SELECT p FROM Product p WHERE p.shortDescription.description LIKE '%"+key+"%' OR p.extendedDescription.description LIKE '%"+key+"%' OR p.technicalDescription.description LIKE '%"+key+"%' ";
                TypedQuery<Product> p = repo.query(s,Product.class);
                return p.getResultList();
            case "Brand":
                TypedQuery<Product> p1 = repo.query("SELECT p FROM Product p WHERE p.brand.name = :keyBrand",Product.class);
                p1.setParameter("keyBrand",key);
                return p1.getResultList();
            case "Price":
                TypedQuery<Product> p2 = null;
                if(key.equalsIgnoreCase("ASC"))
                    p2= repo.query("SELECT p FROM Product p ORDER BY p.price.priceWithTaxes ASC",Product.class);
                if(key.equalsIgnoreCase("DESC"))
                    p2= repo.query("SELECT p FROM Product p ORDER BY p.price.priceWithTaxes DESC",Product.class);
                return p2.getResultList();
            case "all":
                return repo.findAllProducts();
            case "Internal Code - Single Product":
                TypedQuery<Product> p3 = repo.query("SELECT p FROM Product p where p.internalCode = :keyCode",Product.class);
                p3.setParameter("keyCode",key);
                return p3.getResultList();
            case "Barcode - Single Product":
                TypedQuery<Product> p5 = repo.query("SELECT p FROM Product p WHERE p.barcode.data = :keyCode",Product.class);
                p5.setParameter("keyCode",key);
                return p5.getResultList();
        }
        return null;

    }


}