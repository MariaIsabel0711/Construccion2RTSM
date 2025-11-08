package app.adapter.in.client;

import app.adapter.in.builder.InvoiceBuilder;
import app.adapter.in.builder.PatientBuilder;
import app.adapter.in.util.InputReader;
import app.application.usecases.AdministrativeStaffUseCase;
import app.domain.model.Invoice;
import app.domain.model.Patient;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class AdministrativeStaffCliMenu implements CliMenu {

    private final AdministrativeStaffUseCase adminUseCase;
    private final PatientBuilder patientBuilder;
    private final InvoiceBuilder invoiceBuilder;

    public AdministrativeStaffCliMenu(AdministrativeStaffUseCase adminUseCase,
                                      PatientBuilder patientBuilder,
                                      InvoiceBuilder invoiceBuilder) {
        this.adminUseCase = adminUseCase;
        this.patientBuilder = patientBuilder;
        this.invoiceBuilder = invoiceBuilder;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Menú Personal Administrativo ---");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Buscar paciente por cédula");
        System.out.println("3. Programar cita");
        System.out.println("4. Generar factura");
        System.out.println("5. Volver al menú principal");
    }

    @Override
    public void handleOption(String option) {
        try {
            switch (option) {
                case "1" -> registrarPaciente();
                case "2" -> buscarPacientePorCedula();
                case "3" -> programarCita();
                case "4" -> generarFactura();
                case "5" -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarPaciente() throws Exception {
        System.out.println("\n--- Registrar Nuevo Paciente ---");

        String fullName = InputReader.readString("Nombre completo: ");
        Long document = InputReader.readLong("Cédula (número): ");
        String email = InputReader.readString("Correo (opcional): ");
        String phone = InputReader.readString("Teléfono (10 dígitos): ");
        Date dateOfBirth = InputReader.readDate("Fecha de nacimiento (YYYY-MM-DD): ");
        String address = InputReader.readString("Dirección: ");
        String gender = InputReader.readString("Género (M/F/Otro): ");

        System.out.println("\n--- Contacto de Emergencia ---");
        String emergencyName = InputReader.readString("Nombre contacto de emergencia: ");
        String emergencyRelation = InputReader.readString("Relación con el paciente: ");
        String emergencyPhone = InputReader.readString("Teléfono contacto de emergencia (10 dígitos): ");

        System.out.println("\n--- Información de Seguro Médico (Deje en blanco si no aplica) ---");
        String insuranceCompany = InputReader.readString("Compañía de seguro: ");
        String policyNumber = null;
        boolean policyStatus = false;
        Date policyEndDate = null;

        if (!insuranceCompany.isEmpty()) {
            policyNumber = InputReader.readString("Número de póliza: ");
            policyStatus = InputReader.readBoolean("Estado de la póliza (activo/inactivo)");
            policyEndDate = InputReader.readDate("Fecha de finalización de la póliza (YYYY-MM-DD): ");
        }

        Patient patient = patientBuilder.build(
            fullName, document, email, phone, dateOfBirth, address, gender,
            emergencyName, emergencyRelation, emergencyPhone,
            insuranceCompany, policyNumber, policyStatus, policyEndDate
        );

        adminUseCase.registerPatient(patient);
        System.out.println("Paciente registrado exitosamente.");
    }

    private void buscarPacientePorCedula() {
        System.out.println("\n--- Buscar Paciente por Cédula ---");
        Long document = InputReader.readLong("Ingrese cédula del paciente: ");

        Patient patient = adminUseCase.findPatientByDocument(document);
        if (patient != null) {
            System.out.println("\n--- Datos del Paciente ---");
            System.out.println("Nombre: " + patient.getFullName());
            System.out.println("Cédula: " + patient.getDocument());
            System.out.println("Email: " + patient.getEmail());
            System.out.println("Teléfono: " + patient.getPhoneNumber());
            System.out.println("Fecha Nacimiento: " + patient.getDateOfBirth());
            System.out.println("Dirección: " + patient.getAddress());
            System.out.println("Género: " + patient.getGender());
            System.out.println("Contacto Emergencia: " + patient.getEmergencyContactName() + " (" + patient.getEmergencyContactRelationship() + ") - " + patient.getEmergencyContactPhoneNumber());
            System.out.println("Seguro: " + (patient.getInsuranceCompanyName() != null ? patient.getInsuranceCompanyName() : "N/A"));
            System.out.println("Póliza: " + (patient.getPolicyNumber() != null ? patient.getPolicyNumber() : "N/A"));
            System.out.println("Estado Póliza: " + (patient.isPolicyStatus() ? "Activa" : "Inactiva"));
            System.out.println("Fin Póliza: " + (patient.getPolicyEndDate() != null ? patient.getPolicyEndDate() : "N/A"));
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private void programarCita() throws Exception {
        System.out.println("\n--- Programar Cita ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        Long doctorDocument = InputReader.readLong("Ingrese cédula del médico: ");
        Date appointmentDate = InputReader.readDate("Ingrese fecha de la cita (YYYY-MM-DD): ");

        adminUseCase.scheduleAppointment(patientDocument, appointmentDate, doctorDocument);
        System.out.println("Cita programada exitosamente.");
    }

    private void generarFactura() throws Exception {
        System.out.println("\n--- Generar Factura ---");

        Long patientDoc = InputReader.readLong("Cédula del paciente: ");
        Long medicalDoc = InputReader.readLong("Cédula del médico: ");
        double totalAmount = InputReader.readDouble("Monto total del servicio: ");
        Date invoiceDate = Date.valueOf(LocalDate.now());

        Invoice invoice = invoiceBuilder.build(patientDoc, medicalDoc, totalAmount, invoiceDate);

        adminUseCase.generateInvoice(invoice);
        System.out.println("Factura generada exitosamente.");
        System.out.println("Detalles de la factura:");
        System.out.println("ID Factura: " + invoice.getId());
        System.out.println("Paciente: " + invoice.getPatientDocument());
        System.out.println("Médico: " + invoice.getMedicalDocument());
        System.out.println("Monto Total: " + invoice.getTotalAmount());
        System.out.println("Monto Copago: " + invoice.getCopayAmount());
        System.out.println("Monto Cobertura Seguro: " + invoice.getInsuranceCoverageAmount());
        System.out.println("Fecha Factura: " + invoice.getInvoiceDate());
    }
}
