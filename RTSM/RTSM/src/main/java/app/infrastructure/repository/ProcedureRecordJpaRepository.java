package app.infrastructure.repository;

import app.infrastructure.entity.ProcedureRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRecordJpaRepository extends JpaRepository<ProcedureRecordEntity, Long> {
    ProcedureRecordEntity findByProcedureName(String procedureName); 

    @Modifying
    @Query("UPDATE ProcedureRecordEntity p SET p.repetitions = p.repetitions + :quantityChange WHERE p.id = :id")
    void updateStock(@Param("id") Long id, @Param("quantityChange") int quantityChange); 
}
