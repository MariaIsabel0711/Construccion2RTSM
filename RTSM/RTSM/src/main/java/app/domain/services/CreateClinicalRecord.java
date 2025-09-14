package app.domain.services;

import app.domain.model.ClinicalRecord;
import app.domain.ports.ClinicalRecordPort;

public class CreateClinicalRecord {

    private ClinicalRecordPort clinicalRecordPort;

    public CreateClinicalRecord(ClinicalRecordPort clinicalRecordPort) {
        this.clinicalRecordPort = clinicalRecordPort;
    }

    public void create(ClinicalRecord clinicalRecord) throws Exception {
        if (clinicalRecord.getPatientDocument() == null) {
            throw new Exception("El documento del paciente es obligatorio para el registro clínico.");
        }
        if (clinicalRecord.getAttentionDate() == null) {
            throw new Exception("La fecha de atención es obligatoria para el registro clínico.");
        }
        clinicalRecordPort.save(clinicalRecord);
    }
}
    