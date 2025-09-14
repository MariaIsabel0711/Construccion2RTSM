package app.infrastructure.repository;

import app.infrastructure.entity.MedicationInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationInventoryJpaRepository extends JpaRepository<MedicationInventoryEntity, Long> {
    MedicationInventoryEntity findByName(String name);

    @Modifying
    @Query("UPDATE MedicationInventoryEntity m SET m.stock = m.stock + :quantityChange WHERE m.id = :id")
    void updateStock(@Param("id") Long id, @Param("quantityChange") int quantityChange);
}
