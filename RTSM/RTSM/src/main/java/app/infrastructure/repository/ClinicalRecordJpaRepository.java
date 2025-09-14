package app.infrastructure.repository;

import app.infrastructure.entity.ClinicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ClinicalRecordJpaRepository extends JpaRepository<ClinicalRecordEntity, Long> {
    ClinicalRecordEntity findByPatientDocumentAndAttentionDate(Long patientDocument, Date attentionDate);
    List<ClinicalRecordEntity> findByPatientDocument(Long patientDocument);
}
