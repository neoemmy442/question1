package utms;

public class Student extends User {
    private String course;

    public Student(String id, String name, String email, String course) {
        super(id, name, email);
        this.course = course;
    }

    @Override
    public void requestTransport() {
        System.out.println(name + " (Student) requested transport for field trip.");
    }
}
