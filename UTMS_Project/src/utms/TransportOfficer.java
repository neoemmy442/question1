package utms;

public class TransportOfficer extends User {

    public TransportOfficer(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public void requestTransport() {
        System.out.println(name + " (Transport Officer) is reviewing requests.");
    }

    public void assignDriver(String vehicleType) {
        System.out.println("Assigning driver for: " + vehicleType);
    }

    public void assignDriver(String vehicleType, String shiftTime) {
        System.out.println("Assigning driver for: " + vehicleType + " during " + shiftTime);
    }
}
