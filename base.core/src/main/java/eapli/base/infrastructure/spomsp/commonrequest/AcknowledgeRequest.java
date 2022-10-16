package eapli.base.infrastructure.spomsp.commonrequest;

import eapli.base.warehouse.application.AssignOrderToAgvController;

public class AcknowledgeRequest extends ProtocolV1Request {
    public AcknowledgeRequest(AssignOrderToAgvController controller, String inputRequest) {
        super(controller, inputRequest);
    }

    @Override
    public String execute() {
        return buildResponse();
    }

    private String buildResponse() {
        return "1,2,0,0";
    }
}
