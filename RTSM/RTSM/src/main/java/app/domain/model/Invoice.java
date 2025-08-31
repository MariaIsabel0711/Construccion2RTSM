package app.domain.model;

import java.sql.Date;

public class Invoice {
	
	private long id;
	private Long patientDocument; // Cédula del paciente
	private Long medicalDocument; // Cédula del médico 
	private String insuranceCompanyName; // Nombre delseguro
	private String policyNumber; // # de póliza
	private int policyValidityDays; // Días de vigencia 
	private Date policyEndDate; // finalización de la póliza
	
	private double totalAmount; // total de la factura
	private double copayAmount; // copago
	private double insuranceCoverageAmount; // Monto que cubre el seguro
	
	private Date invoiceDate; // Fecha de la factura
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public int getPolicyValidityDays() {
		return policyValidityDays;
	}
	public void setPolicyValidityDays(int policyValidityDays) {
		this.policyValidityDays = policyValidityDays;
	}
	public Date getPolicyEndDate() {
		return policyEndDate;
	}
	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getCopayAmount() {
		return copayAmount;
	}
	public void setCopayAmount(double copayAmount) {
		this.copayAmount = copayAmount;
	}
	public double getInsuranceCoverageAmount() {
		return insuranceCoverageAmount;
	}
	public void setInsuranceCoverageAmount(double insuranceCoverageAmount) {
		this.insuranceCoverageAmount = insuranceCoverageAmount;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
}
