package eapli.base.warehouse.application.services;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderService {

    //private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final OrderRepository orderRepository = PersistenceContext.repositories().orders();

    public List<OrderDTO> getOrders(String type){
        //authz.isAuthenticatedUserAuthorizedTo(BaseRoles.SALES_CLERK);

        Iterable<Order> iterable;

        if(type.equals("UNPREPARED")) iterable = orderRepository.findUnpreparedOrders();
        else iterable = orderRepository.findAlreadyPreparedOrders();

        Iterator<Order> iterator = iterable.iterator();

        String name, date;
        Long id;
        double orderTotal;
        Order order;
        OrderDTO orderDTO;

        List<OrderDTO> orderList = new ArrayList<>();
        while(iterator.hasNext()){
            order = iterator.next();
            id = order.identity();
            date = order.statusList().get(0).date().toString();
            name = order.customer().firstNames() + " " + order.customer().lastNames();
            orderTotal = order.totalPrice().priceWithTaxes();
            orderDTO = new OrderDTO(id, date, name, orderTotal);
            orderList.add(orderDTO);
        }
        return orderList;
    }
}
