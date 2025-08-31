package app.application.usecases;

import app.domain.model.User;
import app.domain.model.enums.Role;
import app.domain.services.CreateUser;
import app.domain.ports.UserPort;

public class HumanResourcesUseCase {
	
	private CreateUser createUser;
	private UserPort userPort;

   
    public HumanResourcesUseCase(UserPort userPort) {
        this.userPort = userPort;
        this.createUser = new CreateUser(userPort);
    }
	
    
	public void createAdministrativeStaff(User user) throws Exception {
		user.setRole(Role.PERSONAL_ADMINISTRATIVO);
		createUser.create(user);
	}
	
    
	public void createInformationSupport(User user) throws Exception {
		user.setRole(Role.SOPORTE_INFORMACION);
		createUser.create(user);
	}
	
    
    
	public void createNurse(User user) throws Exception {
		user.setRole(Role.ENFERMERA);
		createUser.create(user);
	}
	
    
	public void createDoctor(User user) throws Exception {
		user.setRole(Role.MEDICO);
		createUser.create(user);
	}
	
  
	public void deleteUser(User user) throws Exception {
		if (user == null) {
			throw new Exception("El usuario no puede ser nulo.");
		}
		userPort.delete(user);
	}
	
    
	public void updateUserPersonalData(User user) throws Exception {
		if (user == null) {
			throw new Exception();
		}
		User existingUser = userPort.findByDocument(user.getDocument());
		if (existingUser == null) {
			throw new Exception("El usuario no existe ");
		}
		userPort.save(user); 
	}
}
