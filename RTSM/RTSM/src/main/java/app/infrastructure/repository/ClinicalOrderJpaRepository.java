package app.infrastructure.repository;

import app.infrastructure.entity.ClinicalOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicalOrderJpaRepository extends JpaRepository<ClinicalOrderEntity, Long> {
    List<ClinicalOrderEntity> findByPatientDocument(Long patientDocument);
}
