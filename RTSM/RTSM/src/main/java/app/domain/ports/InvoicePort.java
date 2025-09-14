package app.domain.ports;

import app.domain.model.Invoice;
import java.util.List;

public interface InvoicePort {
    void save(Invoice invoice) throws Exception;
    Invoice findById(long id);
    List<Invoice> findByPatientDocument(Long patientDocument);
    List<Invoice> findAll();
    void delete(Invoice invoice) throws Exception;
    Double getAnnualCopaySum(Long patientDocument, int year); 
}
    