package app.adapter.in.rest.request;

public class UserRequest {
    private String fullName;
    private Long document;
    private String email;
    private String phoneNumber;
    private String dateOfBirth; 
    private String address;
    private String gender;
    private String userName;
    private String password;
    private String role; 

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Long getDocument() { return document; }
    public void setDocument(Long document) { this.document = document; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}