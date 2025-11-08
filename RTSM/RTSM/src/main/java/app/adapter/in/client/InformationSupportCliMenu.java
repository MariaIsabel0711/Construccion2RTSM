package app.adapter.in.client;

import app.adapter.in.builder.DiagnosticAidBuilder;
import app.adapter.in.builder.MedicationInventoryBuilder;
import app.adapter.in.builder.ProcedureItemBuilder;
import app.adapter.in.util.InputReader;
import app.application.usecases.InformationSupportUseCase;
import app.domain.model.DiagnosticRecord;
import app.domain.model.MedicationInventory;
import app.domain.model.ProcedireRecord;
import org.springframework.stereotype.Component;

@Component
public class InformationSupportCliMenu implements CliMenu {

    private final InformationSupportUseCase supportUseCase;
    private final MedicationInventoryBuilder medicationInventoryBuilder;
    private final ProcedureItemBuilder procedureItemBuilder;
    private final DiagnosticAidBuilder diagnosticAidBuilder;

    public InformationSupportCliMenu(InformationSupportUseCase supportUseCase,
                                     MedicationInventoryBuilder medicationInventoryBuilder,
                                     ProcedureItemBuilder procedureItemBuilder,
                                     DiagnosticAidBuilder diagnosticAidBuilder) {
        this.supportUseCase = supportUseCase;
        this.medicationInventoryBuilder = medicationInventoryBuilder;
        this.procedureItemBuilder = procedureItemBuilder;
        this.diagnosticAidBuilder = diagnosticAidBuilder;
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
                case "1" -> gestionarInventarioMedicamentos();
                case "2" -> gestionarInventarioProcedimientos();
                case "3" -> gestionarInventarioAyudasDiagnosticas();
                case "4" -> { return; }
                default -> System.out.println("Opción inválida.");
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
                    case "1" -> {
                        String name = InputReader.readString("Nombre del medicamento: ");
                        String description = InputReader.readString("Descripción: ");
                        int stock = InputReader.readInt("Stock inicial: ");
                        double unitCost = InputReader.readDouble("Costo unitario: ");
                        MedicationInventory newItem = medicationInventoryBuilder.build(name, description, stock, unitCost);
                        supportUseCase.addMedicationItem(newItem);
                        System.out.println("Medicamento añadido exitosamente.");
                    }
                    case "2" -> {
                        Long updateId = InputReader.readLong("ID del medicamento a actualizar: ");
                        MedicationInventory existingMed = supportUseCase.findMedicationById(updateId);
                        if (existingMed == null) {
                            System.out.println("Medicamento no encontrado.");
                            break;
                        }
                        String newName = InputReader.readString("Nuevo nombre (" + existingMed.getName() + ", dejar vacío para no cambiar): ");
                        String newDesc = InputReader.readString("Nueva descripción (" + existingMed.getDescription() + ", dejar vacío para no cambiar): ");
                        double newCost = InputReader.readDouble("Nuevo costo unitario (" + existingMed.getUnitCost() + ", 0 para no cambiar): ");
                        medicationInventoryBuilder.applyUpdates(existingMed, newName, newDesc, newCost);
                        supportUseCase.updateMedicationItem(existingMed);
                        System.out.println("Medicamento actualizado exitosamente.");
                    }
                    case "3" -> {
                        Long addStockId = InputReader.readLong("ID del medicamento para añadir stock: ");
                        int addQuantity = InputReader.readInt("Cantidad a añadir: ");
                        supportUseCase.addMedicationStock(addStockId, addQuantity);
                        System.out.println("Stock añadido exitosamente.");
                    }
                    case "4" -> {
                        Long removeStockId = InputReader.readLong("ID del medicamento para remover stock: ");
                        int removeQuantity = InputReader.readInt("Cantidad a remover: ");
                        supportUseCase.removeMedicationStock(removeStockId, removeQuantity);
                        System.out.println("Stock removido exitosamente.");
                    }
                    case "5" -> { return; }
                    default -> System.out.println("Opción inválida.");
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
                    case "1" -> {
                        String name = InputReader.readString("Nombre del procedimiento: ");
                        int repetitions = InputReader.readInt("Repeticiones iniciales (stock): ");
                        double cost = InputReader.readDouble("Costo: ");
                        ProcedireRecord newProc = procedureItemBuilder.build(name, repetitions, cost);
                        supportUseCase.addProcedureItem(newProc);
                        System.out.println("Procedimiento añadido exitosamente.");
                    }
                    case "2" -> {
                        Long updateProcId = InputReader.readLong("ID del procedimiento a actualizar: ");
                        ProcedireRecord existingProc = supportUseCase.findProcedureById(updateProcId);
                        if (existingProc == null) {
                            System.out.println("Procedimiento no encontrado.");
                            break;
                        }
                        String newName = InputReader.readString("Nuevo nombre (" + existingProc.getProcedureName() + ", dejar vacío para no cambiar): ");
                        double newCost = InputReader.readDouble("Nuevo costo (" + existingProc.getCost() + ", 0 para no cambiar): ");
                        procedureItemBuilder.applyUpdates(existingProc, newName, newCost);
                        supportUseCase.updateProcedureItem(existingProc);
                        System.out.println("Procedimiento actualizado exitosamente.");
                    }
                    case "3" -> {
                        Long addProcStockId = InputReader.readLong("ID del procedimiento para añadir stock: ");
                        int addProcQuantity = InputReader.readInt("Cantidad a añadir: ");
                        supportUseCase.addProcedureStock(addProcStockId, addProcQuantity);
                        System.out.println("Stock de procedimiento añadido exitosamente.");
                    }
                    case "4" -> {
                        Long removeProcStockId = InputReader.readLong("ID del procedimiento para remover stock: ");
                        int removeProcQuantity = InputReader.readInt("Cantidad a remover: ");
                        supportUseCase.removeProcedureStock(removeProcStockId, removeProcQuantity);
                        System.out.println("Stock de procedimiento removido exitosamente.");
                    }
                    case "5" -> { return; }
                    default -> System.out.println("Opción inválida.");
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
                    case "1" -> {
                        String name = InputReader.readString("Nombre de la ayuda diagnóstica: ");
                        int quantity = InputReader.readInt("Cantidad inicial (stock): ");
                        double cost = InputReader.readDouble("Costo: ");
                        DiagnosticRecord newDiag = diagnosticAidBuilder.build(name, quantity, cost);
                        supportUseCase.addDiagnosticAidItem(newDiag);
                        System.out.println("Ayuda diagnóstica añadida exitosamente.");
                    }
                    case "2" -> {
                        Long updateDiagId = InputReader.readLong("ID de la ayuda diagnóstica a actualizar: ");
                        DiagnosticRecord existingDiag = supportUseCase.findDiagnosticAidById(updateDiagId);
                        if (existingDiag == null) {
                            System.out.println("Ayuda diagnóstica no encontrada.");
                            break;
                        }
                        String newName = InputReader.readString("Nuevo nombre (" + existingDiag.getDiagnosticName() + ", dejar vacío para no cambiar): ");
                        double newCost = InputReader.readDouble("Nuevo costo (" + existingDiag.getCost() + ", 0 para no cambiar): ");
                        diagnosticAidBuilder.applyUpdates(existingDiag, newName, newCost);
                        supportUseCase.updateDiagnosticAidItem(existingDiag);
                        System.out.println("Ayuda diagnóstica actualizada exitosamente.");
                    }
                    case "3" -> {
                        Long addDiagStockId = InputReader.readLong("ID de la ayuda diagnóstica para añadir stock: ");
                        int addDiagQuantity = InputReader.readInt("Cantidad a añadir: ");
                        supportUseCase.addDiagnosticAidStock(addDiagStockId, addDiagQuantity);
                        System.out.println("Stock de ayuda diagnóstica añadido exitosamente.");
                    }
                    case "4" -> {
                        Long removeDiagStockId = InputReader.readLong("ID de la ayuda diagnóstica para remover stock: ");
                        int removeDiagQuantity = InputReader.readInt("Cantidad a remover: ");
                        supportUseCase.removeDiagnosticAidStock(removeDiagStockId, removeDiagQuantity);
                        System.out.println("Stock de ayuda diagnóstica removido exitosamente.");
                    }
                    case "5" -> { return; }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
