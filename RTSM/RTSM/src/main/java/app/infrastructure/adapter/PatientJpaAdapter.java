package app.infrastructure.adapter;

import app.domain.model.Patient;
import app.domain.ports.PatientPort;
import app.infrastructure.entity.PatientEntity;
import app.infrastructure.mapper.PatientMapper;
import app.infrastructure.repository.PatientJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientJpaAdapter implements PatientPort {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientMapper patientMapper;

    public PatientJpaAdapter(PatientJpaRepository patientJpaRepository, PatientMapper patientMapper) {
        this.patientJpaRepository = patientJpaRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public void save(Patient patient) throws Exception {
        PatientEntity entity = patientMapper.toEntity(patient);
        patientJpaRepository.save(entity);
    }

    @Override
    public Patient findByDocument(Long document) {
        PatientEntity entity = patientJpaRepository.findByDocument(document);
        return patientMapper.toDomain(entity);
    }

    @Override
    public List<Patient> findAll() {
        List<PatientEntity> entities = patientJpaRepository.findAll();
        return entities.stream()
                .map(patientMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Patient patient) throws Exception {
        PatientEntity entity = patientMapper.toEntity(patient);
        patientJpaRepository.delete(entity);
    }

    @Override
    public void update(Patient patient) throws Exception {
        save(patient);
    }
}
