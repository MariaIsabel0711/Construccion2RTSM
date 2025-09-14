package app.application.usecases;

import app.domain.model.Patient;
import app.domain.model.ClinicalRecord;
import app.domain.model.ClinicalOrder;
import app.domain.services.SearchClinicalRecordByPatient;
import app.domain.services.SearchClinicalOrderByPatient;
import app.domain.services.CreateClinicalRecord;
import app.domain.ports.PatientPort;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.ClinicalOrderPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;

@Service
public class NurseUseCase {

    private PatientPort patientPort;
    private SearchClinicalRecordByPatient searchClinicalRecord;
    private SearchClinicalOrderByPatient searchClinicalOrder;
    private CreateClinicalRecord createClinicalRecord;

    public NurseUseCase(PatientPort patientPort, ClinicalRecordPort clinicalRecordPort, ClinicalOrderPort clinicalOrderPort) {
        this.patientPort = patientPort;
        this.searchClinicalRecord = new SearchClinicalRecordByPatient(clinicalRecordPort);
        this.searchClinicalOrder = new SearchClinicalOrderByPatient(clinicalOrderPort);
        this.createClinicalRecord = new CreateClinicalRecord(clinicalRecordPort);
    }

    public Patient getPatientInfo(Long patientDocument) {
        return patientPort.findByDocument(patientDocument);
    }

    public List<ClinicalRecord> getPatientClinicalHistory(Long patientDocument) throws Exception {
        return searchClinicalRecord.searchByDocument(patientDocument);
    }

    public List<ClinicalOrder> getPatientOrders(Long patientDocument) throws Exception {
        return searchClinicalOrder.searchByDocument(patientDocument);
    }

    public void registerVitalSigns(Long patientDocument, String bloodPressure, double temperature, int pulse, double oxygenLevel) throws Exception {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new Exception("El paciente con documento " + patientDocument + " no existe.");
        }
        if (bloodPressure == null || bloodPressure.isEmpty()) throw new Exception("La presión arterial es obligatoria.");
        if (temperature <= 0) throw new Exception("La temperatura debe ser un valor positivo.");
        if (pulse <= 0) throw new Exception("El pulso debe ser un valor positivo.");
        if (oxygenLevel <= 0) throw new Exception("El nivel de oxígeno debe ser un valor positivo.");


        Date today = new Date(System.currentTimeMillis());
        ClinicalRecord clinicalRecord = searchClinicalRecord.searchByDocumentAndDate(patientDocument, today);

        if (clinicalRecord == null) {
            clinicalRecord = new ClinicalRecord();
            clinicalRecord.setPatientDocument(patientDocument);
            clinicalRecord.setAttentionDate(today);
            clinicalRecord.setRecordDetails(new HashMap<>());
        }

        Map<String, Object> recordDetails = clinicalRecord.getRecordDetails();
        recordDetails.put("bloodPressure", bloodPressure);
        recordDetails.put("temperature", temperature);
        recordDetails.put("pulse", pulse);
        recordDetails.put("oxygenLevel", oxygenLevel);
        recordDetails.put("lastVitalSignsUpdate", new Date(System.currentTimeMillis()).toString());

        createClinicalRecord.create(clinicalRecord);
    }

    public void registerMedicationAdministered(Long patientDocument, long orderNumber, String medicationName, String dose) throws Exception {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new Exception("El paciente con documento " + patientDocument + " no existe.");
        }
        if (medicationName == null || medicationName.isEmpty()) throw new Exception("El nombre del medicamento es obligatorio.");
        if (dose == null || dose.isEmpty()) throw new Exception("La dosis es obligatoria.");

        List<ClinicalOrder> orders = searchClinicalOrder.searchByDocument(patientDocument);
        boolean orderExists = orders.stream().anyMatch(order -> order.getOrderNumber() == orderNumber);
        if (!orderExists) {
            throw new Exception("La orden número " + orderNumber + " no existe para este paciente.");
        }

        Date today = new Date(System.currentTimeMillis());
        ClinicalRecord clinicalRecord = searchClinicalRecord.searchByDocumentAndDate(patientDocument, today);

        if (clinicalRecord == null) {
            clinicalRecord = new ClinicalRecord();
            clinicalRecord.setPatientDocument(patientDocument);
            clinicalRecord.setAttentionDate(today);
            clinicalRecord.setRecordDetails(new HashMap<>());
        }

        Map<String, Object> recordDetails = clinicalRecord.getRecordDetails();
        @SuppressWarnings("unchecked") 
        List<Map<String, Object>> administeredMedications = (List<Map<String, Object>>) recordDetails.getOrDefault("administeredMedications", new ArrayList<>());

        Map<String, Object> administeredMed = new HashMap<>();
        administeredMed.put("orderNumber", orderNumber);
        administeredMed.put("medicationName", medicationName);
        administeredMed.put("dose", dose);
        administeredMed.put("administrationTime", new Date(System.currentTimeMillis()).toString());

        administeredMedications.add(administeredMed);
        recordDetails.put("administeredMedications", administeredMedications);

        createClinicalRecord.create(clinicalRecord);
    }

    public void registerProcedurePerformed(Long patientDocument, long orderNumber, String procedureName, String details) throws Exception {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new Exception("El paciente con documento " + patientDocument + " no existe.");
        }
        if (procedureName == null || procedureName.isEmpty()) throw new Exception("El nombre del procedimiento es obligatorio.");

        List<ClinicalOrder> orders = searchClinicalOrder.searchByDocument(patientDocument);
        boolean orderExists = orders.stream().anyMatch(order -> order.getOrderNumber() == orderNumber);
        if (!orderExists) {
            throw new Exception("La orden número " + orderNumber + " no existe para este paciente.");
        }

        Date today = new Date(System.currentTimeMillis());
        ClinicalRecord clinicalRecord = searchClinicalRecord.searchByDocumentAndDate(patientDocument, today);

        if (clinicalRecord == null) {
            clinicalRecord = new ClinicalRecord();
            clinicalRecord.setPatientDocument(patientDocument);
            clinicalRecord.setAttentionDate(today);
            clinicalRecord.setRecordDetails(new HashMap<>());
        }

        Map<String, Object> recordDetails = clinicalRecord.getRecordDetails();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> performedProcedures = (List<Map<String, Object>>) recordDetails.getOrDefault("performedProcedures", new ArrayList<>());

        Map<String, Object> performedProc = new HashMap<>();
        performedProc.put("orderNumber", orderNumber);
        performedProc.put("procedureName", procedureName);
        performedProc.put("details", details);
        performedProc.put("performanceTime", new Date(System.currentTimeMillis()).toString());

        performedProcedures.add(performedProc);
        recordDetails.put("performedProcedures", performedProcedures);

        createClinicalRecord.create(clinicalRecord);
    }

    public void addObservationsToClinicalRecord(Long patientDocument, String observations) throws Exception {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new Exception("El paciente con documento " + patientDocument + " no existe.");
        }
        if (observations == null || observations.isEmpty()) {
            throw new Exception("Las observaciones no pueden estar vacías.");
        }

        Date today = new Date(System.currentTimeMillis());
        ClinicalRecord clinicalRecord = searchClinicalRecord.searchByDocumentAndDate(patientDocument, today);

        if (clinicalRecord == null) {
            clinicalRecord = new ClinicalRecord();
            clinicalRecord.setPatientDocument(patientDocument);
            clinicalRecord.setAttentionDate(today);
            clinicalRecord.setRecordDetails(new HashMap<>());
        }

        Map<String, Object> recordDetails = clinicalRecord.getRecordDetails();
        @SuppressWarnings("unchecked")
        List<String> existingObservations = (List<String>) recordDetails.getOrDefault("observations", new ArrayList<>());
        existingObservations.add(observations + " (Registrado por enfermera el " + new Date(System.currentTimeMillis()).toString() + ")");
        recordDetails.put("observations", existingObservations);

        createClinicalRecord.create(clinicalRecord);
    }
}
