package app.infrastructure.mapper;

import app.domain.model.Invoice;
import app.infrastructure.entity.InvoiceEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public Invoice toDomain(InvoiceEntity entity) {
        if (entity == null) return null;

        Invoice domain = new Invoice();
        domain.setId(entity.getId()); // tu dominio puede ser long; si entity.getId()==null, quedará 0 en el dominio según tu setter
        domain.setPatientDocument(entity.getPatientDocument());
        domain.setMedicalDocument(entity.getMedicalDocument());
        domain.setInsuranceCompanyName(entity.getInsuranceCompanyName());
        domain.setPolicyNumber(entity.getPolicyNumber());
        domain.setPolicyValidityDays(entity.getPolicyValidityDays());
        domain.setPolicyEndDate(entity.getPolicyEndDate());
        domain.setTotalAmount(entity.getTotalAmount());
        domain.setCopayAmount(entity.getCopayAmount());
        domain.setInsuranceCoverageAmount(entity.getInsuranceCoverageAmount());
        domain.setInvoiceDate(entity.getInvoiceDate());
        return domain;
    }

    public InvoiceEntity toEntity(Invoice domain) {
        if (domain == null) return null;

        InvoiceEntity entity = new InvoiceEntity();

        if (domain.getId() > 0L) {
            entity.setId(domain.getId());
        } else {
            entity.setId(null); // fuerza INSERT
        }

        entity.setPatientDocument(domain.getPatientDocument());
        entity.setMedicalDocument(domain.getMedicalDocument());
        entity.setInsuranceCompanyName(domain.getInsuranceCompanyName());
        entity.setPolicyNumber(domain.getPolicyNumber());
        entity.setPolicyValidityDays(domain.getPolicyValidityDays());
        entity.setPolicyEndDate(domain.getPolicyEndDate());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setCopayAmount(domain.getCopayAmount());
        entity.setInsuranceCoverageAmount(domain.getInsuranceCoverageAmount());
        entity.setInvoiceDate(domain.getInvoiceDate());
        return entity;
    }
}
