package app.infrastructure.mapper;

import app.domain.model.ProcedireRecord;
import app.infrastructure.entity.ProcedureRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class ProcedureRecordMapper {

    public ProcedureRecordEntity toEntity(ProcedireRecord model) {
        if (model == null) return null;
        ProcedureRecordEntity entity = new ProcedureRecordEntity();
        entity.setItemNumber(model.getItemNumber());
        entity.setProcedureName(model.getProcedureName());
        entity.setRepetitions(model.getRepetitions());
        entity.setFrequency(model.getFrequency());
        entity.setCost(model.getCost());
        entity.setRequiresSpecialist(model.isRequiresSpecialist());
        entity.setSpecialistRoleId(model.getSpecialistRoleId());
        return entity;
    }

    public ProcedireRecord toDomain(ProcedureRecordEntity entity) {
        if (entity == null) return null;
        ProcedireRecord model = new ProcedireRecord();
        model.setItemNumber(entity.getItemNumber());
        model.setProcedureName(entity.getProcedureName());
        model.setRepetitions(entity.getRepetitions());
        model.setFrequency(entity.getFrequency());
        model.setCost(entity.getCost());
        model.setRequiresSpecialist(entity.isRequiresSpecialist());
        model.setSpecialistRoleId(entity.getSpecialistRoleId());
        return model;
    }
}
