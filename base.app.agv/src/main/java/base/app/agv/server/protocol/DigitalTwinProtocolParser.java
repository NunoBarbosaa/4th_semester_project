package base.app.agv.server.protocol;

import base.app.agv.server.protocol.request.AgvAssignRequest;
import eapli.base.infrastructure.spomsp.*;
import eapli.base.infrastructure.spomsp.commonrequest.*;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.framework.csv.util.CsvLineMarshaler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class DigitalTwinProtocolParser {
    private static final Logger LOG = LogManager.getLogger(DigitalTwinProtocolParser.class);

    private static AssignOrderToAgvController controller = new AssignOrderToAgvController();

    public static ProtocolV1Request parseRequest(String line) {
        ProtocolV1Request request = new UnknownRequest(line);

        String[] parsedString;

        try {
            parsedString = CsvLineMarshaler.tokenize(line).toArray(new String[0]);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.COMM_TEST_CODE)
                request = parseCommTestRequest(line, parsedString);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.DISCONN_CODE)
                request = parseDisconnectRequest(line, parsedString);
            if (Integer.parseInt(parsedString[0]) == CommProtocol.PROTOCOL_V1 && Integer.parseInt(parsedString[1]) == CommProtocol.ASSIGNAGV_CODE)
                request = parseAssignAgvRequest(line, parsedString);
        } catch (ParseException e) {
            LOG.info("Unable to parse request: {}", line);
            request = new ErrorInRequest(line, "Unable to parse request");
        }
        return request;
    }

    private static ProtocolV1Request parseAssignAgvRequest(String line, String[] parsedString) {
        System.out.println(new Date() + " INFO: Received AGV assignment Request");
        if (parsedString.length != 6)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if (isStringParam(parsedString[4]))
            return new ErrorInRequest(line, "Order id should be a number");
        if (!isStringParam(parsedString[5]))
            return new ErrorInRequest(line, "Agv id should be an alphanumeric code");
        return new AgvAssignRequest(controller, line);
    }

    private static ProtocolV1Request parseCommTestRequest(String line, String[] parsedString) {
        System.out.println(new Date() + " INFO: Received CommTest Request");
        if (parsedString.length != 4)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if (!Objects.equals(parsedString[2], "0") || !Objects.equals(parsedString[3], "0"))
            return new ErrorInRequest(line, "Message should have data size 0");
        return new AcknowledgeRequest(controller, line);
    }

    private static ProtocolV1Request parseDisconnectRequest(String line, String[] parsedString) {
        System.out.println(new Date() + " INFO: Received Disconnect Request");
        if (parsedString.length != 4)
            return new ErrorInRequest(line, "Number of parameters different than expected");
        if (!Objects.equals(parsedString[2], "0") || !Objects.equals(parsedString[3], "0"))
            return new ErrorInRequest(line, "Message should have data size 0");
        return new DisconnectRequest(controller, line);
    }

    private static boolean isStringParam(final String string) {
        return string.length() >= 2 && string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"';
    }

}
