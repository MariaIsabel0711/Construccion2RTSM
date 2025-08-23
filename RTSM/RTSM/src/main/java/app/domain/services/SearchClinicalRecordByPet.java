package app.domain.services;

import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.ports.ClinicalRecordPort;

import java.util.List;

public class SearchClinicalRecordByPet {

    private ClinicalRecordPort clinicalRecordPort;


    public SearchClinicalRecordByPet(ClinicalRecordPort clinicalRecordPort) {
        this.clinicalRecordPort = clinicalRecordPort;
    }

    //Busca todos los registros clínicos asociados a una mascota.

    public List<ClinicalRecord> search(Pet pet) throws Exception {

    	//Recordar Meterle a esto una lógica de validación muchachos y lo mismo con los otros servicios. 
    	if (pet == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula para la búsqueda de registros clínicos.");
        }
        return clinicalRecordPort.findByPet(pet);
    }
}
