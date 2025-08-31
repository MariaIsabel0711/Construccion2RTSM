package app.application.usecases;

import app.domain.model.ClinicalRecord;
import app.domain.model.ClinicalOrder;
import app.domain.model.Patient;
import app.domain.services.CreateClinicalRecord;
import app.domain.services.CreateClinicalOrder;
import app.domain.services.SearchClinicalRecordByPatient;
import app.domain.services.SearchClinicalOrderByPatient;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.PatientPort;

import java.util.List;

public class DoctorUseCase {
	
	private CreateClinicalRecord createClinicalRecord;
	private CreateClinicalOrder createClinicalOrder;
	private SearchClinicalRecordByPatient searchClinicalRecord;
	private SearchClinicalOrderByPatient searchClinicalOrder;
	private PatientPort patientPort;

       public DoctorUseCase(ClinicalRecordPort clinicalRecordPort, ClinicalOrderPort clinicalOrderPort, PatientPort patientPort) {
        this.patientPort = patientPort;
        this.createClinicalRecord = new CreateClinicalRecord(clinicalRecordPort);
        this.createClinicalOrder = new CreateClinicalOrder(clinicalOrderPort);
        this.searchClinicalRecord = new SearchClinicalRecordByPatient(clinicalRecordPort);
        this.searchClinicalOrder = new SearchClinicalOrderByPatient(clinicalOrderPort);
    }
	
   
	public void createClinicalRecord(ClinicalRecord clinicalRecord) throws Exception {
		Patient patient = patientPort.findByDocument(clinicalRecord.getPatientDocument());
		if (patient == null) {
			throw new Exception("El paciente no existe en el sistema");
		}
		
		createClinicalRecord.create(clinicalRecord);
	}
	
   
	public void updateClinicalRecord(ClinicalRecord clinicalRecord) throws Exception {
		ClinicalRecord existingRecord = searchClinicalRecord.searchByDocumentAndDate(
			clinicalRecord.getPatientDocument(), 
			clinicalRecord.getAttentionDate()
		);
		if (existingRecord == null) {
			throw new Exception("El registro clínico no existe");
		}
		
		createClinicalRecord.create(clinicalRecord); 
	}
	
    
	public List<ClinicalRecord> getClinicalHistory(Long patientDocument) throws Exception {
		return searchClinicalRecord.searchByDocument(patientDocument);
	}
	
   	public void createMedicalOrder(ClinicalOrder order) throws Exception {
		// Validar que el paciente existe
		Patient patient = patientPort.findByDocument(order.getPatientDocument());
		if (patient == null) {
			throw new Exception("El paciente no existe.");
		}
		
		createClinicalOrder.create(order);
	}
	
    
	public List<ClinicalOrder> getPatientOrders(Long patientDocument) throws Exception {
		return searchClinicalOrder.searchByDocument(patientDocument);
	}
}
