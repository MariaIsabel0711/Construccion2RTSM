package app.application.usecases;

import app.domain.model.ClinicalRecord;
import app.domain.model.ClinicalOrder;
import app.domain.model.Patient;
import app.domain.model.Medicationrecord;
import app.domain.model.ProcedireRecord;
import app.domain.model.DiagnosticRecord;
import app.domain.services.CreateClinicalRecord;
import app.domain.services.CreateClinicalOrder;
import app.domain.services.SearchClinicalRecordByPatient;
import app.domain.services.SearchClinicalOrderByPatient;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.ClinicalOrderPort;
import app.domain.ports.PatientPort;
import app.infrastructure.entity.ClinicalOrderEntity;
import app.infrastructure.entity.MedicationRecordEntity;
import app.infrastructure.entity.ProcedureRecordEntity;
import app.infrastructure.entity.DiagnosticRecordEntity;
import app.infrastructure.repository.MedicationRecordJpaRepository;
import app.infrastructure.repository.ProcedureRecordJpaRepository;
import app.infrastructure.repository.DiagnosticRecordJpaRepository;
import app.infrastructure.mapper.MedicationRecordMapper;
import app.infrastructure.mapper.ProcedureRecordMapper;
import app.infrastructure.mapper.DiagnosticRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.Date;
import java.util.HashMap;

@Service
public class DoctorUseCase {

    private final CreateClinicalRecord createClinicalRecord;
    private final CreateClinicalOrder createClinicalOrder;
    private final SearchClinicalRecordByPatient searchClinicalRecord;
    private final SearchClinicalOrderByPatient searchClinicalOrder;
    private final PatientPort patientPort;
    private final ClinicalOrderPort clinicalOrderPort;
    private final MedicationRecordJpaRepository medicationRecordJpaRepository;
    private final ProcedureRecordJpaRepository procedureRecordJpaRepository;
    private final DiagnosticRecordJpaRepository diagnosticRecordJpaRepository;
    private final MedicationRecordMapper medicationRecordMapper;
    private final ProcedureRecordMapper procedureRecordMapper;
    private final DiagnosticRecordMapper diagnosticRecordMapper;

    public DoctorUseCase(ClinicalRecordPort clinicalRecordPort, 
                         ClinicalOrderPort clinicalOrderPort, 
                         PatientPort patientPort,
                         MedicationRecordJpaRepository medicationRecordJpaRepository,
                         ProcedureRecordJpaRepository procedureRecordJpaRepository,
                         DiagnosticRecordJpaRepository diagnosticRecordJpaRepository,
                         MedicationRecordMapper medicationRecordMapper,
                         ProcedureRecordMapper procedureRecordMapper,
                         DiagnosticRecordMapper diagnosticRecordMapper) {
        this.patientPort = patientPort;
        this.clinicalOrderPort = clinicalOrderPort;
        this.createClinicalRecord = new CreateClinicalRecord(clinicalRecordPort);
        this.createClinicalOrder = new CreateClinicalOrder(clinicalOrderPort);
        this.searchClinicalRecord = new SearchClinicalRecordByPatient(clinicalRecordPort);
        this.searchClinicalOrder = new SearchClinicalOrderByPatient(clinicalOrderPort);
        this.medicationRecordJpaRepository = medicationRecordJpaRepository;
        this.procedureRecordJpaRepository = procedureRecordJpaRepository;
        this.diagnosticRecordJpaRepository = diagnosticRecordJpaRepository;
        this.medicationRecordMapper = medicationRecordMapper;
        this.procedureRecordMapper = procedureRecordMapper;
        this.diagnosticRecordMapper = diagnosticRecordMapper;
    }

    public void createClinicalRecord(ClinicalRecord clinicalRecord) throws Exception {
        Patient patient = patientPort.findByDocument(clinicalRecord.getPatientDocument());
        if (patient == null) {
            throw new Exception("El paciente con documento " + clinicalRecord.getPatientDocument() + " no existe en el sistema.");
        }
        createClinicalRecord.create(clinicalRecord);
    }

    public void updateClinicalRecord(ClinicalRecord clinicalRecord) throws Exception {
        if (clinicalRecord.getPatientDocument() == null || clinicalRecord.getAttentionDate() == null) {
            throw new Exception("Documento del paciente y fecha de atención son obligatorios para actualizar el registro clínico.");
        }
        ClinicalRecord existingRecord = searchClinicalRecord.searchByDocumentAndDate(
            clinicalRecord.getPatientDocument(),
            clinicalRecord.getAttentionDate()
        );
        if (existingRecord == null) {
            throw new Exception("El registro clínico para el paciente " + clinicalRecord.getPatientDocument() +
                                " en la fecha " + clinicalRecord.getAttentionDate() + " no existe.");
        }
        existingRecord.setRecordDetails(clinicalRecord.getRecordDetails());
        createClinicalRecord.create(existingRecord);
    }

    public List<ClinicalRecord> getClinicalHistory(Long patientDocument) throws Exception {
        if (patientDocument == null) {
            throw new Exception("El documento del paciente es obligatorio para obtener la historia clínica.");
        }
        return searchClinicalRecord.searchByDocument(patientDocument);
    }

    public void createMedicalOrder(ClinicalOrder order, List<Medicationrecord> medications,
                                   List<ProcedireRecord> procedures, List<DiagnosticRecord> diagnostics) throws Exception {
        Patient patient = patientPort.findByDocument(order.getPatientDocument());
        if (patient == null) {
            throw new Exception("El paciente con documento " + order.getPatientDocument() + " no existe.");
        }

        boolean hasDiagnostics = diagnostics != null && !diagnostics.isEmpty();
        boolean hasMedications = medications != null && !medications.isEmpty();
        boolean hasProcedures = procedures != null && !procedures.isEmpty();

        if (hasDiagnostics && (hasMedications || hasProcedures)) {
            throw new Exception("Si se receta una ayuda diagnóstica, NO se puede recetar medicamento ni procedimiento en la misma orden.");
        }

        if (!hasMedications && !hasProcedures && !hasDiagnostics) {
            throw new Exception("La orden debe contener al menos un medicamento, procedimiento o ayuda diagnóstica.");
        }

        if (hasMedications && medications.stream().map(Medicationrecord::getItemNumber).distinct().count() != medications.size()) {
            throw new Exception("Los números de ítem de los medicamentos deben ser únicos dentro de la orden.");
        }
        if (hasProcedures && procedures.stream().map(ProcedireRecord::getItemNumber).distinct().count() != procedures.size()) {
            throw new Exception("Los números de ítem de los procedimientos deben ser únicos dentro de la orden.");
        }
        if (hasDiagnostics && diagnostics.stream().map(DiagnosticRecord::getItemNumber).distinct().count() != diagnostics.size()) {
            throw new Exception("Los números de ítem de las ayudas diagnósticas deben ser únicos dentro de la orden.");
        }

        createClinicalOrder.create(order);

        ClinicalOrder savedOrder = clinicalOrderPort.findByOrderNumber(order.getOrderNumber());
        if (savedOrder == null) {
            throw new Exception("Error al recuperar la orden creada.");
        }

        Optional<ClinicalOrderEntity> orderEntityOptional = ((app.infrastructure.adapter.ClinicalOrderJpaAdapter) clinicalOrderPort)
                                            .clinicalOrderJpaRepository.findById(order.getOrderNumber());

        if (!orderEntityOptional.isPresent()) {
            throw new Exception("Error al recuperar la orden creada para asociar los ítems.");
        }
        ClinicalOrderEntity orderEntity = orderEntityOptional.get();

        if (hasMedications) {
            for (Medicationrecord med : medications) {
                MedicationRecordEntity medEntity = medicationRecordMapper.toEntity(med);
                medEntity.setClinicalOrder(orderEntity);
                medicationRecordJpaRepository.save(medEntity);
            }
        }

        if (hasProcedures) {
            for (ProcedireRecord proc : procedures) {
                ProcedureRecordEntity procEntity = procedureRecordMapper.toEntity(proc);
                procEntity.setClinicalOrder(orderEntity);
                procedureRecordJpaRepository.save(procEntity);
            }
        }

        if (hasDiagnostics) {
            for (DiagnosticRecord diag : diagnostics) {
                DiagnosticRecordEntity diagEntity = diagnosticRecordMapper.toEntity(diag);
                diagEntity.setClinicalOrder(orderEntity);
                diagnosticRecordJpaRepository.save(diagEntity);
            }
        }
    }

    public List<ClinicalOrder> getPatientOrders(Long patientDocument) throws Exception {
        if (patientDocument == null) {
            throw new Exception("El documento del paciente es obligatorio para obtener las órdenes.");
        }
        return searchClinicalOrder.searchByDocument(patientDocument);
    }

    public void processDiagnosticAidResults(Long patientDocument, Date attentionDate,
                                            Map<String, Object> newRecordDetails,
                                            List<Medicationrecord> newMedications,
                                            List<ProcedireRecord> newProcedures,
                                            List<DiagnosticRecord> newDiagnostics) throws Exception {

        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new Exception("El paciente con documento " + patientDocument + " no existe.");
        }
        if (attentionDate == null) {
            throw new Exception("La fecha de atención es obligatoria para procesar resultados.");
        }
        if (newRecordDetails == null || newRecordDetails.isEmpty()) {
            throw new Exception("Los detalles del nuevo registro clínico son obligatorios.");
        }

        ClinicalRecord newClinicalRecord = new ClinicalRecord();
        newClinicalRecord.setPatientDocument(patientDocument);
        newClinicalRecord.setAttentionDate(attentionDate);
        newClinicalRecord.setRecordDetails(newRecordDetails);
        createClinicalRecord(newClinicalRecord);

        if ((newMedications != null && !newMedications.isEmpty()) ||
            (newProcedures != null && !newProcedures.isEmpty()) ||
            (newDiagnostics != null && !newDiagnostics.isEmpty())) {

            long newOrderNumber = System.currentTimeMillis();
            ClinicalOrder newOrder = new ClinicalOrder();
            newOrder.setOrderNumber(newOrderNumber);
            newOrder.setPatientDocument(patientDocument);
            newOrder.setMedicalDocument(newRecordDetails.containsKey("medicalDocument") ? (Long) newRecordDetails.get("medicalDocument") : null);
            newOrder.setCreationDate(attentionDate);

            createMedicalOrder(newOrder, newMedications, newProcedures, newDiagnostics);
        }
    }

    public void registerDiagnosisAndTreatment(Long patientDocument, Date attentionDate, String diagnosis, String treatment) throws Exception {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new Exception("El paciente con documento " + patientDocument + " no existe.");
        }
        if (attentionDate == null) {
            throw new Exception("La fecha de atención es obligatoria.");
        }
        if (diagnosis == null || diagnosis.isEmpty()) {
            throw new Exception("El diagnóstico es obligatorio.");
        }
        if (treatment == null || treatment.isEmpty()) {
            throw new Exception("El tratamiento es obligatorio.");
        }

        ClinicalRecord clinicalRecord = searchClinicalRecord.searchByDocumentAndDate(patientDocument, attentionDate);

        if (clinicalRecord == null) {
            clinicalRecord = new ClinicalRecord();
            clinicalRecord.setPatientDocument(patientDocument);
            clinicalRecord.setAttentionDate(attentionDate);
            clinicalRecord.setRecordDetails(new HashMap<>());
        }

        Map<String, Object> recordDetails = clinicalRecord.getRecordDetails();
        recordDetails.put("diagnosis", diagnosis);
        recordDetails.put("treatment", treatment);
        recordDetails.put("lastDiagnosisUpdate", new Date(System.currentTimeMillis()).toString());

        createClinicalRecord.create(clinicalRecord);
    }
}
