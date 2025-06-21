package utms;

public class TransportOfficer extends User {

    public TransportOfficer(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public void requestTransport() {
        System.out.println("Welcome " + name);
    }

    public void assignDriver(String vehicleType, String shiftTime) {
        System.out.println("Assigning driver for: " + shiftTime +" "+ vehicleType);
    }
}
