package app.infrastructure.mapper;

import app.domain.model.Patient;
import app.infrastructure.entity.PatientEntity;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientEntity toEntity(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientEntity entity = new PatientEntity();
        
        // ✅ Corregir la comparación - usar Long en lugar de long
        if (patient.getId() != null && patient.getId() > 0) {
            entity.setId(patient.getId());
        }
        
        entity.setFullName(patient.getFullName());
        entity.setDocument(patient.getDocument());
        entity.setEmail(patient.getEmail());
        entity.setPhoneNumber(patient.getPhoneNumber());
        entity.setDateOfBirth(patient.getDateOfBirth());
        entity.setAddress(patient.getAddress());
        entity.setGender(patient.getGender());
        entity.setEmergencyContactName(patient.getEmergencyContactName());
        entity.setEmergencyContactRelationship(patient.getEmergencyContactRelationship());
        entity.setEmergencyContactPhoneNumber(patient.getEmergencyContactPhoneNumber());
        entity.setInsuranceCompanyName(patient.getInsuranceCompanyName());
        entity.setPolicyNumber(patient.getPolicyNumber());
        entity.setPolicyStatus(patient.isPolicyStatus());
        entity.setPolicyEndDate(patient.getPolicyEndDate());

        return entity;
    }

    public Patient toDomain(PatientEntity entity) {
        if (entity == null) {
            return null;
        }

        Patient patient = new Patient();
        
        patient.setId(entity.getId());
        patient.setFullName(entity.getFullName());
        patient.setDocument(entity.getDocument());
        patient.setEmail(entity.getEmail());
        patient.setPhoneNumber(entity.getPhoneNumber());
        patient.setDateOfBirth(entity.getDateOfBirth());
        patient.setAddress(entity.getAddress());
        patient.setGender(entity.getGender());
        patient.setEmergencyContactName(entity.getEmergencyContactName());
        patient.setEmergencyContactRelationship(entity.getEmergencyContactRelationship());
        patient.setEmergencyContactPhoneNumber(entity.getEmergencyContactPhoneNumber());
        patient.setInsuranceCompanyName(entity.getInsuranceCompanyName());
        patient.setPolicyNumber(entity.getPolicyNumber());
        patient.setPolicyStatus(entity.isPolicyStatus());
        patient.setPolicyEndDate(entity.getPolicyEndDate());

        return patient;
    }
}
