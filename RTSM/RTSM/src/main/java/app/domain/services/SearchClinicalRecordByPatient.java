package app.domain.services;

import app.domain.model.ClinicalRecord;
import app.domain.ports.ClinicalRecordPort;

import java.util.List;

public class SearchClinicalRecordByPatient {

    private ClinicalRecordPort clinicalRecordPort;

    public SearchClinicalRecordByPatient(ClinicalRecordPort clinicalRecordPort) {
        this.clinicalRecordPort = clinicalRecordPort;
    }

    public List<ClinicalRecord> searchByDocument(Long patientDocument) throws Exception {
        if (patientDocument == null) {
            throw new IllegalArgumentException("El documento del paciente no puede ser nulo para la búsqueda de registros.");
        }
        return clinicalRecordPort.findByPatientDocument(patientDocument);
    }

    public ClinicalRecord searchByDocumentAndDate(Long patientDocument, java.sql.Date attentionDate) throws Exception {
        if (patientDocument == null || attentionDate == null) {
            throw new IllegalArgumentException("El documento del paciente y la fecha de atención no pueden ser nulos.");
        }
        return clinicalRecordPort.findByPatientDocumentAndDate(patientDocument, attentionDate);
    }
}
