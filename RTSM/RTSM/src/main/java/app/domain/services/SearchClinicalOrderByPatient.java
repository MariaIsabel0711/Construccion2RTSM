package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.Patient;
import app.domain.ports.ClinicalOrderPort;

import java.util.List;

public class SearchClinicalOrderByPatient {

    private ClinicalOrderPort clinicalOrderPort;

    public SearchClinicalOrderByPatient(ClinicalOrderPort clinicalOrderPort) {
        this.clinicalOrderPort = clinicalOrderPort;
    }

 
    public List<ClinicalOrder> search(Patient patient) throws Exception {
        if (patient == null) {
            throw new IllegalArgumentException();
        }
        if (patient.getDocument() == null) {
            throw new IllegalArgumentException();
        }
        
        return clinicalOrderPort.findByPatientDocument(patient.getDocument());
    }


    public List<ClinicalOrder> searchByDocument(Long patientDocument) throws Exception {
        if (patientDocument == null) {
            throw new IllegalArgumentException();
        }
        
        return clinicalOrderPort.findByPatientDocument(patientDocument);
    }
}
