package app.domain.services;

import app.domain.model.User;
import app.domain.ports.UserPort;

public class UserService {
	
	private UserPort userPort;
	
	public void createUser(User user) throws Exception{
		//Validar que solo exista 1 persona con la cedula
		if (userPort.findByDocument(user)!=null) {
			throw new Exception("Ya existe una persona registrada con esa cédula");
		
		}
	
		// Validar que solo exista 1 usuario con ese nombre de usuario 
		if (userPort.findByUserName(user)!=null) {
			throw new Exception("Ya existe una persona registrada con ese Nombre de Usuario");
	
		}
		userPort.save(user);
		
	}

}
