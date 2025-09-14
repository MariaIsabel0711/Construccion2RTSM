package app.infrastructure.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Entity
@Table(name = "clinical_records")
public class ClinicalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientDocument;

    @Column(nullable = false)
    private Date attentionDate;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String recordDetailsJson;

    @Transient
    private Map<String, Object> recordDetails;

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

    public Date getAttentionDate() {
        return attentionDate;
    }

    public void setAttentionDate(Date attentionDate) {
        this.attentionDate = attentionDate;
    }

    public String getRecordDetailsJson() {
        return recordDetailsJson;
    }

    public void setRecordDetailsJson(String recordDetailsJson) {
        this.recordDetailsJson = recordDetailsJson;
    }

    public Map<String, Object> getRecordDetails() {
        if (recordDetails == null && recordDetailsJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                @SuppressWarnings("unchecked")
                Map<String, Object> tempMap = mapper.readValue(recordDetailsJson, Map.class);
                recordDetails = tempMap;
            } catch (IOException e) {
                System.err.println("Error al deserializar recordDetailsJson: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return recordDetails;
    }

    public void setRecordDetails(Map<String, Object> recordDetails) {
        this.recordDetails = recordDetails;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.recordDetailsJson = mapper.writeValueAsString(recordDetails);
        } catch (IOException e) {
            System.err.println("Error al serializar recordDetails: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
