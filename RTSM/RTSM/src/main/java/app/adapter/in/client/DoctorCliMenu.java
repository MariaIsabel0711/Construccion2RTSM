package app.adapter.in.client;

import app.adapter.in.builder.ClinicalOrderBuilder;
import app.adapter.in.builder.ClinicalRecordBuilder;
import app.adapter.in.builder.DiagnosticRecordBuilder;
import app.adapter.in.builder.MedicationRecordBuilder;
import app.adapter.in.builder.ProcedureRecordBuilder;
import app.adapter.in.util.InputReader;
import app.application.usecases.DoctorUseCase;
import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.DiagnosticRecord;
import app.domain.model.Medicationrecord;
import app.domain.model.ProcedireRecord;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorCliMenu implements CliMenu {

    private final DoctorUseCase doctorUseCase;
    private final ClinicalRecordBuilder clinicalRecordBuilder;
    private final ClinicalOrderBuilder clinicalOrderBuilder;
    private final MedicationRecordBuilder medicationRecordBuilder;
    private final ProcedureRecordBuilder procedureRecordBuilder;
    private final DiagnosticRecordBuilder diagnosticRecordBuilder;

    public DoctorCliMenu(DoctorUseCase doctorUseCase,
                         ClinicalRecordBuilder clinicalRecordBuilder,
                         ClinicalOrderBuilder clinicalOrderBuilder,
                         MedicationRecordBuilder medicationRecordBuilder,
                         ProcedureRecordBuilder procedureRecordBuilder,
                         DiagnosticRecordBuilder diagnosticRecordBuilder) {
        this.doctorUseCase = doctorUseCase;
        this.clinicalRecordBuilder = clinicalRecordBuilder;
        this.clinicalOrderBuilder = clinicalOrderBuilder;
        this.medicationRecordBuilder = medicationRecordBuilder;
        this.procedureRecordBuilder = procedureRecordBuilder;
        this.diagnosticRecordBuilder = diagnosticRecordBuilder;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Menú Médico ---");
        System.out.println("1. Crear/Actualizar Historia Clínica");
        System.out.println("2. Registrar Diagnóstico y Tratamiento");
        System.out.println("3. Prescribir Medicamentos, Procedimientos o Ayudas Diagnósticas (Generar Orden)");
        System.out.println("4. Ver Historia Clínica de Paciente");
        System.out.println("5. Ver Órdenes de Paciente");
        System.out.println("6. Volver al menú principal");
    }

    @Override
    public void handleOption(String option) {
        try {
            switch (option) {
                case "1" -> crearActualizarHistoriaClinica();
                case "2" -> registrarDiagnosticoTratamiento();
                case "3" -> prescribirOrden();
                case "4" -> verHistoriaClinica();
                case "5" -> verOrdenesPaciente();
                case "6" -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearActualizarHistoriaClinica() throws Exception {
        System.out.println("\n--- Crear/Actualizar Historia Clínica ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        Date attentionDate = InputReader.readDate("Ingrese fecha de atención (YYYY-MM-DD): ");

        Long medicalDocument = InputReader.readLong("Cédula del médico: ");
        String motivoConsulta = InputReader.readString("Motivo de consulta: ");
        String sintomas = InputReader.readString("Síntomas: ");
        String diagnostico = InputReader.readString("Diagnóstico: ");

        ClinicalRecord clinicalRecord = clinicalRecordBuilder.build(
                patientDocument, attentionDate, medicalDocument, motivoConsulta, sintomas, diagnostico
        );

        try {
            doctorUseCase.updateClinicalRecord(clinicalRecord);
            System.out.println("Historia clínica actualizada exitosamente.");
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("no existe")) {
                doctorUseCase.createClinicalRecord(clinicalRecord);
                System.out.println("Historia clínica creada exitosamente.");
            } else {
                throw e;
            }
        }
    }

    private void registrarDiagnosticoTratamiento() throws Exception {
        System.out.println("\n--- Registrar Diagnóstico y Tratamiento ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        Date attentionDate = InputReader.readDate("Ingrese fecha de atención (YYYY-MM-DD): ");
        String diagnosis = InputReader.readString("Diagnóstico: ");
        String treatment = InputReader.readString("Tratamiento: ");

        doctorUseCase.registerDiagnosisAndTreatment(patientDocument, attentionDate, diagnosis, treatment);
        System.out.println("Diagnóstico y tratamiento registrados exitosamente.");
    }

    private void prescribirOrden() throws Exception {
        System.out.println("\n--- Prescribir Orden Médica ---");
        Long patientDocument = InputReader.readLong("Cédula del paciente: ");
        Long medicalDocument = InputReader.readLong("Cédula del médico: ");
        long orderNumber = InputReader.readLong("Número de orden (único): ");
        Date creationDate = Date.valueOf(LocalDate.now());

        ClinicalOrder order = clinicalOrderBuilder.build(orderNumber, patientDocument, medicalDocument, creationDate);

        List<Medicationrecord> medications = new ArrayList<>();
        List<ProcedireRecord> procedures = new ArrayList<>();
        List<DiagnosticRecord> diagnostics = new ArrayList<>();

        if (InputReader.readBoolean("\n¿Desea añadir medicamentos a la orden?")) {
            while (true) {
                int itemNumber = InputReader.readInt("Número de ítem (único en esta orden): ");
                String name = InputReader.readString("Nombre del medicamento: ");
                String dose = InputReader.readString("Dosis: ");
                String duration = InputReader.readString("Duración del tratamiento: ");
                double cost = InputReader.readDouble("Costo: ");

                medications.add(
                    medicationRecordBuilder.build(itemNumber, name, dose, duration, cost)
                );

                if (!InputReader.readBoolean("¿Añadir otro medicamento?")) break;
            }
        }

        if (InputReader.readBoolean("\n¿Desea añadir procedimientos a la orden?")) {
            while (true) {
                int itemNumber = InputReader.readInt("Número de ítem (único en esta orden): ");
                String procName = InputReader.readString("Nombre del procedimiento: ");
                int repetitions = InputReader.readInt("Veces que se repite: ");
                String frequency = InputReader.readString("Frecuencia: ");
                double cost = InputReader.readDouble("Costo: ");
                boolean requiresSpecialist = InputReader.readBoolean("¿Requiere especialista?");
                Long specialistRoleId = requiresSpecialist ? InputReader.readLong("ID de especialista (rol): ") : null;

                procedures.add(
                    procedureRecordBuilder.build(itemNumber, procName, repetitions, frequency, cost, requiresSpecialist, specialistRoleId)
                );

                if (!InputReader.readBoolean("¿Añadir otro procedimiento?")) break;
            }
        }

        if (InputReader.readBoolean("\n¿Desea añadir ayudas diagnósticas a la orden?")) {
            while (true) {
                int itemNumber = InputReader.readInt("Número de ítem (único en esta orden): ");
                String diagName = InputReader.readString("Nombre del examen: ");
                int quantity = InputReader.readInt("Cantidad: ");
                double cost = InputReader.readDouble("Costo: ");
                boolean requiresSpecialist = InputReader.readBoolean("¿Requiere especialista?");
                Long specialistRoleId = requiresSpecialist ? InputReader.readLong("ID de especialista (rol): ") : null;

                diagnostics.add(
                    diagnosticRecordBuilder.build(itemNumber, diagName, quantity, cost, requiresSpecialist, specialistRoleId)
                );

                if (!InputReader.readBoolean("¿Añadir otra ayuda diagnóstica?")) break;
            }
        }

        doctorUseCase.createMedicalOrder(order, medications, procedures, diagnostics);
        System.out.println("Orden médica creada exitosamente.");
    }

    private void verHistoriaClinica() throws Exception {
        System.out.println("\n--- Ver Historia Clínica de Paciente ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");

        List<ClinicalRecord> history = doctorUseCase.getClinicalHistory(patientDocument);
        if (history.isEmpty()) {
            System.out.println("No se encontró historia clínica para el paciente " + patientDocument);
            return;
        }

        System.out.println("\n--- Historia Clínica para Paciente " + patientDocument + " ---");
        for (ClinicalRecord record : history) {
            System.out.println("Fecha de Atención: " + record.getAttentionDate());
            System.out.println("  Detalles:");
            record.getRecordDetails().forEach((key, value) -> System.out.println("    " + key + ": " + value));
            System.out.println("------------------------------------");
        }
    }

    private void verOrdenesPaciente() throws Exception {
        System.out.println("\n--- Ver Órdenes de Paciente ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");

        List<ClinicalOrder> orders = doctorUseCase.getPatientOrders(patientDocument);
        if (orders.isEmpty()) {
            System.out.println("No se encontraron órdenes para el paciente " + patientDocument);
            return;
        }

        System.out.println("\n--- Órdenes para Paciente " + patientDocument + " ---");
        for (ClinicalOrder order : orders) {
            System.out.println("Número de Orden: " + order.getOrderNumber());
            System.out.println("  Médico: " + order.getMedicalDocument());
            System.out.println("  Fecha Creación: " + order.getCreationDate());
            System.out.println("------------------------------------");
        }
    }
}
