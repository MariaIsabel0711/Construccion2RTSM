package app.infrastructure.mapper;

import app.domain.model.ClinicalRecord;
import app.infrastructure.entity.ClinicalRecordEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;

@Component
public class ClinicalRecordMapper {

    public ClinicalRecord toDomain(ClinicalRecordEntity entity) {
        if (entity == null) {
            return null;
        }
        ClinicalRecord clinicalRecord = new ClinicalRecord();
        clinicalRecord.setPatientDocument(entity.getPatientDocument());
        clinicalRecord.setAttentionDate(entity.getAttentionDate());
        clinicalRecord.setRecordDetails(entity.getRecordDetails() != null ? entity.getRecordDetails() : new HashMap<>());
        return clinicalRecord;
    }

    public ClinicalRecordEntity toEntity(ClinicalRecord domain) {
        if (domain == null) {
            return null;
        }
        ClinicalRecordEntity entity = new ClinicalRecordEntity();
        entity.setPatientDocument(domain.getPatientDocument());
        entity.setAttentionDate(domain.getAttentionDate());
        entity.setRecordDetails(domain.getRecordDetails() != null ? domain.getRecordDetails() : Collections.emptyMap());
        return entity;
    }
}
