package utms;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== University Transport Management System ===");
        System.out.println("Select user type:");
        System.out.println("1. Student");
        System.out.println("2. Lecturer");
        System.out.println("3. Transport Officer");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        String department = "";
        if (choice != 3) { // Not a Transport Officer
            System.out.print("Enter Department: ");
            department = scanner.nextLine();
        }

        User user = switch (choice) {
            case 1 -> new Student(id, name, email, department);
            case 2 -> new Lecturer(id, name, email, department);
            case 3 -> new TransportOfficer(id, name, email);
            default -> null;
        };

        if (user != null) {
            user.requestTransport();
            System.out.println("\nUser created successfully!");
            System.out.println("Type: " + (choice == 1 ? "Student" : choice == 2 ? "Lecturer" : "Transport Officer"));
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
        }

        scanner.close();
    }
}
