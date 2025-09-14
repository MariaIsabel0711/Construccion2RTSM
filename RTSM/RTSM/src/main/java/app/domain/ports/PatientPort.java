package app.domain.ports;

import app.domain.model.Patient;
import java.util.List;

public interface PatientPort {
    void save(Patient patient) throws Exception;
    Patient findByDocument(Long document);
    List<Patient> findAll();
    void delete(Patient patient) throws Exception;
    void update(Patient patient) throws Exception; 
}
