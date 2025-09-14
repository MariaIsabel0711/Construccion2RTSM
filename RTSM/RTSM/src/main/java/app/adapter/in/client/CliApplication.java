package app.adapter.in.client;

import app.adapter.in.util.InputReader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CliApplication {

    private final Map<String, CliMenu> menus;

    public CliApplication(HumanResourcesCliMenu hrMenu,
                          AdministrativeStaffCliMenu adminMenu,
                          InformationSupportCliMenu supportMenu,
                          NurseCliMenu nurseMenu,
                          DoctorCliMenu doctorMenu) {
        this.menus = new HashMap<>();
        menus.put("1", hrMenu);
        menus.put("2", adminMenu);
        menus.put("3", supportMenu);
        menus.put("4", nurseMenu);
        menus.put("5", doctorMenu);
    }

    public void start() {
        System.out.println("Bienvenido al Sistema de Gestión de la Clínica... ");

        while (true) {
            System.out.println("\nSeleccione su rol:");
            System.out.println("1. Recursos Humanos");
            System.out.println("2. Personal Administrativo");
            System.out.println("3. Soporte de Información");
            System.out.println("4. Enfermera");
            System.out.println("5. Médico");
            System.out.println("6. Salir");

            String option = InputReader.readString("Ingrese opción: ");

            if (option.equals("6")) {
                System.out.println("Gracias por usar el sistema");
                break;
            }

            CliMenu selectedMenu = menus.get(option);
            if (selectedMenu != null) {
                runSubMenu(selectedMenu);
            } else {
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private void runSubMenu(CliMenu menu) {
        String subOption;
        do {
            menu.displayMenu();
            subOption = InputReader.readString("Ingrese opción: ");
            if (!subOption.equals("4") && !subOption.equals("5") && !subOption.equals("6")) { 
                menu.handleOption(subOption);
            }
        } while (!subOption.equals("4") && !subOption.equals("5") && !subOption.equals("6")); 
    }
}
