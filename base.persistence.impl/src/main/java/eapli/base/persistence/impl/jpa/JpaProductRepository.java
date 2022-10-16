package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import org.apache.commons.lang3.reflect.Typed;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Optional;

public class JpaProductRepository extends JpaAutoTxRepository<Product,String,String> implements ProductRepository {

    public JpaProductRepository(TransactionalContext tx) {
        super(tx,"id");
    }

    public JpaProductRepository(final String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(),"id");
    }

    @Override
    public Iterable<Product> findAllProducts() {
        return super.findAll();
    }

    @Override
    public TypedQuery<Product> query(final String queryString, final Class<Product> class1) {
        return super.createQuery(queryString,class1);
    }
    @Override
    public TypedQuery<Product> findById(final String internalCode, final Class<Product> class1){
        return super.createQuery("SELECT p from Product p where IDPRODUCT = '" + internalCode + "'",class1);
    }

}
