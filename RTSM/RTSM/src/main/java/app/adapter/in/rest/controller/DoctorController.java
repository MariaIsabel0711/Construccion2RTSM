package app.adapter.in.rest.controller;

import app.adapter.in.builder.ClinicalOrderBuilder;
import app.adapter.in.builder.ClinicalRecordBuilder;
import app.adapter.in.builder.DiagnosticRecordBuilder;
import app.adapter.in.builder.MedicationRecordBuilder;
import app.adapter.in.builder.ProcedureRecordBuilder;
import app.adapter.in.rest.request.ClinicalOrderRequest;
import app.adapter.in.rest.request.ClinicalRecordRequest;
import app.adapter.in.rest.request.DiagnosisTreatmentRequest;
import app.application.usecases.DoctorUseCase;
import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.DiagnosticRecord;
import app.domain.model.Medicationrecord;
import app.domain.model.ProcedireRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorController {

    private final DoctorUseCase doctorUseCase;
    private final ClinicalRecordBuilder clinicalRecordBuilder;
    private final ClinicalOrderBuilder clinicalOrderBuilder;
    private final MedicationRecordBuilder medicationRecordBuilder;
    private final ProcedureRecordBuilder procedureRecordBuilder;
    private final DiagnosticRecordBuilder diagnosticRecordBuilder;

    public DoctorController(DoctorUseCase doctorUseCase,
                            ClinicalRecordBuilder clinicalRecordBuilder,
                            ClinicalOrderBuilder clinicalOrderBuilder,
                            MedicationRecordBuilder medicationRecordBuilder,
                            ProcedureRecordBuilder procedureRecordBuilder,
                            DiagnosticRecordBuilder diagnosticRecordBuilder) {
        this.doctorUseCase = doctorUseCase;
        this.clinicalRecordBuilder = clinicalRecordBuilder;
        this.clinicalOrderBuilder = clinicalOrderBuilder;
        this.medicationRecordBuilder = medicationRecordBuilder;
        this.procedureRecordBuilder = procedureRecordBuilder;
        this.diagnosticRecordBuilder = diagnosticRecordBuilder;
    }

    // Crear historia clínica
    @PostMapping("/clinical-records")
    public ResponseEntity<ClinicalRecord> createClinicalRecord(@RequestBody ClinicalRecordRequest req) throws Exception {
        Date attentionDate = Date.valueOf(req.getAttentionDate());
        ClinicalRecord record = clinicalRecordBuilder.build(
                req.getPatientDocument(),
                attentionDate,
                req.getMedicalDocument(),
                req.getMotivoConsulta(),
                req.getSintomas(),
                req.getDiagnostico()
        );
        // Caso de uso retorna void
        doctorUseCase.createClinicalRecord(record);
        return ResponseEntity.created(
                        URI.create("/api/clinical-records/" + record.getPatientDocument() + "/" + record.getAttentionDate()))
                .body(record);
    }

    // Actualizar historia clínica
    @PutMapping("/clinical-records")
    public ResponseEntity<ClinicalRecord> updateClinicalRecord(@RequestBody ClinicalRecordRequest req) throws Exception {
        Date attentionDate = Date.valueOf(req.getAttentionDate());
        ClinicalRecord record = clinicalRecordBuilder.build(
                req.getPatientDocument(),
                attentionDate,
                req.getMedicalDocument(),
                req.getMotivoConsulta(),
                req.getSintomas(),
                req.getDiagnostico()
        );
        // Caso de uso retorna void
        doctorUseCase.updateClinicalRecord(record);
        return ResponseEntity.ok(record);
    }

    // Registrar diagnóstico y tratamiento
    @PostMapping("/clinical-records/diagnosis")
    public ResponseEntity<Void> registerDiagnosisAndTreatment(@RequestBody DiagnosisTreatmentRequest req) throws Exception {
        Date attentionDate = Date.valueOf(req.getAttentionDate());
        doctorUseCase.registerDiagnosisAndTreatment(req.getPatientDocument(), attentionDate, req.getDiagnosis(), req.getTreatment());
        return ResponseEntity.ok().build();
    }

    // Crear orden médica con ítems
    @PostMapping("/orders")
    public ResponseEntity<Void> createOrder(@RequestBody ClinicalOrderRequest req) throws Exception {
        Date creationDate = Date.valueOf(req.getCreationDate());
        ClinicalOrder order = clinicalOrderBuilder.build(
                req.getOrderNumber(),
                req.getPatientDocument(),
                req.getMedicalDocument(),
                creationDate
        );

        List<Medicationrecord> medications = new ArrayList<>();
        if (req.getMedications() != null) {
            req.getMedications().forEach(m -> medications.add(
                    medicationRecordBuilder.build(
                            m.getItemNumber(),
                            m.getMedicationName(),
                            m.getDose(),
                            m.getTreatmentDuration(),
                            m.getCost()
                    )
            ));
        }

        List<ProcedireRecord> procedures = new ArrayList<>();
        if (req.getProcedures() != null) {
            req.getProcedures().forEach(p -> procedures.add(
                    procedureRecordBuilder.build(
                            p.getItemNumber(),
                            p.getProcedureName(),
                            p.getRepetitions(),
                            p.getFrequency(),
                            p.getCost(),
                            p.isRequiresSpecialist(),
                            p.getSpecialistRoleId()
                    )
            ));
        }

        List<DiagnosticRecord> diagnostics = new ArrayList<>();
        if (req.getDiagnostics() != null) {
            req.getDiagnostics().forEach(d -> diagnostics.add(
                    diagnosticRecordBuilder.build(
                            d.getItemNumber(),
                            d.getDiagnosticName(),
                            d.getQuantity(),
                            d.getCost(),
                            d.isRequiresSpecialist(),
                            d.getSpecialistRoleId()
                    )
            ));
        }

        doctorUseCase.createMedicalOrder(order, medications, procedures, diagnostics);
        return ResponseEntity.created(URI.create("/api/orders/" + req.getOrderNumber())).build();
    }
}
