package app.domain.model;

import java.sql.Date;
 

public class ClinicalOrder {
	
	private long orderNumber; // # De orden
	private Long patientDocument; // Cédula del paciente
	private Long medicalDocument; // Cédula del médico que la generó
	private Date creationDate; // Fecha de creación
	
	
	
	public long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Long getPatientDocument() {
		return patientDocument;
	}
	public void setPatientDocument(Long patientDocument) {
		this.patientDocument = patientDocument;
	}
	public Long getMedicalDocument() {
		return medicalDocument;
	}
	public void setMedicalDocument(Long medicalDocument) {
		this.medicalDocument = medicalDocument;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
