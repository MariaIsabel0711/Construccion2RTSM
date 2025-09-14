package app.infrastructure.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "invoices")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientDocument;

    @Column
    private Long medicalDocument;

    @Column
    private String insuranceCompanyName;

    @Column
    private String policyNumber;

    @Column
    private int policyValidityDays;

    @Column
    private Date policyEndDate;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private double copayAmount;

    @Column(nullable = false)
    private double insuranceCoverageAmount;

    @Column(nullable = false)
    private Date invoiceDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
