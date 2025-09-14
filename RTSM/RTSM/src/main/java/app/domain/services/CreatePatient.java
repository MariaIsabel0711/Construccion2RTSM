package app.domain.services;

import app.domain.model.Patient;
import app.domain.ports.PatientPort;

public class CreatePatient {

    private PatientPort patientPort;

    public CreatePatient(PatientPort patientPort) {
        this.patientPort = patientPort;
    }

    public void create(Patient patient) throws Exception {
        if (patient.getDocument() == null) {
            throw new Exception("Número de identificación obligatorio.");
        }
        if (patientPort.findByDocument(patient.getDocument()) != null) {
            throw new Exception("El paciente ya existe.");
        }

        if (patient.getFullName() == null || patient.getFullName().isEmpty()) {
            throw new Exception("Nombre completo obligatorio.");
        }

        if (patient.getDateOfBirth() == null) {
            throw new Exception("Fecha de nacimiento es obligatoria.");
        }

        java.util.Date now = new java.util.Date();
        long ageInMillis = now.getTime() - patient.getDateOfBirth().getTime();
        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
        if (ageInYears > 150) {
            throw new Exception("Fecha de nacimiento inválida.");
        }

        String phone = patient.getPhoneNumber();
        if (phone == null || !phone.matches("^\\d{10}$")) {
            throw new Exception("Número de teléfono inválido. Debe contener 10 dígitos.");
        }

        if (patient.getEmergencyContactName() == null || patient.getEmergencyContactName().isEmpty()) {
            throw new Exception("Nombre del contacto de emergencia obligatorio.");
        }
        if (patient.getEmergencyContactRelationship() == null || patient.getEmergencyContactRelationship().isEmpty()) {
            throw new Exception("Relación con el paciente obligatoria.");
        }
        String emergencyPhone = patient.getEmergencyContactPhoneNumber();
        if (emergencyPhone == null || !emergencyPhone.matches("^\\d{10}$")) {
            throw new Exception("Número de teléfono de emergencia inválido. Debe contener 10 dígitos.");
        }

        String email = patient.getEmail();
        if (email != null && !email.isEmpty() && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new Exception("Correo electrónico inválido.");
        }

        // Validar póliza (si existe)
        if (patient.getPolicyNumber() != null && !patient.getPolicyNumber().isEmpty()) {
            if (patient.getInsuranceCompanyName() == null || patient.getInsuranceCompanyName().isEmpty()) {
                throw new Exception("Nombre de la compañía de seguro obligatorio si hay número de póliza.");
            }
            if (patient.getPolicyEndDate() == null) {
                throw new Exception("Fecha de finalización de la póliza obligatoria si hay número de póliza.");
            }
        }

        patientPort.save(patient);
    }
}
    