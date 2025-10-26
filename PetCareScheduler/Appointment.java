class Appointment {
    private String petName;
    private String ownerName;
    private String appointmentDate;
    private String appointmentTime;
    private String serviceType;

    public Appointment(String petName, String ownerName, String appointmentDate, String appointmentTime, String serviceType) {
        this.petName = petName;
        this.ownerName = ownerName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.serviceType = serviceType;
    }

    public String getPetName() {
        return petName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "petName='" + petName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }
}