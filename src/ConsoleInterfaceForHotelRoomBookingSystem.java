import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleInterfaceForHotelRoomBookingSystem {

    private final String message = """
            enter 1 - for adding a room
            enter 2 - for adding a Customer
            enter 3 - for booking a room for a customer
            enter 4 - for saving the state of system in file (via full path)
            enter 5 - for loading the state of system from file (via full path)
            enter 0 - for terminating the program
            """;

    private final HotelRoomBookingSystem bookingSystem;


    ConsoleInterfaceForHotelRoomBookingSystem() {
        bookingSystem = new HotelRoomBookingSystem();
        start();
    }

    private void start() {

        Scanner sc = new Scanner(System.in);
        String operation;
        boolean isTrue = true;
        do {
            System.out.println(message);
            operation = sc.nextLine();
            switch (operation) {
                case "1" -> addRoom();
                case "2" -> addCustomer();
                case "3" -> bookARoom();
                case "4" -> saveTheState();
                case "5" -> loadTheState();
                case "0" -> {
                    isTrue = false;
                    System.out.println("\nGood Bye!\n");
                }
                default -> System.out.println("\n - WARNING - \"" + operation + "\" is not valid operation!\n");
            }

        } while (isTrue);
        sc.close();
    }

    private void addRoom() {
        Scanner sc = new Scanner(System.in);
        boolean isTrue;

        String operation;
        do {
            isTrue = false;
            System.out.println("""

                    - Here you can add room by type -
                    please enter the room type
                    \tSingle Room - 1
                    \tDouble Room - 2
                    \tDeluxe Room - 3
                    """);
            operation = sc.nextLine();
            switch (operation) {
                case "1" -> bookingSystem.registerSingleRoom();
                case "2" -> bookingSystem.registerDoubleRoom();
                case "3" -> bookingSystem.registerDeluxeRoom();
                default -> {
                    isTrue = true;
                    System.out.println("\n\t- WARNING! - not correct room type\n");
                }
            }
        } while (isTrue);
    }

    private void addCustomer() {
        Scanner sc = new Scanner(System.in);

        String name;
        String email;

        do {
            System.out.println("\n- Here you can register a customer -");
            System.out.print("Please enter the customers name - ");
            name = sc.nextLine();
            System.out.print("\nPlease enter the customers email - ");
            email = sc.nextLine();
        } while (name.isBlank() && email.isBlank());

        bookingSystem.registerCustomer(new Customer(name, email));
    }

    private void bookARoom() {
        Scanner sc = new Scanner(System.in);
        String customerName;
        int roomId;
        LocalDate startDate;
        LocalDate endDate;

        System.out.println("\n- Here you can book a room for customer -");
        do {
            System.out.print("Please enter the name of registered customer - ");
            customerName = sc.nextLine();
        } while (customerName.isBlank());

        boolean checkIfNotNumber;
        do {
            checkIfNotNumber = false;
            System.out.print("\nPlease enter the Room ID - ");
            bookingSystem.showRegisteredRoomsAndRoomIDs();
            String num = sc.nextLine();
            try {
                roomId = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                System.out.println("\"" + num + "\" is not a numeric ID\n");
                roomId = -1;
                checkIfNotNumber = true;
            }
        } while (checkIfNotNumber);

        boolean isDateFormatWrong;
        do {
            isDateFormatWrong = false;
            System.out.print("\nPlease enter the 'Start Date' of booking (yyyy-mm-dd) - ");
            try {
                startDate = LocalDate.parse(sc.nextLine());
            } catch (DateTimeException e) {
                startDate = LocalDate.now();
                isDateFormatWrong = true;
                System.out.println("please enter date as required (yyyy-mm-dd)");
            }
        } while (isDateFormatWrong);

        do {
            isDateFormatWrong = false;
            System.out.print("\nPlease enter the 'End Date' of booking (yyyy-mm-dd) - ");
            try {
                endDate = LocalDate.parse(sc.nextLine());
            } catch (DateTimeException e) {
                endDate = LocalDate.now();
                isDateFormatWrong = true;
                System.out.println("please enter date as required (yyyy-mm-dd)");
            }
        } while (isDateFormatWrong);

        bookingSystem.bookRoom(customerName, roomId, startDate, endDate);
    }

    private void saveTheState() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the 'Absolute Path' of a file to SAVE the system state");
        bookingSystem.saveTheStateOfSystem(sc.nextLine());
    }

    private void loadTheState() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the 'Absolute Path' of a file to LOAD the system state from");
        bookingSystem.loadTheStateOfSystem(sc.nextLine());
    }

}
