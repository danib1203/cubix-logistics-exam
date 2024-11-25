package hu.cubix.logistics.repository;

import hu.cubix.logistics.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {
}
