package app.domain.model;

public class DiagnosticRecord {
    private long orderNumber;
    private int itemNumber;
    private String diagnosticAidName;
    private int quantity;
    private double cost;
    private boolean requiresSpecialist;
    private Long specialistRoleId;

    public DiagnosticRecord() {}

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
    public String getDiagnosticName() {
        return diagnosticAidName;
    }
    public void setDiagnosticName(String diagnosticAidName) {
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
}
