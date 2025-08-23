package app.domain.services;

import app.domain.model.Pet;
import app.domain.model.User; // Importar User
import app.domain.ports.PetPort;
import app.domain.ports.UserPort;

public class CreatePet {

    private PetPort petPort;
    private UserPort userPort;

    public CreatePet(PetPort petPort, UserPort userPort) {
        this.petPort = petPort;
        this.userPort = userPort;
    }
    
    public void create(Pet pet) throws Exception {
        // Validar que el dueño de la mascota exista 
        if (pet.getOwner() == null || pet.getOwner().getDocument() <= 0) { 
            throw new Exception("El dueño de la mascota no está registrado en el sistema.");
        }
        
        // Creamos aqui un User temporal solo con el documento para buscar 
        User searchUser   = new User();
        searchUser .setDocument(pet.getOwner().getDocument());
        
        // Buscar si el dueño existe
        if (userPort.findByDocument(searchUser ) == null) {
            throw new Exception("El dueño de la mascota no está registrado en el sistema.");
        }
        
        petPort.save(pet);
    }

   
   
}
