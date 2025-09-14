package app.domain.services;

import app.domain.model.ProcedireRecord;
import app.domain.ports.ProcedureInventoryPort;

public class ManageProcedureInventory {
    private final ProcedureInventoryPort procedureInventoryPort;

    public ManageProcedureInventory(ProcedureInventoryPort procedureInventoryPort) {
        this.procedureInventoryPort = procedureInventoryPort;
    }

    public void addItem(ProcedireRecord item) throws Exception {
        if (item.getProcedureName() == null || item.getProcedureName().isEmpty()) {
            throw new Exception("El nombre del procedimiento es obligatorio.");
        }
        if (procedureInventoryPort.findByName(item.getProcedureName()) != null) {
            throw new Exception("El procedimiento ya existe en el inventario.");
        }
        if (item.getRepetitions() < 0) {
            throw new Exception("Las repeticiones iniciales no pueden ser negativas.");
        }
        procedureInventoryPort.save(item);
    }

    public void updateItem(ProcedireRecord item) throws Exception {
        if (item.getItemNumber() == 0) { 
            throw new Exception("El ID del procedimiento es obligatorio para actualizar.");
        }
        if (procedureInventoryPort.findById((long)item.getItemNumber()) == null) {
            throw new Exception("El procedimiento no existe en el inventario.");
        }
        procedureInventoryPort.save(item);
    }

    public void removeStock(Long id, int quantity) throws Exception {
        if (id == null) {
            throw new Exception("El ID del procedimiento es obligatorio para remover stock.");
        }
        if (quantity <= 0) {
            throw new Exception("La cantidad a remover debe ser positiva.");
        }
        ProcedireRecord item = procedureInventoryPort.findById(id);
        if (item == null) {
            throw new Exception("Procedimiento no encontrado.");
        }
        if (item.getRepetitions() < quantity) {
            throw new Exception("Stock insuficiente para remover. Repeticiones actuales: " + item.getRepetitions());
        }
        procedureInventoryPort.updateStock(id, -quantity);
    }

    public void addStock(Long id, int quantity) throws Exception {
        if (id == null) {
            throw new Exception("El ID del procedimiento es obligatorio para añadir stock.");
        }
        if (quantity <= 0) {
            throw new Exception("La cantidad a añadir debe ser positiva.");
        }
        ProcedireRecord item = procedureInventoryPort.findById(id);
        if (item == null) {
            throw new Exception("Procedimiento no encontrado.");
        }
        procedureInventoryPort.updateStock(id, quantity);
    }
}
