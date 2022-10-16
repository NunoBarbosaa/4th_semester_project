package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.warehouse.application.PrepareOrderByAGVController;
import eapli.base.warehouse.dto.AgvDto;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.io.IOException;
import java.util.List;


public class PrepareOrderByAGVUI extends AbstractUI {

    private final PrepareOrderByAGVController prepareOrderByAGVController = new PrepareOrderByAGVController();

    @Override
    protected boolean doShow() {

        // try to starts communication

        int option;
        List<AgvDto> agvs = null;
        String agvID;



        boolean invalidOrderID, agvInvalid;
        long orderID;
        do{

            try {
                if(prepareOrderByAGVController.startsCommunication()) {
                    System.out.println("INFO: Connection established");
                }else {
                    System.out.println("ERROR: Server Unavailable, check the AGV Manager server and try again");
                    return false;
                }
            } catch (IOException e) {
                System.out.println("ERROR: Server Unavailable, check the AGV Manager server and try again");
                return false;
            }

            // get order from database

            List<OrderDTO> orderList = prepareOrderByAGVController.getOrders();
            if(orderList.isEmpty()){
                System.out.println("\nINFO: There are no orders needing preparation.");
                return false;
            }

            // gets AGVs

            try {
                agvs = prepareOrderByAGVController.getAGVS();
                if(agvs.isEmpty()){
                    System.out.println("INFO: No AGVs available");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // verify if exist AGV´s

            if(agvs == null){
                System.out.println("Não existem AGV´s disponíveis.");
                return false;
            }

            // while order id is invalid

            do{
                // list orders

                OrderPrinter.print(orderList);

                orderID = Console.readLong("Order ID: ");
                invalidOrderID = !prepareOrderByAGVController.checkIfOrderIsValid(orderID);
            } while (invalidOrderID);

            // while AGV ID is invalid

            agvInvalid = false;
            do {

                //list agvs
                listAgvs(agvs);

                agvID = Console.readLine("AGV ID: ");
                if (!checkIfAgvIsValid(agvID, agvs)) {
                    agvInvalid = true;
                }
            } while (agvInvalid);

            // try to send taskassign message

            try {
                if (prepareOrderByAGVController.send(orderID, agvID)) {
                    System.out.println("Order with ID: " + orderID + " is being prepared by the AGV with ID: " + agvID + ".");
                } else {
                    System.out.println("The AGV selected could not prepare the order, please try with another one.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(prepareOrderByAGVController.disconnect())
                    System.out.println("INFO: Server disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("\nPretend to prepare another Order?");
            System.out.println("1 - Yes\n2 - No\n");
            option = Console.readOption(1,2,2 );
        } while(option == 1);



        return true;
    }

    private void listAgvs(List<AgvDto> agvs) {
        System.out.print("\n-----------");
        System.out.print("| AGV's |");
        System.out.print("-----------\n\n");
        for (AgvDto s : agvs) {
            System.out.printf("AGV ID: %s, Max Weight: %.1f kg, Max Volume: %.2f m3\n", s.getId(), s.getMaxWeight().quantity(), s.getMaxVolume().quantity());
        }
        System.out.println();
    }

    private boolean checkIfAgvIsValid(String agvID, List<AgvDto> agvs){
        for (AgvDto dto : agvs)
            if(dto.getId().equals(agvID)) return true;
        return false;
    }

    @Override
    public String headline() {
        return "Prepare an Order by an AGV.";
    }
}
