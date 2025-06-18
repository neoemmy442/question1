package utms;

public class Main {
    public static void main(String[] args) {
        // Runtime Polymorphism
        User s = new Student("S001", "Neo", "neo@vu.ac.ug", "CS");
        User l = new Lecturer("L001", "Dr. Smith", "smith@vu.ac.ug", "Math");
        TransportOfficer t = new TransportOfficer("T001", "Officer John", "john@vu.ac.ug");

        s.requestTransport();
        l.requestTransport();
        t.requestTransport();

        // Method Overloading
        t.assignDriver("Bus");
        t.assignDriver("Van", "Morning");

        // Interfaces and Implementation
        Schedulable bus = new Bus("UBB123X");
        Schedulable van = new Van("UAX456T");

        bus.schedule("8:00 AM");
        van.schedule("2:30 PM");

        // Encapsulation in Driver
        Driver driver = new Driver("David", "D12345678");
        System.out.println("Driver Name: " + driver.getName());
        System.out.println("License Number: " + driver.getLicenseNumber());
    }
}
