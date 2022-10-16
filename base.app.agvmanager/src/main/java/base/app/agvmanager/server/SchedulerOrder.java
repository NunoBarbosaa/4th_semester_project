package base.app.agvmanager.server;

import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.base.warehouse.application.services.AgvOrderAssignmentService;
import eapli.base.warehouse.domain.Agv;
import eapli.base.warehouse.dto.AgvDto;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SchedulerOrder extends TimerTask {
    private final AssignOrderToAgvController controller = new AssignOrderToAgvController();

    @Override
    public void run() {
        FileWriter myWriter;
        try {
            myWriter = new FileWriter("ReportAssignmentOfOrders.txt", true);
        } catch (IOException ioException) {
            System.out.println(new Date() + " INFO: Could not open log file.");
            return;
        }

        // get order from database

        Iterable<Order> orderList = controller.ordersWaitingForAgv();


        if (orderList == null || !orderList.iterator().hasNext()) {
            try {
                System.out.println(new Date() + " INFO: There are no orders available");
                myWriter.write(getDate() + ": There are no orders available!\n");
                myWriter.close();
                return;
            } catch (IOException e) {
                System.out.println(new Date() + " INFO: An error occurred when trying to write to the log file");
                e.printStackTrace();
                return;
            }
        }


//        for (Order order : orderList) {
//            System.out.printf("Order %s\n", order.identity());
//        }


        for (Order orderUnprepared : orderList) {
//            System.out.printf("Order %d", orderUnprepared.identity());

            Iterable<Agv> agvs = controller.getAvailableAGVs();


            if (agvs == null || !agvs.iterator().hasNext()) {
                try {
                    System.out.println(new Date() + " INFO: No AGVs Available to prepare orders. Trying again later");
                    myWriter.write(getDate() + ": There are no AGVs available!\n");
                    myWriter.close();
                    return;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    return;
                }

            }

            boolean assigned = false;

            if (agvs.iterator().hasNext()) {

                for (Agv agv : agvs) {

                    try {
                        if (controller.checkAgvCanProcessOrder(agv, orderUnprepared) && controller.prepareOrder(agv, orderUnprepared)) {
                            assigned = true;
                            myWriter.write(getDate() + ": Product Order with id " + orderUnprepared.identity() + " is being prepared by AGV " + agv.agvId() + "\n");
                            System.out.printf(new Date() + " INFO: Order %d assigned to AGV %s\n", orderUnprepared.identity(), agv.agvId());
                            break;
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            if (!assigned) {
                try {
                    myWriter.write(getDate() + ": Product Order with id " + orderUnprepared.identity() + " couldn't be assigned to any AGV\n");
                    System.out.printf(new Date() + " INFO: Order %d could not be assigned by any AGV\n", orderUnprepared.identity());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    return;
                }
            }
            try {
                Thread.sleep(1000 * 15);
            } catch (InterruptedException e) {
                System.out.println(new Date() + " ERROR: Failure when waiting");
                return;
            }

        }
        try {
            myWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        System.out.println(new Date() + " INFO: All orders were assigned to an AGV");

    }

    private String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

}
