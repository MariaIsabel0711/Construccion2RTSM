package app.adapter.in.rest.request;

public class DiagnosisTreatmentRequest {
    private Long patientDocument;
    private String attentionDate; 
    private String diagnosis;
    private String treatment;

    public Long getPatientDocument() { return patientDocument; }
    public void setPatientDocument(Long patientDocument) { this.patientDocument = patientDocument; }
    public String getAttentionDate() { return attentionDate; }
    public void setAttentionDate(String attentionDate) { this.attentionDate = attentionDate; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
}