package app.infrastructure.mapper;

import app.domain.model.ClinicalOrder;
import app.infrastructure.entity.ClinicalOrderEntity;
import org.springframework.stereotype.Component;


@Component
public class ClinicalOrderMapper {

    
    public ClinicalOrderMapper() {
        
    }

    public ClinicalOrder toDomain(ClinicalOrderEntity entity) {
        if (entity == null) {
            return null;
        }
        ClinicalOrder domain = new ClinicalOrder();
        domain.setOrderNumber(entity.getOrderNumber());
        domain.setPatientDocument(entity.getPatientDocument());
        domain.setMedicalDocument(entity.getMedicalDocument());
        domain.setCreationDate(entity.getCreationDate());

        
        return domain;
    }

    public ClinicalOrderEntity toEntity(ClinicalOrder domain) {
        if (domain == null) {
            return null;
        }
        ClinicalOrderEntity entity = new ClinicalOrderEntity();
        entity.setOrderNumber(domain.getOrderNumber());
        entity.setPatientDocument(domain.getPatientDocument());
        entity.setMedicalDocument(domain.getMedicalDocument());
        entity.setCreationDate(domain.getCreationDate());

        
        return entity;
    }
}
