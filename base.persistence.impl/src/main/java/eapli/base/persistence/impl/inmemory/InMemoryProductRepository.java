package eapli.base.persistence.impl.inmemory;

import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import javax.persistence.TypedQuery;

public class InMemoryProductRepository extends InMemoryDomainRepository<Product, String> implements ProductRepository {
    @Override
    public Iterable<Product> findAllProducts() {
        return super.findAll();
    }

    @Override
    public TypedQuery<Product> query(String queryString, Class<Product> class1) {
        return null;
    }

    @Override
    public TypedQuery<Product> findById(String queryString, Class<Product> class1) {
        return null;
    }
}
