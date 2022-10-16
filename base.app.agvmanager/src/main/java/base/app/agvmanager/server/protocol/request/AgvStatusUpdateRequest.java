package base.app.agvmanager.server.protocol.request;

import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.framework.csv.util.CsvLineMarshaler;

import java.text.ParseException;
import java.util.List;

public class AgvStatusUpdateRequest extends ProtocolV1Request {
    public AgvStatusUpdateRequest(AssignOrderToAgvController controller, String line) {
        super(controller, line);
    }

    @Override
    public String execute() {
        try {
            List<String> parsedLine = CsvLineMarshaler.tokenize(super.request);
            controller.updateAgvStatus(parsedLine);
            return buildReply();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildReply() {
        return "";
    }
}
