package base.app.agvmanager.server.protocol.request;

import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.framework.csv.util.CsvLineMarshaler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TaskAssignToAgvRequest extends ProtocolV1Request {
    private final String []splittedLine;
    public TaskAssignToAgvRequest(AssignOrderToAgvController controller, String line, String [] splittedLine) {
        super(controller, line);
        this.splittedLine = splittedLine;
    }

    @Override
    public String execute() {
        if(controller.prepareOrder(CsvLineMarshaler.unquote(splittedLine[5]), Long.parseLong(splittedLine[4])))
            return buildResponse(1);
        return buildResponse(2);
    }

    private String buildResponse(int type) {
        if(type == 1)
            return "1,2,0,0";
        else
            return "1,10,0,0";
    }
}
