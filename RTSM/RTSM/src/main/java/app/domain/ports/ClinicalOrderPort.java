package app.domain.ports;

import app.domain.model.ClinicalOrder;
import java.util.List;

public interface ClinicalOrderPort {
    void save(ClinicalOrder order) throws Exception;
    ClinicalOrder findByOrderNumber(long orderNumber);
    List<ClinicalOrder> findByPatientDocument(Long patientDocument);
    List<ClinicalOrder> findAll();
    void delete(ClinicalOrder order) throws Exception;
}
