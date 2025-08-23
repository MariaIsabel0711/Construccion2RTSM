package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.model.Pet;
import app.domain.ports.ClinicalOrderPort;

import java.util.List;

public class SearchClinicalOrderByPet {

    private ClinicalOrderPort clinicalOrderPort;

  
    public SearchClinicalOrderByPet(ClinicalOrderPort clinicalOrderPort) {
        this.clinicalOrderPort = clinicalOrderPort;
    }

    //Busca todas las órdenes clínicas asociadas a una mascota.

    public List<ClinicalOrder> search(Pet pet) throws Exception {
        if (pet == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula para la búsqueda de órdenes.");
        }
        return clinicalOrderPort.findByPet(pet);
    }
}
