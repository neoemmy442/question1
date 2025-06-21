package utms;

public class Student extends User {

    public Student(String id, String name, String email, String course) {
        super(id, name, email);
    }

    @Override
    public void requestTransport() {
        System.out.println(name + " requested transport for field trip.");
    }
}
