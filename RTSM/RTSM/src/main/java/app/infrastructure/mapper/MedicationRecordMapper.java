package app.infrastructure.mapper;

import app.domain.model.Medicationrecord;
import app.infrastructure.entity.MedicationRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class MedicationRecordMapper {

    public MedicationRecordEntity toEntity(Medicationrecord model) {
        if (model == null) return null;
        MedicationRecordEntity entity = new MedicationRecordEntity();
        entity.setItemNumber(model.getItemNumber());
        entity.setMedicationName(model.getMedicationName());
        entity.setDose(model.getDose());
        entity.setTreatmentDuration(model.getTreatmentDuration());
        entity.setCost(model.getCost());
        return entity;
    }

    public Medicationrecord toDomain(MedicationRecordEntity entity) {
        if (entity == null) return null;
        Medicationrecord model = new Medicationrecord();
        model.setItemNumber(entity.getItemNumber());
        model.setMedicationName(entity.getMedicationName());
        model.setDose(entity.getDose());
        model.setTreatmentDuration(entity.getTreatmentDuration());
        model.setCost(entity.getCost());
        return model;
    }
}
