package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.ports.ClinicalOrderPort;

public class CreateClinicalOrder {

    private ClinicalOrderPort clinicalOrderPort;

   
    public CreateClinicalOrder(ClinicalOrderPort clinicalOrderPort) {
        this.clinicalOrderPort = clinicalOrderPort;
    }

 
    public void create(ClinicalOrder order) throws Exception {
    
        clinicalOrderPort.save(order); //Crea y guarda corden clinica
    }
}
