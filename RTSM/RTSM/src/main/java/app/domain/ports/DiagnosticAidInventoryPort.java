package app.domain.ports;

import app.domain.model.DiagnosticRecord;
import java.util.List;

public interface DiagnosticAidInventoryPort {
    void save(DiagnosticRecord item) throws Exception;
    DiagnosticRecord findById(Long id);
    DiagnosticRecord findByName(String name);
    List<DiagnosticRecord> findAll();
    void delete(DiagnosticRecord item) throws Exception;
    void updateStock(Long id, int quantityChange) throws Exception;
}
