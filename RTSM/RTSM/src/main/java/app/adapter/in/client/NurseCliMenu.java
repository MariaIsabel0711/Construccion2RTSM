package app.adapter.in.client;

import app.adapter.in.util.InputReader;
import app.application.usecases.NurseUseCase;
import org.springframework.stereotype.Component;

@Component
public class NurseCliMenu implements CliMenu {

    private final NurseUseCase nurseUseCase;

    public NurseCliMenu(NurseUseCase nurseUseCase) {
        this.nurseUseCase = nurseUseCase;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--- Menú Enfermera ---");
        System.out.println("1. Registrar signos vitales");
        System.out.println("2. Registrar medicamentos administrados");
        System.out.println("3. Registrar procedimientos realizados");
        System.out.println("4. Añadir observaciones a historia clínica");
        System.out.println("5. Volver al menú principal");
    }

    @Override
    public void handleOption(String option) {
        try {
            switch (option) {
                case "1":
                    registrarSignosVitales();
                    break;
                case "2":
                    registrarMedicamentosAdministrados();
                    break;
                case "3":
                    registrarProcedimientosRealizados();
                    break;
                case "4":
                    anadirObservacionesHistoriaClinica();
                    break;
                case "5":
                    return; 
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarSignosVitales() throws Exception {
        System.out.println("\n--- Registrar Signos Vitales ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        String bloodPressure = InputReader.readString("Presión arterial: ");
        double temperature = InputReader.readDouble("Temperatura: ");
        int pulse = InputReader.readInt("Pulso: ");
        double oxygenLevel = InputReader.readDouble("Nivel de oxígeno: ");

        nurseUseCase.registerVitalSigns(patientDocument, bloodPressure, temperature, pulse, oxygenLevel);
        System.out.println("Signos vitales registrados exitosamente.");
    }

    private void registrarMedicamentosAdministrados() throws Exception {
        System.out.println("\n--- Registrar Medicamentos Administrados ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        long orderNumber = InputReader.readLong("Ingrese número de orden: ");
        String medicationName = InputReader.readString("Nombre del medicamento administrado: ");
        String dose = InputReader.readString("Dosis administrada: ");

        nurseUseCase.registerMedicationAdministered(patientDocument, orderNumber, medicationName, dose);
        System.out.println("Medicamento administrado registrado exitosamente.");
    }

    private void registrarProcedimientosRealizados() throws Exception {
        System.out.println("\n--- Registrar Procedimientos Realizados ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        long orderNumber = InputReader.readLong("Ingrese número de orden: ");
        String procedureName = InputReader.readString("Nombre del procedimiento realizado: ");
        String details = InputReader.readString("Detalles del procedimiento: ");

        nurseUseCase.registerProcedurePerformed(patientDocument, orderNumber, procedureName, details);
        System.out.println("Procedimiento realizado registrado exitosamente.");
    }

    private void anadirObservacionesHistoriaClinica() throws Exception {
        System.out.println("\n--- Añadir Observaciones a Historia Clínica ---");
        Long patientDocument = InputReader.readLong("Ingrese cédula del paciente: ");
        String observations = InputReader.readString("Observaciones: ");

        nurseUseCase.addObservationsToClinicalRecord(patientDocument, observations);
        System.out.println("Observaciones añadidas exitosamente.");
    }
}
