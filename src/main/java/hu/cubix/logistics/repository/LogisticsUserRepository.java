package hu.cubix.logistics.repository;

import hu.cubix.logistics.model.LogisticsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticsUserRepository extends JpaRepository<LogisticsUser, String> {
}
