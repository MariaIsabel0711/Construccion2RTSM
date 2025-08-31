package app.domain.services;

import app.domain.model.ClinicalRecord;
import app.domain.model.Patient;
import app.domain.ports.ClinicalRecordPort;

import java.util.List;

public class SearchClinicalRecordByPatient {

    private ClinicalRecordPort clinicalRecordPort;

    public SearchClinicalRecordByPatient(ClinicalRecordPort clinicalRecordPort) {
        this.clinicalRecordPort = clinicalRecordPort;
    }

   
    public List<ClinicalRecord> search(Patient patient) throws Exception {
        if (patient == null) {
            throw new IllegalArgumentException();
        }
        if (patient.getDocument() == null) {
            throw new IllegalArgumentException("El documento del paciente no puede ser nulo para la búsqueda de registros");
        }
        
        return clinicalRecordPort.findByPatientDocument(patient.getDocument());
    }

 
    public List<ClinicalRecord> searchByDocument(Long patientDocument) throws Exception {
        if (patientDocument == null) {
            throw new IllegalArgumentException();
        }
        
        return clinicalRecordPort.findByPatientDocument(patientDocument);
    }

     
    public ClinicalRecord searchByDocumentAndDate(Long patientDocument, java.sql.Date attentionDate) throws Exception {
        if (patientDocument == null) {
            throw new IllegalArgumentException();
        }
        if (attentionDate == null) {
            throw new IllegalArgumentException();
        }
        
        return clinicalRecordPort.findByPatientDocumentAndDate(patientDocument, attentionDate);
    }
}
