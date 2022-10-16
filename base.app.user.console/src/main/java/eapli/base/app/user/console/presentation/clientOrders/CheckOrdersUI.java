package eapli.base.app.user.console.presentation.clientOrders;

import eapli.base.usermanagement.application.ClientOrderController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckOrdersUI extends AbstractUI {

    ClientOrderController controller=new ClientOrderController();
    @Override
    protected boolean doShow() {
        String orders = null;
        try {
            controller.startsCommunication();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        String email=controller.getEmail();

        try {
            controller.sendEmail(email);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        System.out.println("DATA  - PRICE - STATE ");
        try {
            orders = controller.getClientOrdersStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(orders.isEmpty()){
            System.out.println("User has no orders");
            return true;
        }else{
        List<String> orderList = List.of(orders.split("#|;"));
        List<String> orderIdList = new ArrayList<>();
        int j=1;
        for (int i=0; i< orderList.size();i++){
            orderIdList.add(orderList.get(i));
            i++;
            System.out.println(j +"- "+orderList.get(i).replace("DateTime{","").replace("}","")+" ");
            j++;
        }
        int option = Console.readInteger("Want to see any order in more detail? \n 1-YES 2- NO");
        if(option==1){
            int orderOption = Console.readInteger("Which order?");
            String orderProducts= null;
            try {
                orderProducts = controller.getOrderProducts(orderIdList.get(orderOption-1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(orderIdList.get(orderOption-1));
            try {
                orderProducts = controller.getOrderProducts(orderIdList.get(orderOption-1)+";");
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> products = List.of(orderProducts.split(";"));
            for(int i=0; i< products.size();i++){
                System.out.println("PRODUCT -  QUANTITY - PRICE");
                System.out.println(products.get(i));
            }
        }}
        return false;
    }

    @Override
    public String headline() {
        return "OPEN ORDERS:";
    }
}
