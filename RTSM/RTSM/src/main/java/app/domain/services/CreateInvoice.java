package app.domain.services;

import app.domain.model.Invoice;
import app.domain.ports.InvoicePort;

public class CreateInvoice {

    private InvoicePort invoicePort;

  
    public CreateInvoice(InvoicePort invoicePort) {
        this.invoicePort = invoicePort;
    }

    public void create(Invoice invoice) throws Exception {
        invoicePort.save(invoice);
    }
}
