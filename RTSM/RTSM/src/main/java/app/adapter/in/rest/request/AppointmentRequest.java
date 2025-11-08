package app.adapter.in.rest.request;

public class AppointmentRequest {
	private Long patientDocument;
    private Long doctorDocument;
    private String appointmentDate; 
    

    public Long getPatientDocument() {
		return patientDocument;
	}
	public void setPatientDocument(Long patientDocument) {
		this.patientDocument = patientDocument;
	}
	public Long getDoctorDocument() {
		return doctorDocument;
	}
	public void setDoctorDocument(Long doctorDocument) {
		this.doctorDocument = doctorDocument;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

 
}
