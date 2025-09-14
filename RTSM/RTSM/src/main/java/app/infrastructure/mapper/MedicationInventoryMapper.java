package app.infrastructure.mapper;

import app.domain.model.MedicationInventory;
import app.infrastructure.entity.MedicationInventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class MedicationInventoryMapper {
    public MedicationInventory toDomain(MedicationInventoryEntity entity) {
        if (entity == null) return null;
        MedicationInventory domain = new MedicationInventory();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setStock(entity.getStock());
        domain.setUnitCost(entity.getUnitCost());
        return domain;
    }

    public MedicationInventoryEntity toEntity(MedicationInventory domain) {
        if (domain == null) return null;
        MedicationInventoryEntity entity = new MedicationInventoryEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setStock(domain.getStock());
        entity.setUnitCost(domain.getUnitCost());
        return entity;
    }
}
    