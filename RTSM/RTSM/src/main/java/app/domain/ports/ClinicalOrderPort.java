package app.domain.ports;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;

import java.util.List;

public interface ClinicalOrderPort {

    // Busca una orden clínica por su ID.
 
    public ClinicalOrder findById(long id) throws Exception;

    //Busca todas las órdenes clínicas asociadas a una mascota.

    public List<ClinicalOrder> findByPet(Pet pet) throws Exception;

    //Guarda una nueva orden clínica.

    public void save(ClinicalOrder clinicalOrder) throws Exception;

    //Actualiza el estado de una orden clínica 
  
    public void update(ClinicalOrder clinicalOrder) throws Exception;

    //Obtiene todas las órdenes clínicas registradas.
  
    public List<ClinicalOrder> findAll() throws Exception;
}
