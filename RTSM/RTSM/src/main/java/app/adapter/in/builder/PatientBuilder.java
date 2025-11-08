package app.adapter.in.builder;

import app.domain.model.Patient;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class PatientBuilder {
    public Patient build(String fullName, Long document, String email, String phone, Date dob,
                         String address, String gender,
                         String emergencyName, String emergencyRelation, String emergencyPhone,
                         String insuranceCompany, String policyNumber, boolean policyStatus, Date policyEndDate) {

        Patient p = new Patient();
        p.setFullName(fullName);
        p.setDocument(document);
        if (email != null && !email.isEmpty()) p.setEmail(email);
        p.setPhoneNumber(phone);
        p.setDateOfBirth(dob);
        p.setAddress(address);
        p.setGender(gender);

        p.setEmergencyContactName(emergencyName);
        p.setEmergencyContactRelationship(emergencyRelation);
        p.setEmergencyContactPhoneNumber(emergencyPhone);

        if (insuranceCompany != null && !insuranceCompany.isEmpty()) {
            p.setInsuranceCompanyName(insuranceCompany);
            p.setPolicyNumber(policyNumber);
            p.setPolicyStatus(policyStatus);
            p.setPolicyEndDate(policyEndDate);
        } else {
            p.setPolicyStatus(false);
        }
        return p;
    }
}
