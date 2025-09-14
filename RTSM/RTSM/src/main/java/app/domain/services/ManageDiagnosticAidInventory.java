package app.domain.services;

import app.domain.model.DiagnosticRecord;
import app.domain.ports.DiagnosticAidInventoryPort;

public class ManageDiagnosticAidInventory {
    private final DiagnosticAidInventoryPort diagnosticAidInventoryPort;

    public ManageDiagnosticAidInventory(DiagnosticAidInventoryPort diagnosticAidInventoryPort) {
        this.diagnosticAidInventoryPort = diagnosticAidInventoryPort;
    }

    public void addItem(DiagnosticRecord item) throws Exception {
        if (item.getDiagnosticName() == null || item.getDiagnosticName().isEmpty()) {
            throw new Exception("El nombre de la ayuda diagnóstica es obligatorio.");
        }
        if (diagnosticAidInventoryPort.findByName(item.getDiagnosticName()) != null) {
            throw new Exception("La ayuda diagnóstica ya existe en el inventario.");
        }
        if (item.getQuantity() < 0) {
            throw new Exception("La cantidad inicial no puede ser negativa.");
        }
        diagnosticAidInventoryPort.save(item);
    }

    public void updateItem(DiagnosticRecord item) throws Exception {
        if (item.getItemNumber() == 0) { // Asumiendo itemNumber como ID para inventario
            throw new Exception("El ID de la ayuda diagnóstica es obligatorio para actualizar.");
        }
        if (diagnosticAidInventoryPort.findById((long)item.getItemNumber()) == null) {
            throw new Exception("La ayuda diagnóstica no existe en el inventario.");
        }
        diagnosticAidInventoryPort.save(item);
    }

    public void removeStock(Long id, int quantity) throws Exception {
        if (id == null) {
            throw new Exception("El ID de la ayuda diagnóstica es obligatorio para remover stock.");
        }
        if (quantity <= 0) {
            throw new Exception("La cantidad a remover debe ser positiva.");
        }
        DiagnosticRecord item = diagnosticAidInventoryPort.findById(id);
        if (item == null) {
            throw new Exception("Ayuda diagnóstica no encontrada.");
        }
        if (item.getQuantity() < quantity) {
            throw new Exception("Stock insuficiente para remover. Cantidad actual: " + item.getQuantity());
        }
        diagnosticAidInventoryPort.updateStock(id, -quantity);
    }

    public void addStock(Long id, int quantity) throws Exception {
        if (id == null) {
            throw new Exception("El ID de la ayuda diagnóstica es obligatorio para añadir stock.");
        }
        if (quantity <= 0) {
            throw new Exception("La cantidad a añadir debe ser positiva.");
        }
        DiagnosticRecord item = diagnosticAidInventoryPort.findById(id);
        if (item == null) {
            throw new Exception("Ayuda diagnóstica no encontrada.");
        }
        diagnosticAidInventoryPort.updateStock(id, quantity);
    }
}
