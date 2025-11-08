package app.adapter.in.builder;

import app.domain.model.ClinicalOrder;
import org.springframework.stereotype.Component;
import java.sql.Date;
@Component
public class ClinicalOrderBuilder {
    public ClinicalOrder build(long orderNumber, Long patientDocument, Long medicalDocument, Date creationDate) {
        ClinicalOrder o = new ClinicalOrder();
        o.setOrderNumber(orderNumber);
        o.setPatientDocument(patientDocument);
        o.setMedicalDocument(medicalDocument);
        o.setCreationDate(creationDate);
        return o;
    }
}