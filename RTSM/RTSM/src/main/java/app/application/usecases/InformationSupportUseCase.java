package app.application.usecases;

import app.domain.model.User;
import app.domain.ports.UserPort;
import java.util.List;

public class InformationSupportUseCase {
	
	private UserPort userPort;

  
    public InformationSupportUseCase(UserPort userPort) {
        this.userPort = userPort;
    }
	
   
	public List<User> getAllUsers() {
		return userPort.findAll();
	}
	
   
	public User findUserByDocument(Long document) {
		return userPort.findByDocument(document);
	}
	
 
	public User findUserByUserName(String userName) {
		return userPort.findByUserName(userName);
	}
	
  
	public void updateUserData(User user) throws Exception {
		if (user == null) {
			throw new Exception("El usuario no puede ser nulo.");
		}
		
		User existingUser = userPort.findByDocument(user.getDocument());
		if (existingUser == null) {
			throw new Exception("El usuario no existe en el sistema.");
		}
		
		userPort.save(user); 
	}
	
	public void manageMedicationInventory() throws Exception {
		throw new UnsupportedOperationException();
	}
	
  
	public void manageProcedureInventory() throws Exception {
		throw new UnsupportedOperationException();
	}
	
   
	public void manageDiagnosticAidInventory() throws Exception {
		throw new UnsupportedOperationException();
	}
}
