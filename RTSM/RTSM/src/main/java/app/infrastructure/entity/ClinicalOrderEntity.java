package app.infrastructure.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "clinical_orders")
public class ClinicalOrderEntity {

    @Id
    private Long orderNumber; // # De orden primary key

    @Column(nullable = false)
    private Long patientDocument;

    @Column(nullable = false)
    private Long medicalDocument;

    @Column(nullable = false)
    private Date creationDate;

    @OneToMany(mappedBy = "clinicalOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationRecordEntity> medications;

    @OneToMany(mappedBy = "clinicalOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcedureRecordEntity> procedures;

    @OneToMany(mappedBy = "clinicalOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiagnosticRecordEntity> diagnostics;

    // Getters and Setters
    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
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

    public List<MedicationRecordEntity> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationRecordEntity> medications) {
        this.medications = medications;
    }

    public List<ProcedureRecordEntity> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<ProcedureRecordEntity> procedures) {
        this.procedures = procedures;
    }

    public List<DiagnosticRecordEntity> getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(List<DiagnosticRecordEntity> diagnostics) {
        this.diagnostics = diagnostics;
    }
}
