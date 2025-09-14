package app.application.usecases;

import app.domain.model.Patient;
import app.domain.model.Invoice;
import app.domain.services.CreatePatient;
import app.domain.services.CreateInvoice;
import app.domain.ports.PatientPort;
import app.domain.ports.InvoicePort;
import org.springframework.stereotype.Service;

@Service
public class AdministrativeStaffUseCase {

    private CreatePatient createPatient;
    private CreateInvoice createInvoice;
    private PatientPort patientPort;
    private InvoicePort invoicePort;

    public AdministrativeStaffUseCase(PatientPort patientPort, InvoicePort invoicePort) {
        this.patientPort = patientPort;
        this.invoicePort = invoicePort;
        this.createPatient = new CreatePatient(patientPort);
        this.createInvoice = new CreateInvoice(invoicePort);
    }

    public void registerPatient(Patient patient) throws Exception {
        createPatient.create(patient);
    }

    public Patient findPatientByDocument(Long document) {
        return patientPort.findByDocument(document);
    }

    public void scheduleAppointment(Long patientDocument, java.sql.Date appointmentDate, Long doctorDocument) throws Exception {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new Exception("El paciente con documento " + patientDocument + " no existe en el sistema.");
        }
       
        System.out.println("Cita programada para el paciente " + patient.getFullName() +
                           " (Doc: " + patientDocument + ") con el doctor (Doc: " + doctorDocument + ")" +
                           " para la fecha " + appointmentDate);
    }

    public void generateInvoice(Invoice invoice) throws Exception {
        Patient patient = patientPort.findByDocument(invoice.getPatientDocument());
        if (patient == null) {
            throw new Exception("El paciente con documento " + invoice.getPatientDocument() + " no existe en el sistema.");
        }

        calculateInvoiceAmounts(invoice, patient);

        createInvoice.create(invoice);
    }

    private void calculateInvoiceAmounts(Invoice invoice, Patient patient) {
        double totalAmount = invoice.getTotalAmount();

        if (patient.isPolicyStatus()) { 
            double copay = 50000.0;

            int currentYear = invoice.getInvoiceDate().toLocalDate().getYear();

            Double annualCopaySum = invoicePort.getAnnualCopaySum(patient.getDocument(), currentYear);

            if (annualCopaySum + copay > 1000000.0) { 
                invoice.setCopayAmount(0.0); 
                invoice.setInsuranceCoverageAmount(totalAmount);
            } else {
                invoice.setCopayAmount(copay);
                invoice.setInsuranceCoverageAmount(totalAmount - copay);
            }
        } else { 
            invoice.setCopayAmount(totalAmount); 
            invoice.setInsuranceCoverageAmount(0.0);
        }
    }
}
