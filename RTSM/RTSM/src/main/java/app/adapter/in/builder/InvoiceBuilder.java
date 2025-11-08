package app.adapter.in.builder;

import app.domain.model.Invoice;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class InvoiceBuilder {
    public Invoice build(Long patientDocument, Long medicalDocument, double totalAmount, Date invoiceDate) {
        Invoice i = new Invoice();
        i.setPatientDocument(patientDocument);
        i.setMedicalDocument(medicalDocument);
        i.setTotalAmount(totalAmount);
        i.setInvoiceDate(invoiceDate);
        return i;
    }
}
