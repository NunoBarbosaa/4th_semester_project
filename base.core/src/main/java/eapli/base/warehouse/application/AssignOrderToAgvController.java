package eapli.base.warehouse.application;

import eapli.base.Application;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.infrastructure.warehouse.AgvData;
import eapli.base.infrastructure.warehouse.WarehouseContainer;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.OrderStatus;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.warehouse.domain.*;
import eapli.base.warehouse.repositories.AgvRepository;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AssignOrderToAgvController {

    private final AgvRepository repo = PersistenceContext.repositories().agvs();
    private final OrderRepository orderRepo = PersistenceContext.repositories().orders();

    public Iterable<Agv> getAvailableAGVs() {
        return repo.findAvailableAgvs();
    }

    public void updateAgvStatus(List<String> parsedString) {
        Map<Integer, Agv> map = AgvData.activeAgvs();
        int agvId = Integer.parseInt(parsedString.get(4));
        int battery = Integer.parseInt(parsedString.get(8));
        int xPos = Integer.parseInt(parsedString.get(5));
        int yPos = Integer.parseInt(parsedString.get(6));
        int status = Integer.parseInt(parsedString.get(7));
        map.get(agvId).changePosition(new Square(xPos, yPos));
        map.get(agvId).updateBatteryCharge(battery);
        map.get(agvId).changeStatusTo(interpretStatus(status));
    }

    private AgvStatus interpretStatus(int status) {
        if(status == 1)
            return AgvStatus.AVAILABLE;
        if(status == 2)
            return AgvStatus.OCCUPIED;
        if(status == 3)
            return AgvStatus.CHARGING;
        else
            return AgvStatus.IN_MAINTENANCE;
    }

    public boolean updateOrderStatus(Long id, String newStatus){
        Order order = orderRepo.findByOrderId(id);
        OrderStatus orderStatus = OrderStatus.WAITING_FOR_AGV;
        if(newStatus.equals("IN_PREPARATION"))
            orderStatus = OrderStatus.IN_PREPARATION;
        if(newStatus.equals("READY_TO_BE_PACKED"))
            orderStatus = OrderStatus.READY_TO_BE_PACKED;

        if(order != null){
            order.changeStatus(new Date(), orderStatus);
            return orderRepo.save(order) != null;
        }
        return false;
    }

    public boolean checkAgvCanProcessOrder(String agvId, long orderId){
        Optional<Agv> agv = repo.findByAgvId(agvId);
        Order order = orderRepo.findByOrderId(orderId);

        if(agv.isPresent() && order != null){
            return checkAgvCanProcessOrder(agv.get(), order);
        }
        return false;
    }
    public boolean checkAgvCanProcessOrder(Agv agv, Order order){
        return agv.maxWeight().quantity() >= order.totalWeight() && agv.maxVolume().quantity() >= order.totalVolume();
    }

    public Iterable<Order> ordersWaitingForAgv(){
        return orderRepo.findUnpreparedOrders();
    }

    public boolean prepareOrder(String agvId, Long orderUnpreparedId) {
        Optional<Agv> agv = repo.findByAgvId(agvId);
        Order order = orderRepo.findByOrderId(orderUnpreparedId);

        if(agv.isPresent() && order != null){
            return prepareOrder(agv.get(), order);
        }
        return false;
    }

    public boolean prepareOrder(Agv agv, Order orderUnprepared) {
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        StringBuilder sb = new StringBuilder();
        try {
            SSLSocket sock = (SSLSocket) sf.createSocket(Application.settings().getProperty("agvdigitaltwin.address"), Integer.parseInt(Application.settings().getProperty("agvdigitaltwin.port")));

            //Socket sock = new Socket(Application.settings().getProperty("agvdigitaltwin.address"), Integer.parseInt(Application.settings().getProperty("agvdigitaltwin.port")));
            sock.startHandshake();

            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            sb.append(orderUnprepared.identity()).append(",").append("\"").append(agv.agvId()).append("\"");

            StringBuilder response = new StringBuilder();
            int []size = CommProtocol.dataSizeCalculator(sb.toString());
            response.append("1,").append(CommProtocol.ASSIGNAGV_CODE).append(",").append(size[0]).append(",").append(size[1]).append(",");
            response.append(sb);
            out.println(response);
            if(updateOrderStatus(Long.parseLong(String.valueOf(orderUnprepared.identity())), "IN_PREPARATION")) {
                System.out.println(new Date() + " INFO: Order Status updated to \"IN_PREPARATION\"");
                return true;
            }

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
        return false;
    }

    public String prepareWarehousePlant() {
        Warehouse warehouse = WarehouseContainer.activeWarehouse();
        Iterable<Agv> agvs = repo.findAllAgv();
        int length = (int) (warehouse.length().quantity() / warehouse.squareDimension().quantity());
        int width = (int) (warehouse.width().quantity() / warehouse.squareDimension().quantity());

        int [][]grid = new int[width][length];

        for(AgvDock dock : warehouse.docks()){
            int wPos = dock.startsFrom().wSquare() - 1;
            int lPos = dock.startsFrom().lSquare() - 1;
            int agvId = 0;
            for(Agv agv : agvs) {
                if (agv.agvDockId().equalsIgnoreCase(dock.identity())) {
                    agvId = Integer.parseInt(agv.agvId().substring(agv.agvId().length() - 1));
                    break;
                }
            }
            if(agvId != 0){
                grid[wPos][lPos] = agvId;
            }
        }
        for(Aisle aisle : warehouse.getAisles()){
            Square start = aisle.startsFrom();
            Square end = aisle.endsOn();
            Square depth = aisle.extendsTo();
            for (int i = start.lSquare()-1; i <= end.lSquare()-1; i++) {
                if(start.wSquare() <= depth.wSquare()) {
                    for (int j = start.wSquare() - 1; j <= depth.wSquare() - 1; j++)
                        grid[j][i] = -1;
                }else{
                    for (int j = depth.wSquare() - 1; j <= start.wSquare() - 1; j++)
                        grid[j][i] = -1;
                }
            }
        }
        return turnWarehousePlantToMessage(grid);
    }

    String turnWarehousePlantToMessage(int [][] grid){
        StringBuilder message = new StringBuilder();
        message.append(grid.length).append(",").append(grid[0].length);
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                message.append(",").append(ints[j]);
            }
        }
        return message.toString();
    }
}
