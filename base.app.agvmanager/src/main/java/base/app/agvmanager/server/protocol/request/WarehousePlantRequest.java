package base.app.agvmanager.server.protocol.request;

import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import eapli.base.warehouse.application.AssignOrderToAgvController;

public class WarehousePlantRequest extends ProtocolV1Request {

    private byte[] header;
    public WarehousePlantRequest(AssignOrderToAgvController controller, String line) {
        super(controller, line);
    }

    @Override
    public String execute() {
        String warehousePlant = controller.prepareWarehousePlant();
        int [] dataSize = CommProtocol.dataSizeCalculator(warehousePlant);
        header = new byte[]{1, 27, (byte)dataSize[0], (byte)dataSize[1]};
        return warehousePlant;
    }

    @Override
    public boolean isToDigitalTwin() {
        return true;
    }

    public byte[] header(){
        return header;
    }
}
