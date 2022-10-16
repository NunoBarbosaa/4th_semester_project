package eapli.base.persistence.impl.inmemory;

import eapli.base.productmanagement.domain.Product;
import eapli.base.shoppingcart.domain.ShoppingCart;
import eapli.base.shoppingcart.domain.ShoppingCartItem;
import eapli.base.shoppingcart.repositories.ShoppingCartRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import javax.persistence.TypedQuery;

public class InMemoryShoppingCartRepository extends InMemoryDomainRepository<ShoppingCart,Long> implements ShoppingCartRepository {

    @Override
    public TypedQuery<ShoppingCart> findByClient(String queryString, Class<ShoppingCart> class1) {
        return null;
    }

    @Override
    public void removeShoppingCartItem(ShoppingCartItem queryString, Class<ShoppingCart> class1) {

    }

    @Override
    public TypedQuery<ShoppingCartItem> getShoppingCart(String queryString, Class<ShoppingCartItem> class1) {
        return null;
    }

    @Override
    public TypedQuery<ShoppingCartItem> findCartItem(String queryString, Class<ShoppingCartItem> class1) {
        return null;
    }
}
