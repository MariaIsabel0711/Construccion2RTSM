package app.application.usecases;

import app.domain.model.User;
import app.domain.model.enums.Role;
import app.domain.services.CreateUser;

public class AdminUseCase {
	
	private CreateUser createUser;

   
    public AdminUseCase(CreateUser createUser) {
        this.createUser = createUser; //Constructor que me trae la dependencia CreateUser
    }
	
	public void createVeterinarian(User user) throws Exception{
		// Asigna el rol de VETERINARIAN al usuario 
		user.setRole(Role.VETERINARIAN);
		createUser.createUser(user);//y lo crea.
		
	}
	
	public void createSeller(User user) throws Exception{
		// Lo mismo que arriba 
		user.setRole(Role.SELLER);
		createUser.createUser(user);
		
	}
	
	
}
