package eapli.base.persistence.impl.inmemory;

import eapli.base.warehouse.domain.Agv;
import eapli.base.warehouse.repositories.AgvRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryAgvRepository extends InMemoryDomainRepository<Agv, String> implements AgvRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<Agv> findAllAgv() {
        return super.findAll();
    }

    @Override
    public Iterable<Agv> findAvailableAgvs() {
        return match(e -> e.currentStatus().equals("AVAILABLE"));
    }

    @Override
    public Optional<Agv> findByAgvId(String id){
        return matchOne(e -> e.agvId().equals(id));
    }
}
