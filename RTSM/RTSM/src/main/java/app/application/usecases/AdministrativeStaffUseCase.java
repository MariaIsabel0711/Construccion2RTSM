package app.application.usecases;

import app.domain.model.Patient;
import app.domain.model.Invoice;
import app.domain.services.CreatePatient;
import app.domain.services.CreateInvoice;
import app.domain.ports.PatientPort;
import app.domain.ports.InvoicePort;

public class AdministrativeStaffUseCase {
	
	private CreatePatient createPatient;
	private CreateInvoice createInvoice;
	private PatientPort patientPort;
	

    public AdministrativeStaffUseCase(PatientPort patientPort, InvoicePort invoicePort) {
        this.patientPort = patientPort;
        // this.invoicePort = invoicePort;
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
		throw new UnsupportedOperationException();
	}
	
	public void generateInvoice(Invoice invoice) throws Exception {
		Patient patient = patientPort.findByDocument(invoice.getPatientDocument());
		if (patient == null) {
			throw new Exception("El paciente no existe en el sistema");
		}
		
		calculateInvoiceAmounts(invoice, patient);
		
		createInvoice.create(invoice);
	}
	
	private void calculateInvoiceAmounts(Invoice invoice, Patient patient) {
		double totalAmount = invoice.getTotalAmount();
		
		if (patient.isPolicyStatus()) { 
			double copay = 50000.0;
			invoice.setCopayAmount(copay);
			invoice.setInsuranceCoverageAmount(totalAmount - copay);
		} else { 
			invoice.setCopayAmount(totalAmount); 
			invoice.setInsuranceCoverageAmount(0.0);
		}
	}
}
