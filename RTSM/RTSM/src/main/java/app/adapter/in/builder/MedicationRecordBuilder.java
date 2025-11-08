package app.adapter.in.builder;

import app.domain.model.Medicationrecord;
import org.springframework.stereotype.Component;

@Component
public class MedicationRecordBuilder {
    public Medicationrecord build(int itemNumber, String medicationName, String dose, String duration, double cost) {
        Medicationrecord m = new Medicationrecord();
        m.setItemNumber(itemNumber);
        m.setMedicationName(medicationName);
        m.setDose(dose);
        m.setTreatmentDuration(duration);
        m.setCost(cost);
        return m;
    }
}
