package app.domain.ports;

import app.domain.model.MedicationInventory;
import java.util.List;

public interface MedicationInventoryPort {
    void save(MedicationInventory item) throws Exception;
    MedicationInventory findById(Long id);
    MedicationInventory findByName(String name);
    List<MedicationInventory> findAll();
    void delete(MedicationInventory item) throws Exception;
    void updateStock(Long id, int quantityChange) throws Exception;
}
