package base.app.agvmanager.server.protocol.request;

import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.framework.csv.util.CsvLineMarshaler;

import java.text.ParseException;
import java.util.List;

public class OrderStatusUpdateRequest extends ProtocolV1Request {
    public OrderStatusUpdateRequest(AssignOrderToAgvController controller, String line) {
        super(controller, line);
    }

    @Override
    public String execute() {
        try {
            List<String> tokens = CsvLineMarshaler.tokenize(super.request);
            if(controller.updateOrderStatus(Long.parseLong(tokens.get(4)), CsvLineMarshaler.unquote(tokens.get(5))))
                return "1,2,0,0";
            return "1,10,0,0";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
