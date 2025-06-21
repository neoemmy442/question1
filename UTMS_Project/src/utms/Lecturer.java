package utms;

public class Lecturer extends User {

    public Lecturer(String id, String name, String email, String department) {
        super(id, name, email);
    }

    @Override
    public void requestTransport() {
        System.out.println(name + " requested transport for conference.");
    }
}
