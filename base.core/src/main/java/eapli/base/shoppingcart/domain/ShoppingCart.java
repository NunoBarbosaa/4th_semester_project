package eapli.base.shoppingcart.domain;


import eapli.base.customermanagement.domain.Customer;
import eapli.base.ordermanagement.domain.OrderItem;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class ShoppingCart implements AggregateRoot<Long> {

    /* orm pk*/
    @Id
    @GeneratedValue
    private long pk;

    //business id
    @Column(name = "idShoppingCart")
    @GeneratedValue
    private long id;

    @OneToOne
    private Customer customer;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idShoppingCart")
    private List<ShoppingCartItem> itemList;

    protected ShoppingCart() {
    }

    public ShoppingCart(Customer cust) {
        this.customer = cust;
        this.itemList = new ArrayList<>();
    }

    public void addItem(ShoppingCartItem s) {
        this.itemList.add(s);
    }

    public void removeItem(ShoppingCartItem s) {
        this.itemList.remove(s);
    }

    @Override
    public boolean sameAs(Object other) {
        if (this == null || other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        if (((ShoppingCart) other).identity().equals(this.identity()))
            return true;
        return false;
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "customer=" + customer +
                ", itemList=" + itemList +
                '}';
    }

    public List<ShoppingCartItem> getItemList() {
        return itemList;
    }

    public boolean alreadyContains(ShoppingCartItem s) {
        String st = s.getShortDesc();
        for (ShoppingCartItem shoppingCartItem : getItemList()) {
            if (st.equalsIgnoreCase(shoppingCartItem.getShortDesc())) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity()+ s.getQuantity());
                shoppingCartItem.setTotalPrice(shoppingCartItem.getTotalPrice()+s.getTotalPrice());
                return true;
            }
        }
        return false;
    }


}
