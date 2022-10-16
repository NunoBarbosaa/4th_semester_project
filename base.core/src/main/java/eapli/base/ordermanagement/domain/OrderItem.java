package eapli.base.ordermanagement.domain;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.DomainEntity;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem implements DomainEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDORDER_ITEM")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDPRODUCT")
    private Product product;

    private int quantity;

    @Embedded
    private TotalPrice totalPrice;

    public OrderItem(final Product product, final int quantity, final double priceWithoutTaxes, final double priceWithTaxes){
        this.product = product;
        this.quantity = quantity;
        totalPrice(priceWithoutTaxes, priceWithTaxes);
    }

    private void totalPrice(final double priceWithoutTaxes, final double priceWithTaxes) {
        this.totalPrice = new TotalPrice(priceWithoutTaxes, priceWithTaxes);
    }

    public OrderItem() {

    }

    public Product product() {
        return product;
    }

    public int quantity() {
        return quantity;
    }

    public TotalPrice totalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean sameAs(final Object other) {
        final OrderItem orderItem = (OrderItem) other;
        return this.equals(orderItem) && product.equals(orderItem.product) && quantity == orderItem.quantity
                && totalPrice.equals(orderItem.totalPrice);
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean hasIdentity(final Long id) {
        return id.equals(identity());
    }
}
