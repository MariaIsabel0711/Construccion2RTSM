package app.domain.services;

import app.domain.model.Invoice;
import app.domain.ports.InvoicePort;

public class CreateInvoice {

    private InvoicePort invoicePort;

    public CreateInvoice(InvoicePort invoicePort) {
        this.invoicePort = invoicePort;
    }

    public void create(Invoice invoice) throws Exception {
        if (invoice.getPatientDocument() == null) {
            throw new Exception("El documento del paciente es obligatorio para la factura.");
        }
        if (invoice.getInvoiceDate() == null) {
            throw new Exception("La fecha de la factura es obligatoria.");
        }
        if (invoice.getTotalAmount() < 0) {
            throw new Exception("El monto total de la factura no puede ser negativo.");
        }
        invoicePort.save(invoice);
    }
}
