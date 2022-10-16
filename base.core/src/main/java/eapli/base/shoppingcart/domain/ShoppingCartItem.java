package eapli.base.shoppingcart.domain;

import eapli.base.productmanagement.domain.Product;

import javax.persistence.*;

@Entity
public class ShoppingCartItem {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Product product;

    private int quantity;
    private double totalPrice;
    private String shortDesc;

    protected ShoppingCartItem(){}

    public ShoppingCartItem(int quantity, Product product){
        this.quantity = quantity;
        this.product = product;
        this.totalPrice = product.getPrice().getPriceWithTaxes()*quantity;
        this.shortDesc =product.getShortDescription().description();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" + id+"id"+
                "quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
