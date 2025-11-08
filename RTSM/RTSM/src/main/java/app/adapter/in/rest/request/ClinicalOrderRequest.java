package app.adapter.in.rest.request;

import java.util.List;

public class ClinicalOrderRequest {
    private Long patientDocument;
    private Long medicalDocument;
    private long orderNumber;
    private String creationDate; // "YYYY-MM-DD"

    private List<MedicationItemRequest> medications;
    private List<ProcedureItemRequest> procedures;
    private List<DiagnosticItemRequest> diagnostics;

    public Long getPatientDocument() { return patientDocument; }
    public void setPatientDocument(Long patientDocument) { this.patientDocument = patientDocument; }
    public Long getMedicalDocument() { return medicalDocument; }
    public void setMedicalDocument(Long medicalDocument) { this.medicalDocument = medicalDocument; }
    public long getOrderNumber() { return orderNumber; }
    public void setOrderNumber(long orderNumber) { this.orderNumber = orderNumber; }
    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
    public List<MedicationItemRequest> getMedications() { return medications; }
    public void setMedications(List<MedicationItemRequest> medications) { this.medications = medications; }
    public List<ProcedureItemRequest> getProcedures() { return procedures; }
    public void setProcedures(List<ProcedureItemRequest> procedures) { this.procedures = procedures; }
    public List<DiagnosticItemRequest> getDiagnostics() { return diagnostics; }
    public void setDiagnostics(List<DiagnosticItemRequest> diagnostics) { this.diagnostics = diagnostics; }
}