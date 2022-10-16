package eapli.base.infrastructure.warehouse;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.warehouse.domain.Agv;
import eapli.base.warehouse.repositories.AgvRepository;

import java.util.HashMap;
import java.util.Map;

public final class AgvData {

    private static final AgvRepository repo = PersistenceContext.repositories().agvs();
    private static Map<Integer, Agv> agvs;


    private AgvData() {
        // ensure utility
    }

    public static Map<Integer, Agv> activeAgvs() {
        if(agvs == null){
            agvs = new HashMap<>();
            for (Agv agv:repo.findAllAgv()) {
                agvs.put(Integer.parseInt(agv.agvId().substring(agv.agvId().length() - 1)), agv);
            }
        }
        return agvs;
    }
}
