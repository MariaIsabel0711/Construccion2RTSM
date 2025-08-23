package app.domain.ports;

import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.model.Person;

import java.util.List;

public interface InvoicePort {

	// Busca una factura por el id
    public Invoice findById(long id) throws Exception;

    //Busca facturas asociadas a una mascota.
     
    public List<Invoice> findByPet(Pet pet) throws Exception;

    //Busca facturas asociadas a un dueño.
   
    public List<Invoice> findByOwner(Person owner) throws Exception;

    //Guarda una nueva factura.
   
    public void save(Invoice invoice) throws Exception;
}
