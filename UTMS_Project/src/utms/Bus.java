package utms;

public class Bus implements Schedulable {
    private final String plateNumber;

    public Bus(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Override
    public void schedule(String time) {
        System.out.println("Bus " + plateNumber + " scheduled at " + time);
    }
}
