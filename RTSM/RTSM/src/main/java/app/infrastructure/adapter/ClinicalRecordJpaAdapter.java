package app.infrastructure.adapter;

import app.domain.model.ClinicalRecord;
import app.domain.ports.ClinicalRecordPort;
import app.infrastructure.entity.ClinicalRecordEntity;
import app.infrastructure.mapper.ClinicalRecordMapper;
import app.infrastructure.repository.ClinicalRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClinicalRecordJpaAdapter implements ClinicalRecordPort {

    private final ClinicalRecordJpaRepository clinicalRecordJpaRepository;
    private final ClinicalRecordMapper clinicalRecordMapper;

    public ClinicalRecordJpaAdapter(ClinicalRecordJpaRepository clinicalRecordJpaRepository, ClinicalRecordMapper clinicalRecordMapper) {
        this.clinicalRecordJpaRepository = clinicalRecordJpaRepository;
        this.clinicalRecordMapper = clinicalRecordMapper;
    }

    @Override
    public void save(ClinicalRecord record) throws Exception {
        ClinicalRecordEntity entity = clinicalRecordMapper.toEntity(record);
        clinicalRecordJpaRepository.save(entity);
    }

    @Override
    public ClinicalRecord findByPatientDocumentAndDate(Long patientDocument, Date attentionDate) {
        ClinicalRecordEntity entity = clinicalRecordJpaRepository.findByPatientDocumentAndAttentionDate(patientDocument, attentionDate);
        return clinicalRecordMapper.toDomain(entity);
    }

    @Override
    public List<ClinicalRecord> findByPatientDocument(Long patientDocument) {
        List<ClinicalRecordEntity> entities = clinicalRecordJpaRepository.findByPatientDocument(patientDocument);
        return entities.stream()
                .map(clinicalRecordMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ClinicalRecord record) throws Exception {
        ClinicalRecordEntity entity = clinicalRecordMapper.toEntity(record);
        clinicalRecordJpaRepository.delete(entity);
    }
}
