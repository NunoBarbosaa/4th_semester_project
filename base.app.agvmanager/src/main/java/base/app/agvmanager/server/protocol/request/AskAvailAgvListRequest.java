package base.app.agvmanager.server.protocol.request;

import eapli.base.infrastructure.spomsp.CommProtocol;
import eapli.base.infrastructure.spomsp.commonrequest.ProtocolV1Request;
import eapli.base.infrastructure.warehouse.AgvData;
import eapli.base.warehouse.application.AssignOrderToAgvController;
import eapli.base.warehouse.domain.Agv;
import eapli.framework.csv.CsvRecord;

import java.util.Map;

public class AskAvailAgvListRequest extends ProtocolV1Request {
    public AskAvailAgvListRequest(AssignOrderToAgvController controller, String line) {
        super(controller, line);
    }

    @Override
    public String execute() {
        Iterable<Agv> agvs = controller.getAvailableAGVs();
        return buildResponse(agvs);
    }

    private String buildResponse(Iterable<Agv> availAgvs) {
        StringBuilder data = new StringBuilder();

        data.append("\"ID\",\"MAX_WEIGHT\",\"MAX_VOLUME\",\"W_SQUARE\",\"L_SQUARE\",\"STATUS\",\"SOC\"");
        Map<Integer, Agv> agvs = AgvData.activeAgvs();
        for(Map.Entry<Integer, Agv> agv : agvs.entrySet()){
            boolean isAvail = false;
            for(Agv availAgv : availAgvs){
                if(availAgv.agvId().equals(agv.getValue().agvId())) {
                    isAvail = true;
                    break;
                }
            }
            if(isAvail) {
                data.append("\n");
                data.append(CsvRecord.valueOf(new Object[]{agv.getValue().agvId(), agv.getValue().maxWeight().quantity(), agv.getValue().maxVolume().quantity(),
                                agv.getValue().currentPosition().wSquare(), agv.getValue().currentPosition().lSquare(), agv.getValue().currentStatus().toString(), (int)agv.getValue().batterySOC()},
                        new boolean[]{true, false, false, false, false, true, false}));
            }

        }

        int[] size = CommProtocol.dataSizeCalculator(data.toString());
        StringBuilder response = new StringBuilder();
        response.append(CommProtocol.PROTOCOL_V1 + ",").append(CommProtocol.AVAILABLE_AGV_LIST_CODE + ",");
        response.append(size[0]).append(",").append(size[1]).append(",");
        response.append(data).append("\n");

        return response.toString();

    }
}
