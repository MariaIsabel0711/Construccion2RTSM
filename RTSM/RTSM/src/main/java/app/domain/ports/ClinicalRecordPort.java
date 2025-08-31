package app.domain.ports;

import app.domain.model.ClinicalRecord;
import java.util.List;

public interface ClinicalRecordPort {
    void save(ClinicalRecord record) throws Exception;
    ClinicalRecord findByPatientDocumentAndDate(Long patientDocument, java.sql.Date attentionDate);
    List<ClinicalRecord> findByPatientDocument(Long patientDocument);
    void delete(ClinicalRecord record) throws Exception;
}
