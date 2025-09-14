package app.infrastructure.adapter;

import app.domain.model.DiagnosticRecord;
import app.domain.ports.DiagnosticAidInventoryPort;
import app.infrastructure.entity.DiagnosticRecordEntity;
import app.infrastructure.mapper.DiagnosticRecordMapper;
import app.infrastructure.repository.DiagnosticRecordJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiagnosticAidInventoryJpaAdapter implements DiagnosticAidInventoryPort {

    private final DiagnosticRecordJpaRepository repository;
    private final DiagnosticRecordMapper mapper;

    public DiagnosticAidInventoryJpaAdapter(DiagnosticRecordJpaRepository repository, DiagnosticRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(DiagnosticRecord item) throws Exception {
        repository.save(mapper.toEntity(item));
    }

    @Override
    public DiagnosticRecord findById(Long id) {
        Optional<DiagnosticRecordEntity> entity = repository.findById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public DiagnosticRecord findByName(String name) {
        DiagnosticRecordEntity entity = repository.findByDiagnosticAidName(name);
        return mapper.toDomain(entity);
    }

    @Override
    public List<DiagnosticRecord> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(DiagnosticRecord item) throws Exception {
        repository.delete(mapper.toEntity(item));
    }

    @Override
    @Transactional
    public void updateStock(Long id, int quantityChange) throws Exception {
        repository.updateStock(id, quantityChange);
    }
}
