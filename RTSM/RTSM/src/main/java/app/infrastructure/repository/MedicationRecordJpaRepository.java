package app.infrastructure.repository;

import app.infrastructure.entity.MedicationRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRecordJpaRepository extends JpaRepository<MedicationRecordEntity, Long> {
}
