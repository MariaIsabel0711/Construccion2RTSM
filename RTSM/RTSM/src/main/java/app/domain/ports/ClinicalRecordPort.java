package app.domain.ports;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;

import java.util.List;

public interface ClinicalRecordPort {

    //Busca un registro clinico por el id
  
    public ClinicalRecord findById(long id) throws Exception;

    //Busca todos los registros clinicos asociados a una mascota
  
    public List<ClinicalRecord> findByPet(Pet pet) throws Exception;

    //Guarda un nuevo registro clínico.
  
    public void save(ClinicalRecord clinicalRecord) throws Exception;

    //Actualiza un registro que ya exuiste.
   
    public void update(ClinicalRecord clinicalRecord) throws Exception;
}
