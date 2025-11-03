import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

class PetScheduler {
    public static void main(String[] args) {

        HashMap<String, Pet> pets = loadPetsFromFile();
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            // Main loop for the Pet Care Scheduler application
            System.out.println("Welcome to Pet Care Scheduler");
            System.out.println("Please choose an option:");
            System.out.println("1. Register Pet");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Save and Exit");
            System.out.println("4. Display Records");
            System.out.println("5. Generate Report");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    // register a pet
                    registerPet(pets, scanner);
                    break;

                case "2":            
                    // schedule an appointment
                    scheduleAppointment(pets, scanner);
                    break;

                case "3":
                    // save data and exit
                    savePetsToFile(pets);
                    running = false;
                    break;

                case "4":
                    // display records
                    displayRecords(pets);
                    break;

                case "5":
                    GenerateReport(pets);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void registerPet(HashMap<String, Pet> pets, Scanner scanner) {
        System.out.println("Enter pet ID:");
            String petId = scanner.nextLine();

            if (pets.containsKey(petId)) {
                System.out.println("Pet ID already exists. Registration failed.");
                break;
            }

            System.out.println("Enter pet name:");
            String petName = scanner.nextLine();

            System.out.println("Enter pet species (dog, cat, etc.):");
            String petSpecies = scanner.nextLine();

            System.out.println("Enter pet Breed (Labrador, Golden Retriever, etc.):");
            String petBreed = scanner.nextLine();

            System.out.println("Enter pet Age:");
            int petAge = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter owner's name:");
            String ownerName = scanner.nextLine();

            System.out.println("Enter owner's contact info:");
            String contactInfo = scanner.nextLine();

            Pet newPet = new Pet(petId, petName, petSpecies, petBreed, petAge, ownerName, contactInfo);
            pets.put(petId, newPet);

            System.out.println("Pet registered successfully!");
    }

    private static void scheduleAppointment(HashMap<String, Pet> pets, Scanner scanner) {
        System.out.println("Enter pet ID for appointment:");
        String apptPetId = scanner.nextLine();
        if (!pets.containsKey(apptPetId)) {
            System.out.println("Pet ID not found. Cannot schedule appointment.");
            break;
        }
        Pet apptPet = pets.get(apptPetId);
        System.out.println("Enter appointment type (e.g., vet visit, grooming):");
        String apptType = scanner.nextLine();

        System.out.println("Enter appointment date and time (YYYY-MM-DD HH:MM):");
        String apptDateTime = scanner.nextLine();
        
        LocalDateTime appointmentDateTime;
        try {
            appointmentDateTime = LocalDateTime.parse(apptDateTime.replace(" ", "T"));
        } catch (Exception e) {
            System.out.println("Invalid date/time format. Use YYYY-MM-DD HH:MM.");
            break;
        }

        // Check if the appointment date is in the future
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Appointment date and time must be in the future.");
            break;
        }

        System.out.println("Enter any notes for the appointment (optional):");
        String apptNotes = scanner.nextLine();

        Appointment newAppointment = new Appointment(apptType, apptDateTime, apptNotes);
        apptPet.addAppointment(newAppointment);
        System.out.println("Appointment scheduled successfully.");
    }

    private static void savePetsToFile(HashMap<String, Pet> pets) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pets.ser"))
        ) {
            out.writeObject(pets);
            System.out.println("Pet data saved.");
        } catch (IOException e) {
            System.out.println("Error saving pet data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static HashMap<String, Pet> loadPetsFromFile() {
        HashMap<String, Pet> pets;
        try (
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("pets.ser"))
        ) {
            pets = (HashMap<String, Pet>) in.readObject();
            System.out.println("Pet data loaded.");
        } catch (FileNotFoundException e) {
            pets = new HashMap<>();
            System.out.println("No saved pet data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            pets = new HashMap<>();
            System.out.println("Error loading pet data: " + e.getMessage());
        }
        return pets;
    }

    private static void displayRecords(HashMap<String, Pet> pets) {
        if (pets.isEmpty()) {
            System.out.println("No records available.");
            return;
        }
        Scanner displayScanner = new Scanner(System.in);
        System.out.println("What do you want to display?");
        System.out.println("1. All Pets");
        System.out.println("2. Appointments for a Pet");
        System.out.println("3. Appointments for all Pets");
        System.out.println("4. Past Appointment history for a Pet");
        String displayChoice = displayScanner.nextLine();
        switch (displayChoice) {    
            
            case "1":
                for (Pet pet : pets.values()) {
                    System.out.println(pet.toString());
                }
                break;
            
            case "2":
                System.out.println("Enter pet ID:");
                String petId = displayScanner.nextLine();
                if (!pets.containsKey(petId)) {
                    System.out.println("Pet ID not found.");
                    break;
                }
                Pet pet = pets.get(petId);
                Collection<Appointment> appointments = pet.getAppointments();
                if (appointments.isEmpty()) {
                    System.out.println("No appointments found for this pet.");
                    break;
                }
                for (Appointment appt : appointments) {     
                    System.out.println(appt);
                }
                break;

            case "3":
                for (Pet p : pets.values()) {
                    ArrayList<Appointment> apptsAll = new ArrayList<>(p.getAppointments());
                    if (apptsAll.isEmpty()) {
                        continue;
                    }
                    System.out.println("Appointments for Pet ID " + p.getId() + ":");
                    for (Appointment appt : apptsAll) {
                        System.out.println(appt);
                    }
                }
                break;

            case "4":
                System.out.println("Enter pet ID:");
                String pId = displayScanner.nextLine();
                if (!pets.containsKey(pId)) {
                    System.out.println("Pet ID not found.");
                    break;
                }
                Pet p = pets.get(pId);
                ArrayList<Appointment> appts = new ArrayList<>(p.getAppointments());
                if (appts.isEmpty()) {
                    System.out.println("No appointments found for this pet.");
                    break;
                }
                LocalDateTime now = LocalDateTime.now();
                boolean foundPast = false;
                for (Appointment appt : appts) {
                    LocalDateTime apptDateTime;
                    try {
                        apptDateTime = LocalDateTime.parse(appt.getAppointmentDateAndTime().replace(" ", "T"));
                    } catch (Exception e) {
                        continue;
                    }
                    if (apptDateTime.isBefore(now)) {
                        System.out.println(appt.toString());
                        foundPast = true;
                    }
                }
                if (!foundPast) {
                    System.out.println("No past appointments found for this pet.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }        
    }

    private static void GenerateReport(HashMap<String, Pet> pets) {
        
        ArrayList<Appointment> upcomingAppointments = new ArrayList<>();
        ArrayList<Pet> noAppointmentsLast6Months = new ArrayList<>();
        for (Pet pet : pets.values()) {
            boolean hadAppointmentLast6Months = false;
            for (Appointment appt : pet.getAppointments()) {
                LocalDateTime apptDateTime;
                try {
                    apptDateTime = LocalDateTime.parse(appt.getAppointmentDateAndTime().replace(" ", "T"));
                } catch (Exception e) {
                    continue;
                }
                if (apptDateTime.isBefore(LocalDateTime.now().plusDays(7))){
                    upcomingAppointments.add(appt);
                }
                if (apptDateTime.isAfter(LocalDateTime.now().minusMonths(6)) && apptDateTime.isBefore(LocalDateTime.now())) {
                    hadAppointmentLast6Months = true;
                }
            }
            if (!hadAppointmentLast6Months) {
                noAppointmentsLast6Months.add(pet);
            }
        }
        // Generate Report Output
        System.out.println("Upcoming Appointments in Next 7 Days:");
        for (Appointment appt : upcomingAppointments) { 
            System.out.println(appt.toString());
        }
        System.out.println("\nPets with No Appointments in Last 6 Months:");
        for (Pet pet : noAppointmentsLast6Months) {
            System.out.println(pet.toString()); 
        
    }
    }   
}