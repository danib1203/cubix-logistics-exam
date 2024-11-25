package hu.cubix.logistics.repository;

import hu.cubix.logistics.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
