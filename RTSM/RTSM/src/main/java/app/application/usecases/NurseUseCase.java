package app.application.usecases;

import app.domain.model.Patient;
import app.domain.model.ClinicalRecord;
import app.domain.model.ClinicalOrder;
import app.domain.services.SearchClinicalRecordByPatient;
import app.domain.services.SearchClinicalOrderByPatient;
import app.domain.ports.PatientPort;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.ClinicalOrderPort;

import java.util.List;

public class NurseUseCase {
	
	private PatientPort patientPort;
	private SearchClinicalRecordByPatient searchClinicalRecord;
	private SearchClinicalOrderByPatient searchClinicalOrder;

    
    public NurseUseCase(PatientPort patientPort, ClinicalRecordPort clinicalRecordPort, ClinicalOrderPort clinicalOrderPort) {
        this.patientPort = patientPort;
        this.searchClinicalRecord = new SearchClinicalRecordByPatient(clinicalRecordPort);
        this.searchClinicalOrder = new SearchClinicalOrderByPatient(clinicalOrderPort);
    }
	
   
	public Patient getPatientInfo(Long patientDocument) {
		return patientPort.findByDocument(patientDocument);
	}
	
    
	public List<ClinicalRecord> getPatientClinicalHistory(Long patientDocument) throws Exception {
		return searchClinicalRecord.searchByDocument(patientDocument);
	}
	
   
	public List<ClinicalOrder> getPatientOrders(Long patientDocument) throws Exception {
		return searchClinicalOrder.searchByDocument(patientDocument);
	}
	
  
	public void registerVitalSigns(Long patientDocument, String bloodPressure, double temperature, int pulse, double oxygenLevel) throws Exception {
		Patient patient = patientPort.findByDocument(patientDocument);
		if (patient == null) {
			throw new Exception("El paciente no exitse" );
		}
		
		throw new UnsupportedOperationException();
	}
	

	public void registerMedicationAdministered(Long patientDocument, long orderNumber, String medicationName, String dose) throws Exception {
		Patient patient = patientPort.findByDocument(patientDocument);
		if (patient == null) {
			throw new Exception("El paciente no existe");
		}
		
		List<ClinicalOrder> orders = searchClinicalOrder.searchByDocument(patientDocument);
		boolean orderExists = orders.stream().anyMatch(order -> order.getOrderNumber() == orderNumber);
		if (!orderExists) {
			throw new Exception("La orden no existe para este paciente.");
		}
		
		throw new UnsupportedOperationException();
	}
}
