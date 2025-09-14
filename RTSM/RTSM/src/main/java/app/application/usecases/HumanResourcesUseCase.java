package app.application.usecases;

import app.domain.model.User;
import app.domain.model.enums.Role;
import app.domain.ports.UserPort;
import app.domain.services.CreateUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanResourcesUseCase {

    private UserPort userPort;
    private CreateUser createUser;

    public HumanResourcesUseCase(UserPort userPort) {
        this.userPort = userPort;
        this.createUser = new CreateUser(userPort);
    }

    public void createDoctor(User user) throws Exception {
        user.setRole(Role.MEDICO); 
        createUser.create(user);
    }

    public void createNurse(User user) throws Exception {
        user.setRole(Role.ENFERMERA); 
        createUser.create(user);
    }

    public void createAdministrativeStaff(User user) throws Exception {
        user.setRole(Role.PERSONAL_ADMINISTRATIVO); 
        createUser.create(user);
    }

    public void createInformationSupport(User user) throws Exception {
        user.setRole(Role.SOPORTE_INFORMACION); 
        createUser.create(user);
    }

    public void deleteUser(Long document) throws Exception {
        User user = userPort.findByDocument(document);
        if (user == null) {
            throw new Exception("Usuario no encontrado con documento: " + document);
        }
        userPort.delete(user);
    }

    public User findUserByDocument(Long document) {
        return userPort.findByDocument(document);
    }

    public User findUserByUserName(String userName) {
        return userPort.findByUserName(userName);
    }

    public List<User> getAllUsers() {
        return userPort.findAll();
    }

    public void updateUserPersonalData(User user) throws Exception {
        if (user == null) {
            throw new Exception("El usuario no puede ser nulo.");
        }
        User existingUser = userPort.findByDocument(user.getDocument());
        if (existingUser == null) {
            throw new Exception("El usuario no existe en el sistema.");
        }
        userPort.save(user);
    }
}
