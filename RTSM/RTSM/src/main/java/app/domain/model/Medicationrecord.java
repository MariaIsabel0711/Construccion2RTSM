package app.domain.model;

public class Medicationrecord {
    private long orderNumber;
    private int itemNumber;
    private String medicationName;
    private String dose;
    private String treatmentDuration;
    private double cost;

    public Medicationrecord() {}

    public long getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
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
}
