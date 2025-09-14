package app.infrastructure.adapter;

import app.domain.model.ProcedireRecord;
import app.domain.ports.ProcedureInventoryPort;
import app.infrastructure.entity.ProcedureRecordEntity;
import app.infrastructure.mapper.ProcedureRecordMapper;
import app.infrastructure.repository.ProcedureRecordJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProcedureInventoryJpaAdapter implements ProcedureInventoryPort {

    private final ProcedureRecordJpaRepository repository;
    private final ProcedureRecordMapper mapper;

    public ProcedureInventoryJpaAdapter(ProcedureRecordJpaRepository repository, ProcedureRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(ProcedireRecord item) throws Exception {
        repository.save(mapper.toEntity(item));
    }

    @Override
    public ProcedireRecord findById(Long id) {
        Optional<ProcedureRecordEntity> entity = repository.findById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public ProcedireRecord findByName(String name) {
        ProcedureRecordEntity entity = repository.findByProcedureName(name);
        return mapper.toDomain(entity);
    }

    @Override
    public List<ProcedireRecord> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(ProcedireRecord item) throws Exception {
        repository.delete(mapper.toEntity(item));
    }

    @Override
    @Transactional
    public void updateStock(Long id, int quantityChange) throws Exception {
        repository.updateStock(id, quantityChange);
    }
}
