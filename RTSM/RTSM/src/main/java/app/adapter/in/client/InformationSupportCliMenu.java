package app.adapter.in.client; 

import app.adapter.in.util.InputReader;
import app.application.usecases.InformationSupportUseCase;
import app.domain.model.DiagnosticRecord;
import app.domain.model.MedicationInventory;
import app.domain.model.ProcedireRecord;
import org.springframework.stereotype.Component;

@Component
public class InformationSupportCliMenu implements CliMenu {

    private final InformationSupportUseCase supportUseCase;

    public InformationSupportCliMenu(InformationSupportUseCase supportUseCase) {
        this.supportUseCase = supportUseCase;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Menú Soporte de Información ---");
        System.out.println("1. Gestionar Inventario de Medicamentos");
        System.out.println("2. Gestionar Inventario de Procedimientos");
        System.out.println("3. Gestionar Inventario de Ayudas Diagnósticas");
        System.out.println("4. Volver al menú principal");
    }

    @Override
    public void handleOption(String option) {
        try {
            switch (option) {
                case "1":
                    gestionarInventarioMedicamentos();
                    break;
                case "2":
                    gestionarInventarioProcedimientos();
                    break;
                case "3":
                    gestionarInventarioAyudasDiagnosticas();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void gestionarInventarioMedicamentos() {
        System.out.println("\n--- Gestión de Inventario de Medicamentos ---");
        while (true) {
            System.out.println("1. Añadir nuevo medicamento");
            System.out.println("2. Actualizar medicamento existente");
            System.out.println("3. Añadir stock a medicamento");
            System.out.println("4. Remover stock de medicamento");
            System.out.println("5. Volver al menú anterior");
            String option = InputReader.readString("Ingrese opción: ");

            try {
                switch (option) {
                    case "1":
                        MedicationInventory newItem = new MedicationInventory();
                        newItem.setName(InputReader.readString("Nombre del medicamento: "));
                        newItem.setDescription(InputReader.readString("Descripción: "));
                        newItem.setStock(InputReader.readInt("Stock inicial: "));
                        newItem.setUnitCost(InputReader.readDouble("Costo unitario: "));
                        supportUseCase.addMedicationItem(newItem); 
                        System.out.println("Medicamento añadido exitosamente.");
                        break;
                    case "2":
                        Long updateId = InputReader.readLong("ID del medicamento a actualizar: ");
                        MedicationInventory existingMed = supportUseCase.findMedicationById(updateId); 
                        if (existingMed == null) {
                            System.out.println("Medicamento no encontrado.");
                            break;
                        }
                        String newName = InputReader.readString("Nuevo nombre (" + existingMed.getName() + ", dejar vacío para no cambiar): ");
                        if (!newName.isEmpty()) existingMed.setName(newName);
                        String newDesc = InputReader.readString("Nueva descripción (" + existingMed.getDescription() + ", dejar vacío para no cambiar): ");
                        if (!newDesc.isEmpty()) existingMed.setDescription(newDesc);
                        double newCost = InputReader.readDouble("Nuevo costo unitario (" + existingMed.getUnitCost() + ", 0 para no cambiar): ");
                        if (newCost != 0) existingMed.setUnitCost(newCost);
                        supportUseCase.updateMedicationItem(existingMed); 
                        System.out.println("Medicamento actualizado exitosamente.");
                        break;
                    case "3":
                        Long addStockId = InputReader.readLong("ID del medicamento para añadir stock: ");
                        int addQuantity = InputReader.readInt("Cantidad a añadir: ");
                        supportUseCase.addMedicationStock(addStockId, addQuantity); 
                        System.out.println("Stock añadido exitosamente.");
                        break;
                    case "4":
                        Long removeStockId = InputReader.readLong("ID del medicamento para remover stock: ");
                        int removeQuantity = InputReader.readInt("Cantidad a remover: ");
                        supportUseCase.removeMedicationStock(removeStockId, removeQuantity); 
                        System.out.println("Stock removido exitosamente.");
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void gestionarInventarioProcedimientos() {
        System.out.println("\n--- Gestión de Inventario de Procedimientos ---");
        while (true) {
            System.out.println("1. Añadir nuevo procedimiento");
            System.out.println("2. Actualizar procedimiento existente");
            System.out.println("3. Añadir stock a procedimiento");
            System.out.println("4. Remover stock de procedimiento");
            System.out.println("5. Volver al menú anterior");
            String option = InputReader.readString("Ingrese opción: ");

            try {
                switch (option) {
                    case "1":
                        ProcedireRecord newProc = new ProcedireRecord();
                        newProc.setProcedureName(InputReader.readString("Nombre del procedimiento: "));
                        newProc.setRepetitions(InputReader.readInt("Repeticiones iniciales (stock): "));
                        newProc.setCost(InputReader.readDouble("Costo: "));
                        supportUseCase.addProcedureItem(newProc); 
                        System.out.println("Procedimiento añadido exitosamente.");
                        break;
                    case "2":
                        Long updateProcId = InputReader.readLong("ID del procedimiento a actualizar: "); 
                        ProcedireRecord existingProc = supportUseCase.findProcedureById(updateProcId); 
                        if (existingProc == null) {
                            System.out.println("Procedimiento no encontrado.");
                            break;
                        }
                        String newProcName = InputReader.readString("Nuevo nombre (" + existingProc.getProcedureName() + ", dejar vacío para no cambiar): ");
                        if (!newProcName.isEmpty()) existingProc.setProcedureName(newProcName);
                        double newProcCost = InputReader.readDouble("Nuevo costo (" + existingProc.getCost() + ", 0 para no cambiar): ");
                        if (newProcCost != 0) existingProc.setCost(newProcCost);
                        supportUseCase.updateProcedureItem(existingProc); 
                        System.out.println("Procedimiento actualizado exitosamente.");
                        break;
                    case "3":
                        Long addProcStockId = InputReader.readLong("ID del procedimiento para añadir stock: ");
                        int addProcQuantity = InputReader.readInt("Cantidad a añadir: ");
                        supportUseCase.addProcedureStock(addProcStockId, addProcQuantity);
                        System.out.println("Stock de procedimiento añadido exitosamente.");
                        break;
                    case "4":
                        Long removeProcStockId = InputReader.readLong("ID del procedimiento para remover stock: ");
                        int removeProcQuantity = InputReader.readInt("Cantidad a remover: ");
                        supportUseCase.removeProcedureStock(removeProcStockId, removeProcQuantity); 
                        System.out.println("Stock de procedimiento removido exitosamente.");
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void gestionarInventarioAyudasDiagnosticas() {
        System.out.println("\n--- Gestión de Inventario de Ayudas Diagnósticas ---");
        while (true) {
            System.out.println("1. Añadir nueva ayuda diagnóstica");
            System.out.println("2. Actualizar ayuda diagnóstica existente");
            System.out.println("3. Añadir stock a ayuda diagnóstica");
            System.out.println("4. Remover stock de ayuda diagnóstica");
            System.out.println("5. Volver al menú anterior");
            String option = InputReader.readString("Ingrese opción: ");

            try {
                switch (option) {
                    case "1":
                        DiagnosticRecord newDiag = new DiagnosticRecord();
                        newDiag.setDiagnosticName(InputReader.readString("Nombre de la ayuda diagnóstica: "));
                        newDiag.setQuantity(InputReader.readInt("Cantidad inicial (stock): "));
                        newDiag.setCost(InputReader.readDouble("Costo: "));
                        supportUseCase.addDiagnosticAidItem(newDiag); 
                        System.out.println("Ayuda diagnóstica añadida exitosamente.");
                        break;
                    case "2":
                        Long updateDiagId = InputReader.readLong("ID de la ayuda diagnóstica a actualizar: "); 
                        DiagnosticRecord existingDiag = supportUseCase.findDiagnosticAidById(updateDiagId); 
                        if (existingDiag == null) {
                            System.out.println("Ayuda diagnóstica no encontrada.");
                            break;
                        }
                        String newDiagName = InputReader.readString("Nuevo nombre (" + existingDiag.getDiagnosticName() + ", dejar vacío para no cambiar): ");
                        if (!newDiagName.isEmpty()) existingDiag.setDiagnosticName(newDiagName);
                        double newDiagCost = InputReader.readDouble("Nuevo costo (" + existingDiag.getCost() + ", 0 para no cambiar): ");
                        if (newDiagCost != 0) existingDiag.setCost(newDiagCost);
                        supportUseCase.updateDiagnosticAidItem(existingDiag); 
                        System.out.println("Ayuda diagnóstica actualizada exitosamente.");
                        break;
                    case "3":
                        Long addDiagStockId = InputReader.readLong("ID de la ayuda diagnóstica para añadir stock: ");
                        int addDiagQuantity = InputReader.readInt("Cantidad a añadir: ");
                        supportUseCase.addDiagnosticAidStock(addDiagStockId, addDiagQuantity); 
                        System.out.println("Stock de ayuda diagnóstica añadido exitosamente.");
                        break;
                    case "4":
                        Long removeDiagStockId = InputReader.readLong("ID de la ayuda diagnóstica para remover stock: ");
                        int removeDiagQuantity = InputReader.readInt("Cantidad a remover: ");
                        supportUseCase.removeDiagnosticAidStock(removeDiagStockId, removeDiagQuantity); 
                        System.out.println("Stock de ayuda diagnóstica removido exitosamente.");
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
