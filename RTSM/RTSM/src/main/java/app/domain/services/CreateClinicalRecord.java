package app.domain.services;

import app.domain.model.ClinicalRecord;
import app.domain.ports.ClinicalRecordPort;

public class CreateClinicalRecord {

    private ClinicalRecordPort clinicalRecordPort;

   
    public CreateClinicalRecord(ClinicalRecordPort clinicalRecordPort) {
        this.clinicalRecordPort = clinicalRecordPort;
    }

    //Crea y guarda un nuevo registro clínico.
  
    public void create(ClinicalRecord clinicalRecord) throws Exception {
        clinicalRecordPort.save(clinicalRecord);
    }
}
