package base.app.agv.server.protocol.request;

import eapli.base.Application;
import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.infrastructure.spomsp.commonrequest.DisconnectRequest;
import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.framework.csv.util.CsvLineMarshaler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class AgvAssignRequest extends ProtocolV1Request {

    private Socket statusSocket;
    public AgvAssignRequest(AssignOrderToAgvController controller, String inputRequest) {
        super(controller, inputRequest);
    }

    @Override
    public String execute() {
        try {
            statusSocket = new Socket(Application.settings().getProperty("agvmanager.address"), Integer.parseInt(Application.settings().getProperty("agvmanager.port")));

        }catch (IOException e){
            System.out.println(new Date() + " ERROR: " + e.getMessage());
        }

        try(PrintWriter out = new PrintWriter(statusSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(statusSocket.getInputStream()))){
            List<String> parsedRequest = CsvLineMarshaler.tokenize(super.request);
            int[] size = CommProtocol.dataSizeCalculator(parsedRequest.get(5) + ",\"OCCUPIED\"");
            StringBuilder response = new StringBuilder();

            response.append("1,6,").append(size[0]).append(",").append(size[1]).append(",");
            response.append(parsedRequest.get(5)).append(",\"OCCUPIED\"");
            out.println(response);
            if(in.readLine().equals("1,2,0,0")) {

                Thread.sleep(60 * 1000);

                response = new StringBuilder();
                size = CommProtocol.dataSizeCalculator(parsedRequest.get(5) + ",\"AVAILABLE\"");
                response.append("1,6,").append(size[0]).append(",").append(size[1]).append(",");
                response.append(parsedRequest.get(5)).append(",\"AVAILABLE\"");
                out.println(response);

                if(in.readLine().equals("1,2,0,0")) {

                    response = new StringBuilder();
                    size = CommProtocol.dataSizeCalculator(parsedRequest.get(4) + ",\"READY_TO_BE_PACKED\"");
                    response.append("1,7,").append(size[0]).append(",").append(size[1]).append(",");
                    response.append(parsedRequest.get(4)).append(",\"READY_TO_BE_PACKED\"");
                    out.println(response);

                    if(in.readLine().equals("1,2,0,0")) {


                        out.println("1,1,0,0");

                        if (in.readLine().equals("1,2,0,0"))
                            statusSocket.close();
                    }
                }
            }else{
                System.out.println(new Date() + "INFO: Operation Aborted");
                String disconnectRequest = new DisconnectRequest(controller, "").execute();

                out.println(disconnectRequest);

                if (in.readLine().equals("1,2,0,0"))
                    statusSocket.close();
            }
        }catch (IOException | ParseException | InterruptedException e){
            System.out.println(new Date() + " ERROR: " + e.getMessage());
        }
        return "";
    }
}
