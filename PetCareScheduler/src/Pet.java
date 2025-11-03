import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Collection;
import java.io.Serializable;

public class Pet implements Serializable {
    private final String id;
    private String name;
    private String species;
    private String breed;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate registrationDate;
    // Use an ArrayList to store appointments.
    private final List<Appointment> appointments = new ArrayList<>();

    // Full constructor - useful for restoring from storage
    public Pet(String id, String name, String species, String breed, int age, String ownerName, String contactInfo) {
        this.id = Objects.requireNonNull(id, "id");
        setName(name);
        setSpecies(species);
        setBreed(breed);
        setAge(age);
        setOwnerName(ownerName);
        setContactInfo(contactInfo);
        this.registrationDate = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = Objects.requireNonNull(species, "species");
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed; // breed may be null/empty for mixed/unknown
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) throw new IllegalArgumentException("age must be >= 0");
        this.age = age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = Objects.requireNonNull(ownerName, "ownerName");
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = Objects.requireNonNull(contactInfo, "contactInfo");
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = Objects.requireNonNull(registrationDate, "registrationDate");
    }

    /**
     * Returns an unmodifiable list view of appointments.
     */
    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public void addAppointment(Appointment appointment) {
        Objects.requireNonNull(appointment, "appointment");
        String aid = getAppointmentId(appointment);
        if (aid != null) {
            // replace existing appointment with same id, if present
            for (int i = 0; i < appointments.size(); i++) {
                Appointment a = appointments.get(i);
                String existingId = getAppointmentId(a);
                if (aid.equals(existingId)) {
                    appointments.set(i, appointment);
                    return;
                }
            }
            appointments.add(appointment);
        } else {
            // no id available - avoid adding exact duplicates
            if (!appointments.contains(appointment)) {
                appointments.add(appointment);
            }
        }
    }

    public boolean removeAppointment(Appointment appointment) {
        if (appointment == null) return false;
        return appointments.remove(appointment);
    }

    public boolean removeAppointmentById(String appointmentId) {
        if (appointmentId == null) return false;
        for (int i = 0; i < appointments.size(); i++) {
            String id = getAppointmentId(appointments.get(i));
            if (appointmentId.equals(id)) {
                appointments.remove(i);
                return true;
            }
        }
        return false;
    }

    private String getAppointmentId(Appointment appointment) {
        // helper to avoid direct dependency if Appointment doesn't expose getId().
        // Replace with appointment.getId() if available.
        try {
            return (String) appointment.getClass().getMethod("getId").invoke(appointment);
        } catch (Exception e) {
            // If Appointment doesn't have getId(), return null so caller can handle.
            return null;
        }
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", ownerName='" + ownerName + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", registrationDate=" + registrationDate +
                ", appointments=" + appointments.size() +
                '}';
    }
}
