package app.domain.model;

public class ProcedireRecord {
    // private long orderNumber; se maneja por la relación con ClinicalOrder
    private int itemNumber;
    private String procedureName;
    private int repetitions;
    private String frequency;
    private double cost;
    private boolean requiresSpecialist;
    private Long specialistRoleId;

    public ProcedireRecord() {}

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
}
