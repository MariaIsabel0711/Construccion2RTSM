package app.adapter.in.builder;

import app.domain.model.ProcedireRecord;
import org.springframework.stereotype.Component;

@Component
public class ProcedureRecordBuilder {
    public ProcedireRecord build(int itemNumber, String procedureName, int repetitions, String frequency,
                                 double cost, boolean requiresSpecialist, Long specialistRoleId) {
        ProcedireRecord p = new ProcedireRecord();
        p.setItemNumber(itemNumber);
        p.setProcedureName(procedureName);
        p.setRepetitions(repetitions);
        p.setFrequency(frequency);
        p.setCost(cost);
        p.setRequiresSpecialist(requiresSpecialist);
        if (requiresSpecialist) {
            p.setSpecialistRoleId(specialistRoleId);
        }
        return p;
    }
}
