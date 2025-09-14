package app.infrastructure.mapper;

import app.domain.model.DiagnosticRecord;
import app.infrastructure.entity.DiagnosticRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticRecordMapper {

    public DiagnosticRecordEntity toEntity(DiagnosticRecord model) {
        if (model == null) return null;
        DiagnosticRecordEntity entity = new DiagnosticRecordEntity();
        entity.setItemNumber(model.getItemNumber());
        entity.setDiagnosticAidName(model.getDiagnosticName());
        entity.setQuantity(model.getQuantity());
        entity.setCost(model.getCost());
        entity.setRequiresSpecialist(model.isRequiresSpecialist());
        entity.setSpecialistRoleId(model.getSpecialistRoleId());
        return entity;
    }

    public DiagnosticRecord toDomain(DiagnosticRecordEntity entity) {
        if (entity == null) return null;
        DiagnosticRecord model = new DiagnosticRecord();
        model.setItemNumber(entity.getItemNumber());
        model.setDiagnosticName(entity.getDiagnosticAidName());
        model.setQuantity(entity.getQuantity());
        model.setCost(entity.getCost());
        model.setRequiresSpecialist(entity.isRequiresSpecialist());
        model.setSpecialistRoleId(entity.getSpecialistRoleId());
        return model;
    }
}
