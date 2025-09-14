package app.domain.services;

import app.domain.model.ClinicalOrder;
import app.domain.ports.ClinicalOrderPort;

public class CreateClinicalOrder {

    private ClinicalOrderPort clinicalOrderPort;

    public CreateClinicalOrder(ClinicalOrderPort clinicalOrderPort) {
        this.clinicalOrderPort = clinicalOrderPort;
    }

    public void create(ClinicalOrder order) throws Exception {
        if (order.getOrderNumber() == 0) {
            throw new Exception("El número de orden es obligatorio.");
        }
        if (clinicalOrderPort.findByOrderNumber(order.getOrderNumber()) != null) {
            throw new Exception("El número de orden ya existe. Las órdenes deben ser únicas.");
        }
        if (order.getPatientDocument() == null) {
            throw new Exception("El documento del paciente es obligatorio para la orden clínica.");
        }
        if (order.getMedicalDocument() == null) {
            throw new Exception("El documento del médico es obligatorio para la orden clínica.");
        }
        if (order.getCreationDate() == null) {
            throw new Exception("La fecha de creación de la orden es obligatoria.");
        }
        clinicalOrderPort.save(order);
    }
}
