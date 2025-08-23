package app.application.usecases;

import app.domain.model.ClinicalOrder;
import app.domain.model.Invoice;
import app.domain.model.Pet;
import app.domain.services.CreateInvoice;
import app.domain.services.SearchClinicalOrderByPet; 

import java.util.List;

public class SellerUseCase {

    private CreateInvoice createInvoice;
    private SearchClinicalOrderByPet searchClinicalOrderByPet; // Para que el vendedor pueda consultar órdenes

    
    public SellerUseCase(CreateInvoice createInvoice, SearchClinicalOrderByPet searchClinicalOrderByPet) {
        this.createInvoice = createInvoice;
        this.searchClinicalOrderByPet = searchClinicalOrderByPet;
    }

 
    public void registerSale(Invoice invoice) throws Exception {
        
        if (invoice.getOrder() != null) {

        	ClinicalOrder order = searchClinicalOrderByPet.search(invoice.getPet())
                                    .stream()
                                    .filter(o -> o.getId() == invoice.getOrder().getId())
                                    .findFirst()
                                    .orElse(null);
            if (order == null ) { 
                throw new Exception("La orden de medicamento asociada no es válida o no existe.");
            }
        }
        createInvoice.create(invoice);
    }

     //Consulta todas las ordenes clinicas para su visualizacin.
     // Los vendedores solo pueden consultar, no puedencrear ni anular.

    public List<ClinicalOrder> consultOrders(Pet pet) throws Exception {
        return searchClinicalOrderByPet.search(pet);
    }



}
