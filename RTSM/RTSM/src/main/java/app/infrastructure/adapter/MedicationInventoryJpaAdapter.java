package app.infrastructure.adapter;

import app.domain.model.MedicationInventory;
import app.domain.ports.MedicationInventoryPort;
import app.infrastructure.entity.MedicationInventoryEntity;
import app.infrastructure.mapper.MedicationInventoryMapper;
import app.infrastructure.repository.MedicationInventoryJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MedicationInventoryJpaAdapter implements MedicationInventoryPort {

    private final MedicationInventoryJpaRepository repository;
    private final MedicationInventoryMapper mapper;

    public MedicationInventoryJpaAdapter(MedicationInventoryJpaRepository repository, MedicationInventoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(MedicationInventory item) throws Exception {
        repository.save(mapper.toEntity(item));
    }

    @Override
    public MedicationInventory findById(Long id) {
        Optional<MedicationInventoryEntity> entity = repository.findById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public MedicationInventory findByName(String name) {
        MedicationInventoryEntity entity = repository.findByName(name);
        return mapper.toDomain(entity);
    }

    @Override
    public List<MedicationInventory> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(MedicationInventory item) throws Exception {
        repository.delete(mapper.toEntity(item));
    }

    @Override
    @Transactional
    public void updateStock(Long id, int quantityChange) throws Exception {
        repository.updateStock(id, quantityChange);
    }
}
