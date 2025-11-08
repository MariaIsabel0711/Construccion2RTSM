package app.adapter.in.builder;

import app.domain.model.MedicationInventory;
import org.springframework.stereotype.Component;

@Component
public class MedicationInventoryBuilder {
    public MedicationInventory build(String name, String description, int stock, double unitCost) {
        MedicationInventory m = new MedicationInventory();
        m.setName(name);
        m.setDescription(description);
        m.setStock(stock);
        m.setUnitCost(unitCost);
        return m;
    }

    public MedicationInventory applyUpdates(MedicationInventory m, String name, String description, double unitCost) {
        if (name != null && !name.isEmpty()) m.setName(name);
        if (description != null && !description.isEmpty()) m.setDescription(description);
        if (unitCost > 0) m.setUnitCost(unitCost);
        return m;
    }
}
