package app.adapter.in.rest.request;

public class DiagnosticItemRequest {
    private int itemNumber;
    private String diagnosticName;
    private int quantity;
    private double cost;
    private boolean requiresSpecialist;
    private Long specialistRoleId; 

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getDiagnosticName() {
        return diagnosticName;
    }

    public void setDiagnosticName(String diagnosticName) {
        this.diagnosticName = diagnosticName;
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