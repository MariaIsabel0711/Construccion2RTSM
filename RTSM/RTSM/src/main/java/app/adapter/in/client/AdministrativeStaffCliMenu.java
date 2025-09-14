package app.adapter.in.client;

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

    public AdministrativeStaffCliMenu(AdministrativeStaffUseCase adminUseCase) {
        this.adminUseCase = adminUseCase;
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
                case "1":
                    registrarPaciente();
                    break;
                case "2":
                    buscarPacientePorCedula();
                    break;
                case "3":
                    programarCita();
                    break;
                case "4":
                    generarFactura();
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

    private void registrarPaciente() throws Exception {
        System.out.println("\n--- Registrar Nuevo Paciente ---");
        Patient patient = new Patient();

        patient.setFullName(InputReader.readString("Nombre completo: "));
        patient.setDocument(InputReader.readLong("Cédula (número): "));
        String email = InputReader.readString("Correo (opcional): ");
        if (!email.isEmpty()) patient.setEmail(email);
        patient.setPhoneNumber(InputReader.readString("Teléfono (10 dígitos): "));
        patient.setDateOfBirth(InputReader.readDate("Fecha de nacimiento (YYYY-MM-DD): "));
        patient.setAddress(InputReader.readString("Dirección: "));
        patient.setGender(InputReader.readString("Género (M/F/Otro): "));

        // Contacto de emergencia
        System.out.println("\n--- Contacto de Emergencia ---");
        patient.setEmergencyContactName(InputReader.readString("Nombre contacto de emergencia: "));
        patient.setEmergencyContactRelationship(InputReader.readString("Relación con el paciente: "));
        patient.setEmergencyContactPhoneNumber(InputReader.readString("Teléfono contacto de emergencia (10 dígitos): "));

        // Seguro médico
        System.out.println("\n--- Información de Seguro Médico (Deje en blanco si no aplica) ---");
        String insuranceCompany = InputReader.readString("Compañía de seguro: ");
        if (!insuranceCompany.isEmpty()) {
            patient.setInsuranceCompanyName(insuranceCompany);
            patient.setPolicyNumber(InputReader.readString("Número de póliza: "));
            patient.setPolicyStatus(InputReader.readBoolean("Estado de la póliza (activo/inactivo)"));
            patient.setPolicyEndDate(InputReader.readDate("Fecha de finalización de la póliza (YYYY-MM-DD): "));
        } else {
            patient.setPolicyStatus(false);
        }

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
        Invoice invoice = new Invoice();

        invoice.setPatientDocument(InputReader.readLong("Cédula del paciente: "));
        invoice.setMedicalDocument(InputReader.readLong("Cédula del médico: "));
        invoice.setTotalAmount(InputReader.readDouble("Monto total del servicio: "));
        invoice.setInvoiceDate(Date.valueOf(LocalDate.now()));

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
