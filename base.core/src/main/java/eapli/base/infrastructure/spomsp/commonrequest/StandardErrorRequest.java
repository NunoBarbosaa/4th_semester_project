package eapli.base.infrastructure.spomsp.commonrequest;

import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.framework.csv.CsvRecord;

public abstract class StandardErrorRequest extends ProtocolV1Request {


    private final String errorDescription;

    protected StandardErrorRequest(final String request, final String errorDescription) {
        super(null, request);
        this.errorDescription = errorDescription;
    }

    protected StandardErrorRequest(final String request) {
        super(null, request);
        this.errorDescription = null;
    }

    @Override
    public String execute() {
        return buildResponse();
    }

    protected String buildResponse() {
        int[] size;
        if(errorDescription != null) {
            size = CommProtocol.dataSizeCalculator("\"" + errorDescription + "\"");
            return "1,10," + size[0] + "," + size[1] + "," + "\"" + errorDescription + "\"";
        }else {
            return "1,11,0,0";
        }
    }
}
