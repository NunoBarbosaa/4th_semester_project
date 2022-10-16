package eapli.base.ordermanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.OrderStatus;
import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehouse.application.services.OrderService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.Date;
import java.util.List;

public class UpdateOrderToBeingDeliveredController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final OrderRepository orderRepository = PersistenceContext.repositories().orders();
    private final OrderService orderService = new OrderService();
    private static final String TYPE = "READY_TO_BE_DELIVERED";

    public List<OrderDTO> getReadyToBeDeliveredOrders() {
        authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK);
        return orderService.getOrders(TYPE);
    }

    public List<OrderDTO> updateOrderState(long id) {
        Order order = orderRepository.findByOrderId(id);
        order.changeStatus(new Date(), OrderStatus.DELIVERING);
        orderRepository.save(order);
        return orderService.getOrders(TYPE);
    }
    public Order checkIfOrderIsValid(long id) {
        return orderRepository.findByOrderId(id);
    }
}

