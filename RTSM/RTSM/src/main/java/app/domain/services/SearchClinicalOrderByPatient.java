package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.ports.ClinicalOrderPort;

import java.util.List;

public class SearchClinicalOrderByPatient {

    private ClinicalOrderPort clinicalOrderPort;

    public SearchClinicalOrderByPatient(ClinicalOrderPort clinicalOrderPort) {
        this.clinicalOrderPort = clinicalOrderPort;
    }

    public List<ClinicalOrder> searchByDocument(Long patientDocument) throws Exception {
        if (patientDocument == null) {
            throw new IllegalArgumentException("El documento del paciente no puede ser nulo para la búsqueda de órdenes.");
        }
        return clinicalOrderPort.findByPatientDocument(patientDocument);
    }
}
    