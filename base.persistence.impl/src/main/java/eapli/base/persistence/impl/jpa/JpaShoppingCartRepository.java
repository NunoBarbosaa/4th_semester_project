package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.shoppingcart.domain.ShoppingCart;
import eapli.base.shoppingcart.domain.ShoppingCartItem;
import eapli.base.shoppingcart.repositories.ShoppingCartRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;


import org.springframework.transaction.annotation.Transactional;


public class JpaShoppingCartRepository extends JpaAutoTxRepository<ShoppingCart,Long,Long> implements ShoppingCartRepository {

    public JpaShoppingCartRepository(TransactionalContext tx) {
        super(tx,"id");
    }

    public JpaShoppingCartRepository(final String puname){
        super(puname, Application.settings().getExtendedPersistenceProperties(),"id");
    }

    @Override
    public TypedQuery<ShoppingCart> findByClient(String queryString, Class<ShoppingCart> class1) {
        return super.createQuery("SELECT c from ShoppingCart c WHERE c.customer.id ='"+queryString+"'",class1);
    }

    @Transactional
    @Override
    public void removeShoppingCartItem(ShoppingCartItem queryString, Class<ShoppingCart> class1) {
        super.entityManager().remove(super.entityManager().contains(queryString) ? ShoppingCartItem.class : super.entityManager().merge(queryString));
       // super.entityManager().createQuery("DELETE FROM ShoppingCartItem s WHERE s.id ="+queryString).executeUpdate();
       //super.createNativeQuery("DELETE FROM ShoppingCartItem s WHERE s.id ="+queryString,class1).executeUpdate();
    }

    @Override
    public TypedQuery<ShoppingCartItem> getShoppingCart(String queryString, Class<ShoppingCartItem> class1) {
        return super.createQuery("select sci from ShoppingCartItem sci, ShoppingCart sc where sc.id = (select id from ShoppingCart c where c.customer.id = "+ queryString+ ")",class1);

    }

    @Override
    public TypedQuery<ShoppingCartItem> findCartItem(String queryString, Class<ShoppingCartItem> class1) {
        return super.createQuery("SELECT s from ShoppingCartItem s WHERE s.id = "+queryString,class1);
    }


}
