package app.adapter.in.client;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DoctorCliMenu implements CliMenu {

    private final DoctorUseCase doctorUseCase;

    public DoctorCliMenu(DoctorUseCase doctorUseCase) {
        this.doctorUseCase = doctorUseCase;
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
                case "1":
                    crearActualizarHistoriaClinica();
                    break;
                case "2":
                    registrarDiagnosticoTratamiento();
                    break;
                case "3":
                    prescribirOrden();
                    break;
                case "4":
                    verHistoriaClinica();
                    break;
                case "5":
                    verOrdenesPaciente();
                    break;
                case "6":
                    return; 
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearActualizarHistoriaClinica() throws Exception {
        System.out.println("\n--- Crear/Actualizar Historia Clínica ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        Date attentionDate = InputReader.readDate("Ingrese fecha de atención (YYYY-MM-DD): ");

        Map<String, Object> recordDetails = new HashMap<>();
        recordDetails.put("medicalDocument", InputReader.readLong("Cédula del médico: "));
        recordDetails.put("motivoConsulta", InputReader.readString("Motivo de consulta: "));
        recordDetails.put("sintomas", InputReader.readString("Síntomas: "));
        recordDetails.put("diagnostico", InputReader.readString("Diagnóstico: "));

        ClinicalRecord clinicalRecord = new ClinicalRecord();
        clinicalRecord.setPatientDocument(patientDocument);
        clinicalRecord.setAttentionDate(attentionDate);
        clinicalRecord.setRecordDetails(recordDetails);

        try {
            doctorUseCase.updateClinicalRecord(clinicalRecord);
            System.out.println("Historia clínica actualizada exitosamente.");
        } catch (Exception e) {
            if (e.getMessage().contains("no existe")) {
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

        ClinicalOrder order = new ClinicalOrder();
        order.setOrderNumber(orderNumber);
        order.setPatientDocument(patientDocument);
        order.setMedicalDocument(medicalDocument);
        order.setCreationDate(creationDate);

        List<Medicationrecord> medications = new ArrayList<>();
        List<ProcedireRecord> procedures = new ArrayList<>();
        List<DiagnosticRecord> diagnostics = new ArrayList<>();

        if (InputReader.readBoolean("\n¿Desea añadir medicamentos a la orden?")) {
            while (true) {
                Medicationrecord med = new Medicationrecord();
                med.setItemNumber(InputReader.readInt("Número de ítem (único en esta orden): "));
                med.setMedicationName(InputReader.readString("Nombre del medicamento: "));
                med.setDose(InputReader.readString("Dosis: "));
                med.setTreatmentDuration(InputReader.readString("Duración del tratamiento: "));
                med.setCost(InputReader.readDouble("Costo: "));
                medications.add(med);
                if (!InputReader.readBoolean("¿Añadir otro medicamento?")) break;
            }
        }

        if (InputReader.readBoolean("\n¿Desea añadir procedimientos a la orden?")) {
            while (true) {
                ProcedireRecord proc = new ProcedireRecord();
                proc.setItemNumber(InputReader.readInt("Número de ítem (único en esta orden): "));
                proc.setProcedureName(InputReader.readString("Nombre del procedimiento: "));
                proc.setRepetitions(InputReader.readInt("Veces que se repite: "));
                proc.setFrequency(InputReader.readString("Frecuencia: "));
                proc.setCost(InputReader.readDouble("Costo: "));
                proc.setRequiresSpecialist(InputReader.readBoolean("¿Requiere especialista?"));
                if (proc.isRequiresSpecialist()) {
                    proc.setSpecialistRoleId(InputReader.readLong("ID de especialista (rol): "));
                }
                procedures.add(proc);
                if (!InputReader.readBoolean("¿Añadir otro procedimiento?")) break;
            }
        }

        if (InputReader.readBoolean("\n¿Desea añadir ayudas diagnósticas a la orden?")) {
            while (true) {
                DiagnosticRecord diag = new DiagnosticRecord();
                diag.setItemNumber(InputReader.readInt("Número de ítem (único en esta orden): "));
                diag.setDiagnosticName(InputReader.readString("Nombre del examen: "));
                diag.setQuantity(InputReader.readInt("Cantidad: "));
                diag.setCost(InputReader.readDouble("Costo: "));
                diag.setRequiresSpecialist(InputReader.readBoolean("¿Requiere especialista?"));
                if (diag.isRequiresSpecialist()) {
                    diag.setSpecialistRoleId(InputReader.readLong("ID de especialista (rol): "));
                }
                diagnostics.add(diag);
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
