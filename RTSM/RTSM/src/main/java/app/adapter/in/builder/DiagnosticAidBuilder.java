package app.adapter.in.builder;

import app.domain.model.DiagnosticRecord;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticAidBuilder {
    public DiagnosticRecord build(String name, int quantity, double cost) {
        DiagnosticRecord d = new DiagnosticRecord();
        d.setDiagnosticName(name);
        d.setQuantity(quantity);
        d.setCost(cost);
        return d;
    }

    public DiagnosticRecord applyUpdates(DiagnosticRecord d, String name, double cost) {
        if (name != null && !name.isEmpty()) d.setDiagnosticName(name);
        if (cost > 0) d.setCost(cost);
        return d;
    }
}
