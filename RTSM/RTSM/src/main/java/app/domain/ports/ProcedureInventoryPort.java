package app.domain.ports;

import app.domain.model.ProcedireRecord;
import java.util.List;

public interface ProcedureInventoryPort {
    void save(ProcedireRecord item) throws Exception;
    ProcedireRecord findById(Long id);
    ProcedireRecord findByName(String name);
    List<ProcedireRecord> findAll();
    void delete(ProcedireRecord item) throws Exception;
    void updateStock(Long id, int quantityChange) throws Exception;
}
