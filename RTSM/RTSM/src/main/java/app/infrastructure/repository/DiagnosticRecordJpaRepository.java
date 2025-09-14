package app.infrastructure.repository;

import app.infrastructure.entity.DiagnosticRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosticRecordJpaRepository extends JpaRepository<DiagnosticRecordEntity, Long> {
    DiagnosticRecordEntity findByDiagnosticAidName(String diagnosticAidName); 

    @Modifying
    @Query("UPDATE DiagnosticRecordEntity d SET d.quantity = d.quantity + :quantityChange WHERE d.id = :id")
    void updateStock(@Param("id") Long id, @Param("quantityChange") int quantityChange); 
}
