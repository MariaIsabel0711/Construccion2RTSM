package app.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnostic_records")
public class DiagnosticRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int itemNumber;

    @Column(nullable = false)
    private String diagnosticAidName;

    @Column
    private int quantity;

    @Column
    private double cost;

    @Column
    private boolean requiresSpecialist;

    @Column
    private Long specialistRoleId;

    @ManyToOne
    @JoinColumn(name = "order_number", nullable = false)
    private ClinicalOrderEntity clinicalOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getDiagnosticAidName() {
        return diagnosticAidName;
    }

    public void setDiagnosticAidName(String diagnosticAidName) {
        this.diagnosticAidName = diagnosticAidName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isRequiresSpecialist() {
        return requiresSpecialist;
    }

    public void setRequiresSpecialist(boolean requiresSpecialist) {
        this.requiresSpecialist = requiresSpecialist;
    }

    public Long getSpecialistRoleId() {
        return specialistRoleId;
    }

    public void setSpecialistRoleId(Long specialistRoleId) {
        this.specialistRoleId = specialistRoleId;
    }

    public ClinicalOrderEntity getClinicalOrder() {
        return clinicalOrder;
    }

    public void setClinicalOrder(ClinicalOrderEntity clinicalOrder) {
        this.clinicalOrder = clinicalOrder;
    }
}
