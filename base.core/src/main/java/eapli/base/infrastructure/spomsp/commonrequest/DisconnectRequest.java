package eapli.base.infrastructure.spomsp.commonrequest;

import eapli.base.warehouse.application.AssignOrderToAgvController;

public class DisconnectRequest extends ProtocolV1Request {
    public DisconnectRequest(AssignOrderToAgvController controller, String inputRequest) {
        super(controller, inputRequest);
    }

    @Override
    public String execute() {
        return buildResponse();
    }

    private String buildResponse() {
        return "1,2,0,0";
    }

    @Override
    public boolean isGoodbye() {
        return true;
    }
}
