package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.OrderItem;
import eapli.base.ordermanagement.domain.OrderStatus;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class JpaOrderRepository extends JpaAutoTxRepository<Order, Long, Long> implements OrderRepository {
    public JpaOrderRepository(TransactionalContext tx) {
        super(tx, "id");
    }

    public JpaOrderRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }

    @Override
    public Order findByOrderId(Long id) {
        TypedQuery<Order> query = super.createQuery(
                "SELECT po FROM Order po WHERE IDORDER = '" + id + "'",
                Order.class);
        return query.getSingleResult();
    }

    @Override
    public Iterable<Order> findAllOrders() {
        return null;
    }

    @Override
    public Iterable<Order> findUnpreparedOrders() {
        Query query = super.createNativeQuery("SELECT * FROM PRODUCT_ORDER o, Customer c, ORDER_STATUS " +
                "os WHERE o.IDORDER = os.IDORDER AND o.IDCUSTOMER = c.IDCUSTOMER AND os.ORDERSTATUS ='WAITING_FOR_AGV' " +
                "AND os.DATE = (SELECT MAX(ost.DATE) FROM ORDER_STATUS ost WHERE ost.IDORDER = os.IDORDER) order by o.DATE DESC;", Order.class);
        return query.getResultList();
    }

    @Override
    public Iterable<Order> findAlreadyPreparedOrders() {
        Query query = super.createNativeQuery("SELECT * FROM PRODUCT_ORDER o, Customer c, ORDER_STATUS " +
                "os WHERE o.IDORDER = os.IDORDER AND o.IDCUSTOMER = c.IDCUSTOMER AND os.ORDERSTATUS ='READY_TO_BE_PACKED' " +
                "AND os.DATE = (SELECT MAX(ost.DATE) FROM ORDER_STATUS ost WHERE ost.IDORDER = os.IDORDER);", Order.class);
        return query.getResultList();
    }

    @Override
    public Iterable<Order> findReadyToBeDeliveredOrders() {
        Query query = super.createNativeQuery("SELECT * FROM PRODUCT_ORDER o, Customer c, ORDER_STATUS " +
                "os WHERE o.IDORDER = os.IDORDER AND o.IDCUSTOMER = c.IDCUSTOMER AND os.ORDERSTATUS ='READY_TO_BE_DELIVERED' " +
                "AND os.DATE = (SELECT MAX(ost.DATE) FROM ORDER_STATUS ost WHERE ost.IDORDER = os.IDORDER);", Order.class);
        return query.getResultList();
    }

    @Override
    public Iterable<Order> findClientOrders(String id) {
        Query query = super.createNativeQuery("SELECT * FROM PRODUCT_ORDER o WHERE o.IDCUSTOMER ='"+id+"' AND o.IDORDER >=121",Order.class);
        return query.getResultList();
    }

    @Override
    public OrderStatus findOrderStatusById(long id) {
       Query query = super.createNativeQuery("SELECT ORDERSTATUS FROM ORDER_STATUS os WHERE os.IDORDER = "+id+"",Order.class);
       return (OrderStatus) query.getSingleResult();
    }

    @Override
    public Iterable<OrderItem> findOrderProducts(String id) {
        System.out.println(id);
        Query query = super.createNativeQuery("SELECT * from ORDER_ITEM oi WHERE oi.IDORDER ="+id,OrderItem.class);
        return query.getResultList();
    }


}
