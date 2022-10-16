package eapli.base.ordermanagement.repositories;

import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.OrderItem;
import eapli.base.ordermanagement.domain.OrderStatus;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface OrderRepository extends DomainRepository<Long, Order> {

    /**
     * Returns the order with the id given if it exists
     * @param id to use in search
     * @return order if it exists
     */
    public Order findByOrderId(Long id);

    /**
     * Finds all the orders available and returns them
     * @return orders in the database
     */
    public Iterable<Order> findAllOrders();

    Iterable<Order> findUnpreparedOrders();

    Iterable<Order> findAlreadyPreparedOrders();

    Iterable<Order> findReadyToBeDeliveredOrders();

    Iterable<Order> findClientOrders(String email);

     OrderStatus findOrderStatusById(long id);

    Iterable<OrderItem> findOrderProducts(String id);

}

