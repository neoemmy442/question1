package utms;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select user type:");
        System.out.println("1. Student");
        System.out.println("2. Lecturer");
        System.out.println("3. Transport Officer");
        System.out.print("Enter option (1-3): ");
        int option = Integer.parseInt(scanner.nextLine());

        User user = null;

        String id, name, email, dept = "";

        switch (option) {
            case 1, 2 -> {
                System.out.print("\nID: ");
                id = scanner.nextLine();
                System.out.print("Name: ");
                name = scanner.nextLine();
                System.out.print("Email: ");
                email = scanner.nextLine();
                System.out.print("Department: ");
                dept = scanner.nextLine();
                user = (option == 1)
                        ? new Student(id, name, email, dept)
                        : new Lecturer(id, name, email, dept);
            }
            case 3 -> {
                System.out.print("\nID: ");
                id = scanner.nextLine();
                System.out.print("Name: ");
                name = scanner.nextLine();
                System.out.print("Email: ");
                email = scanner.nextLine();
                user = new TransportOfficer(id, name, email);
            }
            default -> {
                System.out.println("Invalid option.");
                scanner.close();
                return;
            }
        }

        user.requestTransport();

        if (user instanceof TransportOfficer tOfficer) {
            System.out.println("\nSelect vehicle to assign:");
            System.out.println("1. Bus");
            System.out.println("2. Van");
            System.out.print("Enter option (1-2): ");
            int vehicleOption = Integer.parseInt(scanner.nextLine());

            if (vehicleOption == 1) {
                String busPlate = "UBQ 859W";
                String busTime = "5:30pm";
                String driverName = "Kirabo Ryan";
                String driverLicense = "H995896JKD96";

                tOfficer.assignDriver("Bus", "Evening");
                Schedulable bus = new Bus(busPlate);
                bus.schedule(busTime);

                System.out.println("Assigned Bus Details:");
                System.out.println("Plate Number: " + busPlate);
                System.out.println("Departure Time: " + busTime);
                System.out.println("Driver Name: " + driverName);
                System.out.println("License Number: " + driverLicense);

            } else if (vehicleOption == 2) {
                // Van details (constant)
                String vanPlate = "UAX 958R";
                String vanTime = "2:30 PM";
                String driverName = "Asiimwe Rajab";
                String driverLicense = "B059960HDG94";

                tOfficer.assignDriver("Van", "Afternoon");
                Schedulable van = new Van(vanPlate);
                van.schedule(vanTime);

                System.out.println("Assigned Van Details:");
                System.out.println("Plate Number: " + vanPlate);
                System.out.println("Departure Time: " + vanTime);
                System.out.println("Driver Name: " + driverName);
                System.out.println("License Number: " + driverLicense);

            } else {
                System.out.println("Invalid vehicle option.");
            }
        }

        scanner.close();
    }
}
