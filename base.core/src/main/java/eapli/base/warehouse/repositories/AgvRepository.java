package eapli.base.warehouse.repositories;

import eapli.base.warehouse.domain.Agv;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface AgvRepository extends DomainRepository<String, Agv> {

    public Iterable<Agv> findAllAgv();

    public Iterable<Agv> findAvailableAgvs();

    public Optional<Agv> findByAgvId(String id);

}
