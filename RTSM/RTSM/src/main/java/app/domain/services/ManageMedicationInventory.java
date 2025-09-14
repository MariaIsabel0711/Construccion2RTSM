package app.domain.services;

import app.domain.model.MedicationInventory;
import app.domain.ports.MedicationInventoryPort;

public class ManageMedicationInventory {
    private final MedicationInventoryPort medicationInventoryPort;

    public ManageMedicationInventory(MedicationInventoryPort medicationInventoryPort) {
        this.medicationInventoryPort = medicationInventoryPort;
    }

    public void addItem(MedicationInventory item) throws Exception {
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new Exception("El nombre del medicamento es obligatorio.");
        }
        if (medicationInventoryPort.findByName(item.getName()) != null) {
            throw new Exception("El medicamento ya existe en el inventario.");
        }
        if (item.getStock() < 0) {
            throw new Exception("El stock inicial no puede ser negativo.");
        }
        medicationInventoryPort.save(item);
    }

    public void updateItem(MedicationInventory item) throws Exception {
        if (item.getId() == 0) { 
            throw new Exception("El ID del medicamento es obligatorio para actualizar.");
        }
        if (medicationInventoryPort.findById(item.getId()) == null) {
            throw new Exception("El medicamento no existe en el inventario.");
        }
        medicationInventoryPort.save(item); 
    }

    public void removeStock(Long id, int quantity) throws Exception {
        if (id == null) {
            throw new Exception("El ID del medicamento es obligatorio para remover stock.");
        }
        if (quantity <= 0) {
            throw new Exception("La cantidad a remover debe ser positiva.");
        }
        MedicationInventory item = medicationInventoryPort.findById(id);
        if (item == null) {
            throw new Exception("Medicamento no encontrado.");
        }
        if (item.getStock() < quantity) {
            throw new Exception("Stock insuficiente para remover. Stock actual: " + item.getStock());
        }
        medicationInventoryPort.updateStock(id, -quantity);
    }

    public void addStock(Long id, int quantity) throws Exception {
        if (id == null) {
            throw new Exception("El ID del medicamento es obligatorio para añadir stock.");
        }
        if (quantity <= 0) {
            throw new Exception("La cantidad a añadir debe ser positiva.");
        }
        MedicationInventory item = medicationInventoryPort.findById(id);
        if (item == null) {
            throw new Exception("Medicamento no encontrado.");
        }
        medicationInventoryPort.updateStock(id, quantity);
    }
}
