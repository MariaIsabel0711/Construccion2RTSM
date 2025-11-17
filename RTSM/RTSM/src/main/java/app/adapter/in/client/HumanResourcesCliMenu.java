package app.adapter.in.client;

import app.adapter.in.builder.UserBuilder;
import app.adapter.in.util.InputReader;
import app.application.usecases.HumanResourcesUseCase;
import app.domain.model.User;
import app.domain.model.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class HumanResourcesCliMenu implements CliMenu {

    private final HumanResourcesUseCase hrUseCase;
    private final UserBuilder userBuilder;

    public HumanResourcesCliMenu(HumanResourcesUseCase hrUseCase, UserBuilder userBuilder) {
        this.hrUseCase = hrUseCase;
        this.userBuilder = userBuilder;
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
                case "1" -> crearUsuarioEmpleado();
                case "2" -> eliminarUsuarioEmpleado();
                case "3" -> actualizarDatosEmpleado();
                case "4" -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearUsuarioEmpleado() throws Exception {
        System.out.println("\n--- Crear Nuevo Usuario Empleado ---");

        String fullName = InputReader.readString("Nombre completo: ");
        String document = InputReader.readString("Cédula: ");
        String email = InputReader.readString("Correo electrónico: ");
        String phone = InputReader.readString("Teléfono: ");
        String dob = InputReader.readString("Fecha de nacimiento (YYYY-MM-DD): ");
        String address = InputReader.readString("Dirección: ");
        String gender = InputReader.readString("Género (M/F/Otro): ");
        String userName = InputReader.readString("Nombre de usuario: ");
        String password = InputReader.readString("Contraseña: ");

        String rolStr = InputReader.readString("Seleccione Rol (MEDICO, ENFERMERA, PERSONAL_ADMINISTRATIVO, SOPORTE_INFORMACION, RECURSOS_HUMANOS): ").toUpperCase();
        Role role;
        try {
            role = Role.valueOf(rolStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Rol inválido. Asignando PERSONAL_ADMINISTRATIVO por defecto.");
            role = Role.PERSONAL_ADMINISTRATIVO;
        }

        User user = userBuilder.build(fullName, document, email, phone, dob, address, gender, userName, password, role);

        switch (user.getRole()) {
            case MEDICO -> hrUseCase.createDoctor(user);
            case ENFERMERA -> hrUseCase.createNurse(user);
            case PERSONAL_ADMINISTRATIVO -> hrUseCase.createAdministrativeStaff(user);
            case SOPORTE_INFORMACION -> hrUseCase.createInformationSupport(user);
            case RECURSOS_HUMANOS -> hrUseCase.createAdministrativeStaff(user);
            default -> {
                System.out.println("Rol no reconocido, no se pudo crear el usuario.");
                return;
            }
        }
        System.out.println("Usuario empleado creado exitosamente.");
    }

    private void eliminarUsuarioEmpleado() throws Exception {
        System.out.println("\n--- Eliminar Usuario Empleado ---");
        String document = InputReader.readString("Ingrese cédula del usuario a eliminar: ");
        hrUseCase.deleteUser(document);
        System.out.println("Usuario empleado eliminado exitosamente.");
    }

    private void actualizarDatosEmpleado() throws Exception {
        System.out.println("\n--- Actualizar Datos de Empleado ---");
        String document = InputReader.readString("Ingrese cédula del empleado a actualizar: ");

        User existingUser = hrUseCase.findUserByDocument(document);
        if (existingUser == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        System.out.println("Deje en blanco los campos que no desea actualizar.");
        String fullName = InputReader.readString("Nombre completo (" + existingUser.getFullName() + "): ");
        String email = InputReader.readString("Correo electrónico (" + existingUser.getEmail() + "): ");
        String phone = InputReader.readString("Teléfono (" + existingUser.getPhoneNumber() + "): ");
        String address = InputReader.readString("Dirección (" + existingUser.getAddress() + "): ");
        String dobStr = InputReader.readString("Fecha de nacimiento (YYYY-MM-DD) (" + existingUser.getDateOfBirth() + "): ");
        String gender = InputReader.readString("Género (M/F/Otro) (" + existingUser.getGender() + "): ");

        userBuilder.applyPersonalDataUpdates(existingUser, fullName, email, phone, address, dobStr, gender);
        hrUseCase.updateUserPersonalData(existingUser);
        System.out.println("Datos de empleado actualizados exitosamente.");
    }
}
