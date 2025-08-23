package app.application.usecases;

import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;
import app.domain.model.enums.Role;
import app.domain.services.CreateClinicalOrder;
import app.domain.services.CreateClinicalRecord; 
import app.domain.services.CreatePet;
import app.domain.services.CreateUser;
import app.domain.services.SearchClinicalOrderByPet;
import app.domain.services.SearchClinicalRecordByPet; 

import java.util.List; //Para guardar los datos en ordrn 

public class VeterinarianUseCase {
	
	private CreateUser createUser;
	private CreatePet createPet;
	private CreateClinicalOrder createClinicalOrder;
	private SearchClinicalOrderByPet searchClinicalOrder;
    private CreateClinicalRecord createClinicalRecord; 
    private SearchClinicalRecordByPet searchClinicalRecord;
    

    //Constructor para traer las dependencias que necesiyo para el caso de uso del veterinario.
    
    public VeterinarianUseCase(CreateUser createUser, CreatePet createPet,
                               CreateClinicalOrder createClinicalOrder, SearchClinicalOrderByPet searchClinicalOrder,
                               CreateClinicalRecord createClinicalRecord, SearchClinicalRecordByPet searchClinicalRecord) {
        this.createUser = createUser;
        this.createPet = createPet;
        this.createClinicalOrder = createClinicalOrder;
        this.searchClinicalOrder = searchClinicalOrder;
        this.createClinicalRecord = createClinicalRecord;
        this.searchClinicalRecord = searchClinicalRecord;
    }
	
	public void CreateOwner(User user) throws Exception{
		
		user.setRole(Role.OWNER); // Asigna el rol de OWNER al usuario 
		createUser.createUser(user); // y lo crea.
		
	}
		
	public void CreatePet(Pet pet) throws Exception{
		
		createPet.create(pet);// Crea una nueva mascota
		
	}
		
	public void CreateOrder (ClinicalOrder order) throws Exception{
		
		createClinicalOrder.create(order); // Crea una nueva orden clínica
		
	}

  
    public void cancelOrder(ClinicalOrder order, ClinicalRecord clinicalRecord) throws Exception {

        // Registrar la anulación en la historia clínica
        clinicalRecord.setClinicalOrder(order); // Asociar la orden anulada al registro
        clinicalRecord.setMedicalProcedure("Anulación de Orden: " + order.getId()); // Detalle de la anulación
        createClinicalRecord.create(clinicalRecord); // Guardar el registro de anulacipn
    }
	
	public List<ClinicalOrder> searchOrders (Pet pet) throws Exception{
		// Busca y retorna todas las órdenes clínicas asociadas a una mascota.
		return searchClinicalOrder.search(pet);
	}

 
    public void createClinicalRecord(ClinicalRecord clinicalRecord) throws Exception {
        createClinicalRecord.create(clinicalRecord);
    }


    public List<ClinicalRecord> searchClinicalRecords(Pet pet) throws Exception {
        return searchClinicalRecord.search(pet);
    }
}
