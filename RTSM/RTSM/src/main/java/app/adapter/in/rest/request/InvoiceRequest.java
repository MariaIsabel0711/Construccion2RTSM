package app.adapter.in.rest.request;

public class InvoiceRequest {
	
	
    private Long patientDocument;
    private Long medicalDocument;
    private double totalAmount;
    private String invoiceDate;
    
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
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	} 

 
}
