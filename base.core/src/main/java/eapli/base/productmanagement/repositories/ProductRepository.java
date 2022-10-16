package eapli.base.productmanagement.repositories;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.repositories.DomainRepository;
import org.apache.commons.lang3.reflect.Typed;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

public interface ProductRepository extends DomainRepository<String, Product> {

    /**
     * Finds all the products available and returns them
     * @return products in the database
     */
    public Iterable<Product> findAllProducts();
    public TypedQuery<Product> query(final String queryString, final Class<Product> class1);

    public TypedQuery<Product> findById(final String queryString, final Class<Product> class1);
}

