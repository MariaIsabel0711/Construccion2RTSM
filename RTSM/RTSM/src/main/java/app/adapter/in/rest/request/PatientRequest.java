package app.adapter.in.rest.request;

public class PatientRequest {
	
	private String fullName;
    private Long document;
    private String email;
    private String phoneNumber;
    private String dateOfBirth; 
    private String address;
    private String gender;

    private String emergencyContactName;
    private String emergencyContactRelationship;
    private String emergencyContactPhoneNumber;

    private String insuranceCompanyName; 
    private String policyNumber;         
    private boolean policyStatus;        
    private String policyEndDate;        
	
    public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getDocument() {
		return document;
	}
	public void setDocument(Long document) {
		this.document = document;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getEmergencyContactRelationship() {
		return emergencyContactRelationship;
	}
	public void setEmergencyContactRelationship(String emergencyContactRelationship) {
		this.emergencyContactRelationship = emergencyContactRelationship;
	}
	public String getEmergencyContactPhoneNumber() {
		return emergencyContactPhoneNumber;
	}
	public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
		this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public boolean isPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(boolean policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getPolicyEndDate() {
		return policyEndDate;
	}
	public void setPolicyEndDate(String policyEndDate) {
		this.policyEndDate = policyEndDate;
	}


}
