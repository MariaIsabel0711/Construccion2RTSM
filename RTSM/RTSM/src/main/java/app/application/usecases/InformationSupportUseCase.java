package app.application.usecases;

import app.domain.model.User;
import app.domain.model.MedicationInventory;
import app.domain.model.ProcedireRecord;
import app.domain.model.DiagnosticRecord;
import app.domain.ports.UserPort;
import app.domain.ports.MedicationInventoryPort;
import app.domain.ports.ProcedureInventoryPort;
import app.domain.ports.DiagnosticAidInventoryPort;
import app.domain.services.ManageMedicationInventory;
import app.domain.services.ManageProcedureInventory;
import app.domain.services.ManageDiagnosticAidInventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationSupportUseCase {

    private UserPort userPort;
    private ManageMedicationInventory manageMedicationInventory;
    private ManageProcedureInventory manageProcedureInventory;
    private ManageDiagnosticAidInventory manageDiagnosticAidInventory;

    private MedicationInventoryPort medicationInventoryPort;
    private ProcedureInventoryPort procedureInventoryPort;
    private DiagnosticAidInventoryPort diagnosticAidInventoryPort;


    public InformationSupportUseCase(UserPort userPort,
                                     MedicationInventoryPort medicationInventoryPort,
                                     ProcedureInventoryPort procedureInventoryPort,
                                     DiagnosticAidInventoryPort diagnosticAidInventoryPort) {
        this.userPort = userPort;
        this.medicationInventoryPort = medicationInventoryPort; 
        this.procedureInventoryPort = procedureInventoryPort;   
        this.diagnosticAidInventoryPort = diagnosticAidInventoryPort; 

        this.manageMedicationInventory = new ManageMedicationInventory(medicationInventoryPort);
        this.manageProcedureInventory = new ManageProcedureInventory(procedureInventoryPort);
        this.manageDiagnosticAidInventory = new ManageDiagnosticAidInventory(diagnosticAidInventoryPort);
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

    public void addMedicationItem(MedicationInventory item) throws Exception {
        manageMedicationInventory.addItem(item);
    }

    public void updateMedicationItem(MedicationInventory item) throws Exception {
        manageMedicationInventory.updateItem(item);
    }

    public void addMedicationStock(Long id, int quantity) throws Exception {
        manageMedicationInventory.addStock(id, quantity);
    }

    public void removeMedicationStock(Long id, int quantity) throws Exception {
        manageMedicationInventory.removeStock(id, quantity);
    }

    public MedicationInventory findMedicationById(Long id) {
        return medicationInventoryPort.findById(id);
    }

    public void addProcedureItem(ProcedireRecord item) throws Exception {
        manageProcedureInventory.addItem(item);
    }

    public void updateProcedureItem(ProcedireRecord item) throws Exception {
        manageProcedureInventory.updateItem(item);
    }

    public void addProcedureStock(Long id, int quantity) throws Exception {
        manageProcedureInventory.addStock(id, quantity);
    }

    public void removeProcedureStock(Long id, int quantity) throws Exception {
        manageProcedureInventory.removeStock(id, quantity);
    }

    public ProcedireRecord findProcedureById(Long id) {
        return procedureInventoryPort.findById(id);
    }

    public void addDiagnosticAidItem(DiagnosticRecord item) throws Exception {
        manageDiagnosticAidInventory.addItem(item);
    }

    public void updateDiagnosticAidItem(DiagnosticRecord item) throws Exception {
        manageDiagnosticAidInventory.updateItem(item);
    }

    public void addDiagnosticAidStock(Long id, int quantity) throws Exception {
        manageDiagnosticAidInventory.addStock(id, quantity);
    }

    public void removeDiagnosticAidStock(Long id, int quantity) throws Exception {
        manageDiagnosticAidInventory.removeStock(id, quantity);
    }

    public DiagnosticRecord findDiagnosticAidById(Long id) {
        return diagnosticAidInventoryPort.findById(id);
    }
}
