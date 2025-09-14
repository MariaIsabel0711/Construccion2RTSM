package app.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "procedure_records")
public class ProcedureRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int itemNumber;

    @Column(nullable = false)
    private String procedureName;

    @Column
    private int repetitions;

    @Column
    private String frequency;

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

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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
