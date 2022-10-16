package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.ordermanagement.dto.OrderDTO;
import eapli.base.warehouse.application.PrepareOrderByAGVController;
import eapli.base.warehouse.domain.Agv;
import eapli.base.warehouse.dto.AgvDto;
import eapli.framework.io.util.Console;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SchedulerPrepareOrderByAGV extends TimerTask{

    private final PrepareOrderByAGVController prepareOrderByAGVController = new PrepareOrderByAGVController();
    @Override
    public void run() {
        System.out.println("Comecou");
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("ReportAssignmentOfOrders.txt",true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // try to starts communication

        try {
            prepareOrderByAGVController.startsCommunication();
            System.out.println("Comecou comunicacao");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get order from database

        List<OrderDTO> orderList = prepareOrderByAGVController.getOrders();
        if(orderList==null||orderList.size()==0){
            try {
                System.out.println("Sem ordens");
                assert myWriter != null;
                String date=getDateNow();
                myWriter.write(date+": There are no orders available!\n");
                myWriter.close();
                return;
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        System.out.println("Com ordens");

        for(int i=0;i<orderList.size();i++){
            System.out.printf("Order %s\n",orderList.get(i).date);
        }

        List<AgvDto> agvs = null;
        String[] agvsDivided;

            try {
                agvs = prepareOrderByAGVController.getAGVS();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(agvs == null){
                assert myWriter != null;
                try {
                    String date=getDateNow();
                    System.out.println("Sen AGVS");
                    myWriter.write(date+": There are no orders available!\n");
                    myWriter.close();
                    return;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
            System.out.println("Com AGVS");
            assert agvs != null;
            //agvsDivided = agvs.split(";");

            List<String> agvsList=new ArrayList<>();
            //agvsList.addAll(Arrays.asList(agvsDivided));
            boolean assigned=false;


            assert myWriter != null;
            for(int i=0;i<orderList.size();i++){
                System.out.printf("Order %d",orderList.get(i).id);
                for(int k=0;k<agvsList.size();k++){
                    try {
                        if(prepareOrderByAGVController.send(orderList.get(i).id,agvsList.get(k))){

                            assigned=true;
                            myWriter.write(getDateNow()+": Product Order with id "+orderList.get(i).id+" is being prepared by AGV "+agvsList.get(k)+"\n");
                            System.out.printf("Order %d assigned to AGV %s\n",orderList.get(i).id,agvsList.get(k));
                            Thread.sleep(5000);
                            break;
                        }
                    } catch (IOException | InterruptedException ioException) {
                        ioException.printStackTrace();
                    }
                }
                if(!assigned){
                    try {
                        myWriter.write(getDateNow()+": Product Order with id "+orderList.get(i).id+" couldn't be assigned to any AGV\n");
                        System.out.printf("Order %d couldnt be assigned by any agv\n",orderList.get(i).id);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                assigned=false;

            }

        try {
            prepareOrderByAGVController.disconnect();
            System.out.println("Disconnected");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ACABOU COM SUCESSO!!!!");

    }

    private String getDateNow(){
        Date now=new Date();
        return now.toString();
    }


}
