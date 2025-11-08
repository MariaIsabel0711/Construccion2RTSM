package app.adapter.in.rest.request;

public class ClinicalRecordRequest {
    private Long patientDocument;
    private String attentionDate; 
    private Long medicalDocument;
    private String motivoConsulta;
    private String sintomas;
    private String diagnostico;

    public Long getPatientDocument() { return patientDocument; }
    public void setPatientDocument(Long patientDocument) { this.patientDocument = patientDocument; }
    public String getAttentionDate() { return attentionDate; }
    public void setAttentionDate(String attentionDate) { this.attentionDate = attentionDate; }
    public Long getMedicalDocument() { return medicalDocument; }
    public void setMedicalDocument(Long medicalDocument) { this.medicalDocument = medicalDocument; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }
    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
}