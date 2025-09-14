package app.adapter.in.client;

import app.adapter.in.util.InputReader;
import app.application.usecases.HumanResourcesUseCase;
import app.domain.model.User;
import app.domain.model.enums.Role;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class HumanResourcesCliMenu implements CliMenu {

    private final HumanResourcesUseCase hrUseCase;

    public HumanResourcesCliMenu(HumanResourcesUseCase hrUseCase) {
        this.hrUseCase = hrUseCase;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Menú Recursos Humanos ---");
        System.out.println("1. Crear usuario (Empleado)");
        System.out.println("2. Eliminar usuario (Empleado)");
        System.out.println("3. Actualizar datos de empleado");
        System.out.println("4. Volver al menú principal");
    }

    @Override
    public void handleOption(String option) {
        try {
            switch (option) {
                case "1":
                    crearUsuarioEmpleado();
                    break;
                case "2":
                    eliminarUsuarioEmpleado();
                    break;
                case "3":
                    actualizarDatosEmpleado();
                    break;
                case "4":
                    return; 
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearUsuarioEmpleado() throws Exception {
        System.out.println("\n--- Crear Nuevo Usuario Empleado ---");
        User user = new User();

        user.setFullName(InputReader.readString("Nombre completo: "));
        user.setDocument(InputReader.readLong("Cédula (número): "));
        user.setEmail(InputReader.readString("Correo electrónico: "));
        user.setPhoneNumber(InputReader.readString("Teléfono (1-10 dígitos): "));
        user.setDateOfBirth(InputReader.readDate("Fecha de nacimiento (YYYY-MM-DD): "));
        user.setAddress(InputReader.readString("Dirección (máx 30 caracteres): "));
        user.setGender(InputReader.readString("Género (M/F/Otro): "));
        user.setUserName(InputReader.readString("Nombre de usuario (máx 15 caracteres, solo letras y números): "));
        user.setPassword(InputReader.readString("Contraseña (mín 8 caracteres, 1 mayúscula, 1 número, 1 especial): "));

        String rolStr = InputReader.readString("Seleccione Rol (MEDICO, ENFERMERA, PERSONAL_ADMINISTRATIVO, SOPORTE_INFORMACION, RECURSOS_HUMANOS): ").toUpperCase();
        try {
            user.setRole(Role.valueOf(rolStr)); 
        } catch (IllegalArgumentException e) {
            System.out.println("Rol inválido. Asignando PERSONAL_ADMINISTRATIVO por defecto.");
            user.setRole(Role.PERSONAL_ADMINISTRATIVO); 
        }

        switch (user.getRole()) {
            case MEDICO:
                hrUseCase.createDoctor(user);
                break;
            case ENFERMERA:
                hrUseCase.createNurse(user);
                break;
            case PERSONAL_ADMINISTRATIVO:
                hrUseCase.createAdministrativeStaff(user);
                break;
            case SOPORTE_INFORMACION:
                hrUseCase.createInformationSupport(user);
                break;
            case RECURSOS_HUMANOS:
                hrUseCase.createAdministrativeStaff(user); 
                break;
            default:
                System.out.println("Rol no reconocido, no se pudo crear el usuario.");
                return;
        }
        System.out.println("Usuario empleado creado exitosamente.");
    }

    private void eliminarUsuarioEmpleado() throws Exception {
        System.out.println("\n--- Eliminar Usuario Empleado ---");
        Long document = InputReader.readLong("Ingrese cédula del usuario a eliminar: ");
        hrUseCase.deleteUser(document);
        System.out.println("Usuario empleado eliminado exitosamente.");
    }

    private void actualizarDatosEmpleado() throws Exception {
        System.out.println("\n--- Actualizar Datos de Empleado ---");
        Long document = InputReader.readLong("Ingrese cédula del empleado a actualizar: ");

        User existingUser = hrUseCase.findUserByDocument(document);
        if (existingUser == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        System.out.println("Deje en blanco los campos que no desea actualizar.");
        String fullName = InputReader.readString("Nombre completo (" + existingUser.getFullName() + "): ");
        if (!fullName.isEmpty()) existingUser.setFullName(fullName);

        String email = InputReader.readString("Correo electrónico (" + existingUser.getEmail() + "): ");
        if (!email.isEmpty()) existingUser.setEmail(email);

        String phone = InputReader.readString("Teléfono (" + existingUser.getPhoneNumber() + "): ");
        if (!phone.isEmpty()) existingUser.setPhoneNumber(phone);

        String address = InputReader.readString("Dirección (" + existingUser.getAddress() + "): ");
        if (!address.isEmpty()) existingUser.setAddress(address);

        String dobStr = InputReader.readString("Fecha de nacimiento (YYYY-MM-DD) (" + existingUser.getDateOfBirth() + "): ");
        if (!dobStr.isEmpty()) existingUser.setDateOfBirth(Date.valueOf(dobStr));

        String gender = InputReader.readString("Género (M/F/Otro) (" + existingUser.getGender() + "): ");
        if (!gender.isEmpty()) existingUser.setGender(gender);

        hrUseCase.updateUserPersonalData(existingUser);
        System.out.println("Datos de empleado actualizados exitosamente.");
    }
}
