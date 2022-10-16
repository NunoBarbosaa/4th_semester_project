package eapli.base.shoppingcart.repositories;

import eapli.base.productmanagement.domain.Product;
import eapli.base.shoppingcart.domain.ShoppingCart;
import eapli.base.shoppingcart.domain.ShoppingCartItem;
import eapli.framework.domain.repositories.DomainRepository;

import javax.persistence.TypedQuery;

public interface ShoppingCartRepository extends DomainRepository<Long, ShoppingCart> {

    public TypedQuery<ShoppingCart> findByClient(final String queryString, final Class<ShoppingCart> class1);

    public void removeShoppingCartItem(final ShoppingCartItem queryString, final Class<ShoppingCart> class1);

    public TypedQuery<ShoppingCartItem> getShoppingCart(final String queryString, final Class<ShoppingCartItem> class1);

    public TypedQuery<ShoppingCartItem> findCartItem(final String queryString, final Class<ShoppingCartItem> class1);



}
