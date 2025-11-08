package app.adapter.in.builder;

import app.domain.model.ProcedireRecord;
import org.springframework.stereotype.Component;

@Component
public class ProcedureItemBuilder {
    public ProcedireRecord build(String name, int repetitions, double cost) {
        ProcedireRecord p = new ProcedireRecord();
        p.setProcedureName(name);
        p.setRepetitions(repetitions);
        p.setCost(cost);
        return p;
    }

    public ProcedireRecord applyUpdates(ProcedireRecord p, String name, double cost) {
        if (name != null && !name.isEmpty()) p.setProcedureName(name);
        if (cost > 0) p.setCost(cost);
        return p;
    }
}
