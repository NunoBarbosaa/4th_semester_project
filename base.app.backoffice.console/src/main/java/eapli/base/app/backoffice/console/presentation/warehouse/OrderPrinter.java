package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.ordermanagement.dto.OrderDTO;

import java.util.List;

public class OrderPrinter {

    public static void print(List<OrderDTO> orderList) {
        System.out.print("\n-----------");
        System.out.print("| Orders |");
        System.out.print("-----------\n\n");
        for (OrderDTO orderUnprepared : orderList){
            System.out.println("Order ID: " + orderUnprepared.id);
            System.out.println("Date: " + orderUnprepared.date);
            System.out.println("Sales Clerk Email: " + orderUnprepared.customerName);
            System.out.printf("Total Price: %.2f€%n", orderUnprepared.total);
            System.out.println();
        }
    }
}
