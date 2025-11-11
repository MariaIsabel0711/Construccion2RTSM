package app.infrastructure.adapter;

import app.domain.model.ClinicalRecord;
import app.domain.ports.ClinicalRecordPort;
import app.infrastructure.entity.ClinicalRecordEntity;
import app.infrastructure.mapper.ClinicalRecordMapper;
import app.infrastructure.repository.ClinicalRecordJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;

@Component
public class ClinicalRecordJpaAdapter implements ClinicalRecordPort {

    private final ClinicalRecordJpaRepository clinicalRecordJpaRepository;
    private final ClinicalRecordMapper clinicalRecordMapper;

    public ClinicalRecordJpaAdapter(ClinicalRecordJpaRepository clinicalRecordJpaRepository,
                                    ClinicalRecordMapper clinicalRecordMapper) {
        this.clinicalRecordJpaRepository = clinicalRecordJpaRepository;
        this.clinicalRecordMapper = clinicalRecordMapper;
    }

    @Override
    @Transactional
    public void save(ClinicalRecord record) throws Exception {
        ClinicalRecordEntity entity = clinicalRecordMapper.toEntity(record);
        // Si la entidad tiene @GeneratedValue en id, deja id en null para INSERT
        if (entity.getId() != null && entity.getId() == 0L) {
            entity.setId(null);
        }
        clinicalRecordJpaRepository.save(entity);
    }

    @Override
    public ClinicalRecord findByPatientDocumentAndDate(Long patientDocument, Date attentionDate) {
        List<ClinicalRecordEntity> list =
                clinicalRecordJpaRepository.findByPatientDocumentAndAttentionDate(patientDocument, attentionDate);

        if (list == null || list.isEmpty()) {
            return null;
        }

        // Si hay duplicados (temporalmente), elige uno de forma determinística (menor id)
        ClinicalRecordEntity chosen = list.stream()
                .min(Comparator.comparingLong(ClinicalRecordEntity::getId))
                .orElse(list.get(0));

        return clinicalRecordMapper.toDomain(chosen);
    }

    @Override
    public List<ClinicalRecord> findByPatientDocument(Long patientDocument) {
        List<ClinicalRecordEntity> entities = clinicalRecordJpaRepository.findByPatientDocument(patientDocument);
        return entities.stream()
                .map(clinicalRecordMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void delete(ClinicalRecord record) throws Exception {
        // El dominio no tiene id; borramos por clave natural
        clinicalRecordJpaRepository.deleteByPatientDocumentAndAttentionDate(
                record.getPatientDocument(),
                record.getAttentionDate()
        );
    }
}
