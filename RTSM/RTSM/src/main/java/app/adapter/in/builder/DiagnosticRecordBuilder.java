package app.adapter.in.builder;

import app.domain.model.DiagnosticRecord;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticRecordBuilder {
    public DiagnosticRecord build(int itemNumber, String diagnosticName, int quantity, double cost,
                                  boolean requiresSpecialist, Long specialistRoleId) {
        DiagnosticRecord d = new DiagnosticRecord();
        d.setItemNumber(itemNumber);
        d.setDiagnosticName(diagnosticName);
        d.setQuantity(quantity);
        d.setCost(cost);
        d.setRequiresSpecialist(requiresSpecialist);
        if (requiresSpecialist) {
            d.setSpecialistRoleId(specialistRoleId);
        }
        return d;
    }
}
