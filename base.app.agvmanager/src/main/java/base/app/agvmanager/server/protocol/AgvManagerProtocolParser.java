package base.app.agvmanager.server.protocol;

import base.app.agvmanager.server.protocol.request.*;
import eapli.base.infrastructure.spomsp.*;
import eapli.base.infrastructure.spomsp.commonrequest.*;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.framework.csv.util.CsvLineMarshaler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class AgvManagerProtocolParser {
    private static final Logger LOG = LogManager.getLogger(AgvManagerProtocolParser.class);

    private static final AssignOrderToAgvController controller = new AssignOrderToAgvController();


    public static ProtocolV1Request parseRequest(String line) {
        ProtocolV1Request request = new UnknownRequest(line);

        String[] parsedString;

        try {
            parsedString = CsvLineMarshaler.tokenize(line).toArray(new String[0]);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.COMM_TEST_CODE)
                request = parseCommTestRequest(line, parsedString);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.DISCONN_CODE)
                request = parseDisconnectRequest(line, parsedString);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.ASK_AVAILABLE_AGV_LIST_CODE)
                request = parseAskAvailAgvListRequest(line, parsedString);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.TASKASSIGN_CODE)
                request = parseTaskAssignToAgvRequest(line, parsedString);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.STATUSUPD_CODE)
                request = parseAGVStatusUpdateRequest(line, parsedString);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.ORDERUPD_CODE)
                request = parseOrderStatusUpdRequest(line, parsedString);
            if(Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.WAREHOUSE_REQUEST)
                request = parseWarehouseRequest(line, parsedString);
        } catch (ParseException e) {
            LOG.info("Unable to parse request: {}", line);
            request = new ErrorInRequest(line, "Unable to parse request");
        }
        return request;
    }

    private static ProtocolV1Request parseWarehouseRequest(String line, String[] parsedString) {
        System.out.println("INFO: Received request for the warehouse plant");
        if(parsedString.length != 4)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        return new WarehousePlantRequest(controller, line);
    }

    private static ProtocolV1Request parseOrderStatusUpdRequest(String line, String[] parsedString) {
        System.out.println(new Date() + " INFO: Status of order " + parsedString[4] + " was updated to " + parsedString[5]);
        if (parsedString.length != 6)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if(isStringParam(parsedString[4]) || !isStringParam(parsedString[5]))
            return new ErrorInRequest(line, "Message does not have the expected content");
        return new OrderStatusUpdateRequest(controller, line);
    }

    private static ProtocolV1Request parseAGVStatusUpdateRequest(String line, String[] parsedString) {
        System.out.println(new Date() + " INFO: Received AGV status update request from " + parsedString[4]);

        return new AgvStatusUpdateRequest(controller, line);
    }

    private static ProtocolV1Request parseCommTestRequest(String line, String[] parsedString) {
        if (parsedString.length != 4)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if (!Objects.equals(parsedString[2], "0") || !Objects.equals(parsedString[3], "0"))
            return new ErrorInRequest(line, "Message should have data size 0");
        return new AcknowledgeRequest(controller, line);
    }

    private static ProtocolV1Request parseDisconnectRequest(String line, String[] parsedString) {
        if (parsedString.length != 4)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if (!Objects.equals(parsedString[2], "0") || !Objects.equals(parsedString[3], "0"))
            return new ErrorInRequest(line, "Message should have data size 0");
        return new DisconnectRequest(controller, line);
    }

    private static ProtocolV1Request parseAskAvailAgvListRequest(String line, String[] parsedString) {
        if (parsedString.length != 4)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if (!Objects.equals(parsedString[2], "0") || !Objects.equals(parsedString[3], "0"))
            return new ErrorInRequest(line, "Message should have data size 0");
        return new AskAvailAgvListRequest(controller, line);
    }

    private static ProtocolV1Request parseTaskAssignToAgvRequest(String line, String[] parsedString) {
        if (parsedString.length != 6)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if (isStringParam(parsedString[4]))
            return new ErrorInRequest(line, "Order id should be a number");
        if (!isStringParam(parsedString[5]))
            return new ErrorInRequest(line, "Agv id should be an alphanumeric code");
        if(agvCanProcessOrder(parsedString))
            return new TaskAssignToAgvRequest(controller, line, parsedString);
        return new ErrorInRequest(line, "AGV cannot process order due to physical constraints");
    }

    private static boolean agvCanProcessOrder(String[] parsedString) {
        return controller.checkAgvCanProcessOrder(CsvLineMarshaler.unquote(parsedString[5]), Long.parseLong(parsedString[4]));
    }

    private static boolean isStringParam(final String string) {
        return string.length() >= 2 && string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"';
    }

}
