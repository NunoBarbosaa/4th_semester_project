package eapli.base.persistence.impl.inmemory;

import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.OrderItem;
import eapli.base.ordermanagement.domain.OrderStatus;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryOrderRepository extends InMemoryDomainRepository<Order, Long> implements OrderRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Order findByOrderId(Long orderId) {
        return null;
    }

    @Override
    public Iterable<Order> findAllOrders() {
        return findAll();
    }

    @Override
    public Iterable<Order> findUnpreparedOrders() { return null; }

    @Override
    public Iterable<Order> findAlreadyPreparedOrders() {
        return null;
    }

    @Override
    public Iterable<Order> findReadyToBeDeliveredOrders() {
        return null;
    }

    @Override
    public Iterable<Order> findClientOrders(String email) {
        return null;
    }

    @Override
    public OrderStatus findOrderStatusById(long id) {
        return null;
    }

    @Override
    public Iterable<OrderItem> findOrderProducts(String id) {
        return null;
    }
}
