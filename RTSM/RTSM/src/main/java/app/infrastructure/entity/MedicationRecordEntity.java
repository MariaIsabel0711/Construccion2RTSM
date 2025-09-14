package app.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medication_records")
public class MedicationRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int itemNumber;

    @Column(nullable = false)
    private String medicationName;

    @Column
    private String dose;

    @Column
    private String treatmentDuration;

    @Column
    private double cost;

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

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getTreatmentDuration() {
        return treatmentDuration;
    }

    public void setTreatmentDuration(String treatmentDuration) {
        this.treatmentDuration = treatmentDuration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ClinicalOrderEntity getClinicalOrder() {
        return clinicalOrder;
    }

    public void setClinicalOrder(ClinicalOrderEntity clinicalOrder) {
        this.clinicalOrder = clinicalOrder;
    }
}
