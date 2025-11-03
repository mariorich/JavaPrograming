import java.io.Serializable;
class Appointment implements Serializable {
    private String appointmentType;
    private String appointmentDateAndTime;
    private String notes; // optional


    public Appointment(String appointmentType, String appointmentDateAndTime, String notes) {
        this.appointmentType = appointmentType;
        this.appointmentDateAndTime = appointmentDateAndTime;
        this.notes = notes;
    }

    // Setters
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
    public void setAppointmentDateAndTime(String appointmentDateAndTime) {
        this.appointmentDateAndTime = appointmentDateAndTime;
    }
    public void setNotes(String notes) {
        this.notes = notes; 
    }

    // Getters
    public String getAppointmentType() {
        return appointmentType;
    }
    public String getAppointmentDateAndTime() {
        return appointmentDateAndTime;
    }
    public String getNotes() {
        return notes;
    }
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentType='" + appointmentType + '\'' +
                ", appointmentDateAndTime='" + appointmentDateAndTime + '\'' +
                ", notes='" + notes + '\'' +
                '}'; 
    }
   
}