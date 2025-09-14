package app.domain.services;

import app.domain.model.User;
import app.domain.ports.UserPort;

public class CreateUser {
    
    private UserPort userPort;
    
    public CreateUser(UserPort userPort) {
        this.userPort = userPort;
    }
    
    public void create(User user) throws Exception {
        if (user == null) {
            throw new Exception("El usuario no puede ser nulo.");
        }
        
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            throw new Exception("El nombre completo es obligatorio.");
        }
        
        if (user.getDocument() == null) {
            throw new Exception("El documento es obligatorio.");
        }
        
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new Exception("El nombre de usuario es obligatorio.");
        }
        
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new Exception("La contraseña es obligatoria.");
        }
        
        if (user.getRole() == null) { 
            throw new Exception("El rol es obligatorio.");
        }
        
        User existingUserByDocument = userPort.findByDocument(user.getDocument());
        if (existingUserByDocument != null) {
            throw new Exception("Ya existe un usuario con el documento " + user.getDocument());
        }
        
        User existingUserByUserName = userPort.findByUserName(user.getUserName());
        if (existingUserByUserName != null) {
            throw new Exception("Ya existe un usuario con el nombre de usuario " + user.getUserName());
        }
        
        user.setId(null);
        
        userPort.save(user);
    }
}
