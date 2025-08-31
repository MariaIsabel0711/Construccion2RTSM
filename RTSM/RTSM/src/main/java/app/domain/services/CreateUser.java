package app.domain.services;

import app.domain.model.User;
import app.domain.ports.UserPort;

public class CreateUser {

    private UserPort userPort;

    public CreateUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public void create(User user) throws Exception {

        
        if (user.getUserName() == null || !user.getUserName().matches("^[a-zA-Z0-9]{1,15}$")) {
            throw new Exception("Nombre de usuario inválido. Debe contener solo letras y números, máximo 15 caracteres.");
        }
        if (userPort.findByUserName(user.getUserName()) != null) {
            throw new Exception("El nombre de usuario ya existe.");
        }

        String password = user.getPassword();
        if (password == null || !password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
            throw new Exception("Contraseña inválida. Debe incluir una mayúscula, un número, un carácter especial y tener al menos 8 caracteres.");
        }

        String email = user.getEmail();
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new Exception("Correo electrónico inválido.");
        }

        if (user.getDocument() == null) {
            throw new Exception("Número de cédula es obligatorio.");
        }
        if (userPort.findByDocument(user.getDocument()) != null) { // findByDocument espera Long, user.getDocument() devuelve Long
            throw new Exception("Número de cédula ya registrado.");
        }

        String phone = user.getPhoneNumber();
        if (phone == null || !phone.matches("^\\d{1,10}$")) {
            throw new Exception("Número de teléfono inválido. Debe contener entre 1 y 10 dígitos.");
        }

        if (user.getDateOfBirth() == null) {
            throw new Exception("Fecha de nacimiento es obligatoria.");
        }
        java.util.Date now = new java.util.Date();
        long ageInMillis = now.getTime() - user.getDateOfBirth().getTime();
        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
        if (ageInYears > 150) {
            throw new Exception("Fecha de nacimiento inválida. Edad máxima permitida: 150 años.");
        }

        if (user.getRole() == null) {
            throw new Exception("Rol es obligatorio.");
        }

        userPort.save(user);
    }
}
