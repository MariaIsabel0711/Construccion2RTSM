package app.adapter.in.rest.controller;

import app.adapter.in.builder.InvoiceBuilder;
import app.adapter.in.builder.PatientBuilder;
import app.adapter.in.rest.request.AppointmentRequest;
import app.adapter.in.rest.request.InvoiceRequest;
import app.adapter.in.rest.request.PatientRequest;
import app.application.usecases.AdministrativeStaffUseCase;
import app.domain.model.Invoice;
import app.domain.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class AdministrativeStaffController {

    private final AdministrativeStaffUseCase adminUseCase;
    private final PatientBuilder patientBuilder;
    private final InvoiceBuilder invoiceBuilder;

    public AdministrativeStaffController(AdministrativeStaffUseCase adminUseCase,
                                         PatientBuilder patientBuilder,
                                         InvoiceBuilder invoiceBuilder) {
        this.adminUseCase = adminUseCase;
        this.patientBuilder = patientBuilder;
        this.invoiceBuilder = invoiceBuilder;
    }

    @PostMapping("/patients")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientRequest req) throws Exception {
        Date dob = req.getDateOfBirth() != null ? Date.valueOf(req.getDateOfBirth()) : null;
        Date policyEnd = req.getPolicyEndDate() != null ? Date.valueOf(req.getPolicyEndDate()) : null;

        Patient patient = patientBuilder.build(
                req.getFullName(),
                req.getDocument(),
                req.getEmail(),
                req.getPhoneNumber(),
                dob,
                req.getAddress(),
                req.getGender(),
                req.getEmergencyContactName(),
                req.getEmergencyContactRelationship(),
                req.getEmergencyContactPhoneNumber(),
                req.getInsuranceCompanyName(),
                req.getPolicyNumber(),
                req.isPolicyStatus(),
                policyEnd
        );

        adminUseCase.registerPatient(patient);
        return ResponseEntity.created(URI.create("/api/patients/" + patient.getDocument()))
                .body(patient);
    }

    @GetMapping("/patients/{document}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long document) {
        Patient patient = adminUseCase.findPatientByDocument(document);
        if (patient == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/appointments")
    public ResponseEntity<Void> scheduleAppointment(@RequestBody AppointmentRequest req) throws Exception {
        Date appointmentDate = Date.valueOf(req.getAppointmentDate()); // YYYY-MM-DD
        adminUseCase.scheduleAppointment(req.getPatientDocument(), appointmentDate, req.getDoctorDocument());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> generateInvoice(@RequestBody InvoiceRequest req) throws Exception {
        Date invoiceDate = req.getInvoiceDate() != null
                ? Date.valueOf(req.getInvoiceDate())
                : Date.valueOf(LocalDate.now());

        Invoice invoice = invoiceBuilder.build(
                req.getPatientDocument(),
                req.getMedicalDocument(),
                req.getTotalAmount(),
                invoiceDate
        );

        adminUseCase.generateInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }
}