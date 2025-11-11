package app.infrastructure.repository;

import app.infrastructure.entity.ClinicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ClinicalRecordJpaRepository extends JpaRepository<ClinicalRecordEntity, Long> {

    List<ClinicalRecordEntity> findByPatientDocumentAndAttentionDate(Long patientDocument, Date attentionDate);

    List<ClinicalRecordEntity> findByPatientDocument(Long patientDocument);

    void deleteByPatientDocumentAndAttentionDate(Long patientDocument, Date attentionDate);
}
