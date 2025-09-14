package app.infrastructure.repository;

import app.infrastructure.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Long> {
    PatientEntity findByDocument(Long document);
}
