package app.infrastructure.adapter;

import app.domain.model.ClinicalOrder;
import app.domain.ports.ClinicalOrderPort;
import app.infrastructure.entity.ClinicalOrderEntity;
import app.infrastructure.mapper.ClinicalOrderMapper;
import app.infrastructure.repository.ClinicalOrderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClinicalOrderJpaAdapter implements ClinicalOrderPort {

    public final ClinicalOrderJpaRepository clinicalOrderJpaRepository; 
    private final ClinicalOrderMapper clinicalOrderMapper;

    public ClinicalOrderJpaAdapter(ClinicalOrderJpaRepository clinicalOrderJpaRepository, ClinicalOrderMapper clinicalOrderMapper) {
        this.clinicalOrderJpaRepository = clinicalOrderJpaRepository;
        this.clinicalOrderMapper = clinicalOrderMapper;
    }

    @Override
    public void save(ClinicalOrder order) throws Exception {
        ClinicalOrderEntity entity = clinicalOrderMapper.toEntity(order);
        clinicalOrderJpaRepository.save(entity);
    }

    @Override
    public ClinicalOrder findByOrderNumber(long orderNumber) {
        Optional<ClinicalOrderEntity> entity = clinicalOrderJpaRepository.findById(orderNumber);
        return entity.map(clinicalOrderMapper::toDomain).orElse(null);
    }

    @Override
    public List<ClinicalOrder> findByPatientDocument(Long patientDocument) {
        List<ClinicalOrderEntity> entities = clinicalOrderJpaRepository.findByPatientDocument(patientDocument);
        return entities.stream()
                .map(clinicalOrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicalOrder> findAll() {
        List<ClinicalOrderEntity> entities = clinicalOrderJpaRepository.findAll();
        return entities.stream()
                .map(clinicalOrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ClinicalOrder order) throws Exception {
        ClinicalOrderEntity entity = clinicalOrderMapper.toEntity(order);
        clinicalOrderJpaRepository.delete(entity);
    }
}
