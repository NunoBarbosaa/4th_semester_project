package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.warehouse.application.AlreadyPreparedOrdersByAGVController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;
import java.util.Scanner;

public class AlreadyPreparedByAgvUI extends AbstractUI {

    private final AlreadyPreparedOrdersByAGVController alreadyPreparedOrdersByAGVController = new AlreadyPreparedOrdersByAGVController();

    @Override
    protected boolean doShow() {

        //get prepared orders list

        List<OrderDTO> orderList;

        orderList = alreadyPreparedOrdersByAGVController.getAlreadyPreparedOrdersList();

        if(orderList.isEmpty()){
            System.out.println("There is no prepared orders.");
            return false;
        }

        int option;
        long orderID;
        boolean invalidOrderID = true;
        do {
            OrderPrinter.print(orderList);

            do {
                orderID = Console.readLong("Order ID: ");
                try {
                    alreadyPreparedOrdersByAGVController.checkIfOrderIsValid(orderID);
                    invalidOrderID = false;
                } catch (Exception e) {
                    System.out.println("\nInvalid Order ID.");
                }
            } while(invalidOrderID);

            orderList = alreadyPreparedOrdersByAGVController.updateOrderState(orderID);

            System.out.println("\nPretend to update another Order?");
            System.out.println("1 - Yes\n2 - No\n");
            option = Console.readOption(1,2,2 );
        } while (option == 1);
        return true;
    }

    @Override
    public String headline() {
        return "List of orders already prepared:";
    }
}
