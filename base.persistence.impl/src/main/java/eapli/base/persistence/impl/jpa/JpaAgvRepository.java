package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.warehouse.domain.Agv;
import eapli.base.warehouse.repositories.AgvRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;


import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaAgvRepository extends JpaAutoTxRepository<Agv, String, String> implements AgvRepository {
    public JpaAgvRepository(TransactionalContext tx) {
        super(tx, "idwarehouse");
    }

    public JpaAgvRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }

    @Override
    public Iterable<Agv> findAllAgv() {
        TypedQuery<Agv> query = super.createQuery(
                "SELECT c FROM Agv c", Agv.class);
        return query.getResultList();
    }

    @Override
    public Iterable<Agv> findAvailableAgvs() {
        TypedQuery<Agv> query = super.createQuery(
                "SELECT c FROM Agv c WHERE currentstatus = 'AVAILABLE'", Agv.class);
        return query.getResultList();
    }

    @Override
    public Optional<Agv> findByAgvId(String id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("AgvId", id);
        return matchOne("e.id =: AgvId", params);
    }
}
