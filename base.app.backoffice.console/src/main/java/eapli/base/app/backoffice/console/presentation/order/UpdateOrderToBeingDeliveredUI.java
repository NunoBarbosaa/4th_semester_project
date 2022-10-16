package eapli.base.app.backoffice.console.presentation.order;

import eapli.base.app.backoffice.console.presentation.warehouse.OrderPrinter;
import eapli.base.ordermanagement.application.UpdateOrderToBeingDeliveredController;
import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;

public class UpdateOrderToBeingDeliveredUI extends AbstractUI {

    private final UpdateOrderToBeingDeliveredController updateOrderToBeingDeliveredController = new UpdateOrderToBeingDeliveredController();

    @Override
    protected boolean doShow() {
        List<OrderDTO> orderDTOList;
        orderDTOList = updateOrderToBeingDeliveredController.getReadyToBeDeliveredOrders();

        if (orderDTOList.isEmpty()) {
            System.out.println("There's no orders ready to be delivered");
            return false;
        }
        int option;
        long orderID;
        boolean invalidOrderID = true;
        do {
            OrderPrinter.print(orderDTOList);
            do {
                orderID = Console.readLong("Order ID: ");
                try {
                    updateOrderToBeingDeliveredController.checkIfOrderIsValid(orderID);
                    invalidOrderID = false;
                } catch (Exception e) {
                    System.out.println("\nInvalid Order ID.");
                }

            } while (invalidOrderID);
            orderDTOList = updateOrderToBeingDeliveredController.updateOrderState(orderID);
            System.out.println("\nPretend to update another Order?");
            System.out.println("1 - Yes\n2 - No\n");
            option = Console.readOption(1, 2, 2);
        } while (option == 1);
        return true;
    }

    @Override
    public String headline() {
        return "List of ready to be delivered Orders:";
    }

}
