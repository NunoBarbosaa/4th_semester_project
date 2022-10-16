package eapli.base.ordermanagement.application;

import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordermanagement.builder.OrderBuilder;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.OrderItem;
import eapli.base.ordermanagement.domain.OrderStatus;
import eapli.base.ordermanagement.dto.OrderItemDTO;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.*;

public class AddOrderController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CustomerRepository customerRepository = PersistenceContext.repositories().customers();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final OrderRepository orderRepository = PersistenceContext.repositories().orders();

    public Customer checkIfCustomerIsValid(final String vat) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_CLERK);
        return customerRepository.findByVAT(vat);
    }

    public boolean newOrder(final String vat, final List<OrderItemDTO> orderItemDTOList, final double priceWithTaxes, final double priceWithoutTaxes,
                            final AddressDTO billingAddress, final AddressDTO deliveringAddress, final int shipmentMethod, final int paymentMethod) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SALES_CLERK);
        Order order;
        List<OrderItem> orderItemList = addProductsToOrderItemList(orderItemDTOList);
        OrderBuilder orderBuilder = new OrderBuilder();
        orderBuilder.status(OrderStatus.CREATED, new Date());
        orderBuilder.customer(checkIfCustomerIsValid(vat));
        orderBuilder.salesClerkEmail(authz.session().get().authenticatedUser().email());
        orderBuilder.date(new Date());
        orderBuilder.orderItems(orderItemList);
        orderBuilder.totalPrice(priceWithTaxes, priceWithoutTaxes);
        orderBuilder.billingAddress(billingAddress);
        orderBuilder.deliveringAddress(deliveringAddress);
        orderBuilder.shipment(shipmentMethod);
        orderBuilder.payment(paymentMethod);
        order = orderRepository.save(orderBuilder.build());
        if(order != null){
            order.changeStatus(new Date(), OrderStatus.WAITING_FOR_AGV);
            orderRepository.save(order);
        }
        return true;
    }

    private List<OrderItem> addProductsToOrderItemList(List<OrderItemDTO> orderItemDTOList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        Product product;
        for (OrderItemDTO oi : orderItemDTOList){
            product = productRepository.findById(oi.productID(), Product.class).getSingleResult();
            orderItemList.add(new OrderItem(product, oi.quantity(), oi.priceWithoutTaxes(), oi.priceWithTaxes()));
        }
        return orderItemList;
    }

}
