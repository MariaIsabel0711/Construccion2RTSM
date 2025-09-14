package app.infrastructure.adapter;

import app.domain.model.Invoice;
import app.domain.ports.InvoicePort;
import app.infrastructure.entity.InvoiceEntity;
import app.infrastructure.mapper.InvoiceMapper;
import app.infrastructure.repository.InvoiceJpaRepository;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InvoiceJpaAdapter implements InvoicePort {

    private final InvoiceJpaRepository invoiceJpaRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceJpaAdapter(InvoiceJpaRepository invoiceJpaRepository, InvoiceMapper invoiceMapper) {
        this.invoiceJpaRepository = invoiceJpaRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public void save(Invoice invoice) throws Exception {
        InvoiceEntity entity = invoiceMapper.toEntity(invoice);
        invoiceJpaRepository.save(entity);
    }

    @Override
    public Invoice findById(long id) {
        Optional<InvoiceEntity> entity = invoiceJpaRepository.findById(id);
        return entity.map(invoiceMapper::toDomain).orElse(null);
    }

    @Override
    public List<Invoice> findByPatientDocument(Long patientDocument) {
        List<InvoiceEntity> entities = invoiceJpaRepository.findByPatientDocument(patientDocument);
        return entities.stream()
                .map(invoiceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> findAll() {
        List<InvoiceEntity> entities = invoiceJpaRepository.findAll();
        return entities.stream()
                .map(invoiceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Invoice invoice) throws Exception {
        InvoiceEntity entity = invoiceMapper.toEntity(invoice);
        invoiceJpaRepository.delete(entity);
    }

    @Override
    public Double getAnnualCopaySum(Long patientDocument, int year) {
        Date dateForYear = Date.valueOf(LocalDate.of(year, 1, 1));
        Double sum = invoiceJpaRepository.sumCopayAmountByPatientAndYear(patientDocument, dateForYear);
        return sum != null ? sum : 0.0;
    }
}
