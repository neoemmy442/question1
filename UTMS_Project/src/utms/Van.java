package utms;

public class Van implements Schedulable {
    private String plateNumber;

    public Van(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Override
    public void schedule(String time) {
        System.out.println("Van " + plateNumber + " scheduled at " + time);
    }
}
