package eapli.base.infrastructure.spomsp.commonrequest;

import eapli.base.infrastructure.spomsp.CommProtocol;

public class ErrorInRequest extends StandardErrorRequest {

    public ErrorInRequest(final String request, final String errorDescription) {
        super(request, errorDescription);
    }

}
