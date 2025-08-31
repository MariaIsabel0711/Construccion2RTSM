package app.domain.model;

import java.sql.Date;
import java.util.Map; // Para el diccionario 

public class ClinicalRecord {
	
	private Long patientDocument; 
	private Date attentionDate; 
	
	// El resto de la historia clínica lo majenamos como un diccionario
	private Map<String, Object> recordDetails; 
	
	public Long getPatientDocument() {
		return patientDocument;
	}
	public void setPatientDocument(Long patientDocument) {
		this.patientDocument = patientDocument;
	}
	public Date getAttentionDate() {
		return attentionDate;
	}
	public void setAttentionDate(Date attentionDate) {
		this.attentionDate = attentionDate;
	}
	public Map<String, Object> getRecordDetails() {
		return recordDetails;
	}
	public void setRecordDetails(Map<String, Object> recordDetails) {
		this.recordDetails = recordDetails;
	}
}
