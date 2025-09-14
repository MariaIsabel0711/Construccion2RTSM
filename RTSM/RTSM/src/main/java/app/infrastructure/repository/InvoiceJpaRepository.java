package app.infrastructure.repository;

import app.infrastructure.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Date;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findByPatientDocument(Long patientDocument);

    @Query("SELECT SUM(i.copayAmount) FROM InvoiceEntity i WHERE i.patientDocument = :patientDocument AND YEAR(i.invoiceDate) = YEAR(:year)")
    Double sumCopayAmountByPatientAndYear(Long patientDocument, Date year);
}
