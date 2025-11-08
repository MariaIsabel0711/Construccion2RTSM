package app.adapter.in.builder;

import app.domain.model.ClinicalRecord;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class ClinicalRecordBuilder {


    public ClinicalRecord build(Long patientDocument,
                                Date attentionDate,
                                Long medicalDocument,
                                String motivoConsulta,
                                String sintomas,
                                String diagnostico) {

        Map<String, Object> details = new HashMap<>();
        details.put("medicalDocument", medicalDocument);
        details.put("motivoConsulta", motivoConsulta);
        details.put("sintomas", sintomas);
        details.put("diagnostico", diagnostico);

        ClinicalRecord record = new ClinicalRecord();
        record.setPatientDocument(patientDocument);
        record.setAttentionDate(attentionDate);
        record.setRecordDetails(details);
        return record;
    }

    
    public ClinicalRecord buildWithDetails(Long patientDocument,
                                           Date attentionDate,
                                           Map<String, Object> recordDetails) {
        ClinicalRecord record = new ClinicalRecord();
        record.setPatientDocument(patientDocument);
        record.setAttentionDate(attentionDate);
        record.setRecordDetails(recordDetails != null ? recordDetails : new HashMap<>());
        return record;
    }


    public ClinicalRecord applyDetails(ClinicalRecord record,
                                       Long medicalDocument,
                                       String motivoConsulta,
                                       String sintomas,
                                       String diagnostico) {
        if (record.getRecordDetails() == null) {
            record.setRecordDetails(new HashMap<>());
        }
        Map<String, Object> details = record.getRecordDetails();
        if (medicalDocument != null) details.put("medicalDocument", medicalDocument);
        if (motivoConsulta != null) details.put("motivoConsulta", motivoConsulta);
        if (sintomas != null) details.put("sintomas", sintomas);
        if (diagnostico != null) details.put("diagnostico", diagnostico);
        return record;
    }
}
