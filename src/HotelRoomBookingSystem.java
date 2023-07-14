import room.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is for registering Hotel rooms and customers and booking customers to rooms.
 * <p>The booking information is also saved in <b>bookingInfo.txt</b> file for every individual customer:</p>
 * <p>that helps us to save information even after program shut down</p>
 * @author Serob
 */
public class HotelRoomBookingSystem {

    private File bookingInfo = new File("bookingInfo.txt");
    private List<Room> registeredRooms = new ArrayList<>();
    private List<Customer> registeredCustomers = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public HotelRoomBookingSystem() {
        //checks are there any registered Rooms, Customers and Bookings
        checkFileForPreviousBookings();
    }

    /**
     * This method is for booking every customer individually to certain room.
     * <p>Method checks if customer or room are registered or not:</p>
     * <p>-if some of them is not registered, the program will notify about it</p>
     * <p>-if room is not available or customer is already booked, the program will notify about it</p>
     * @param customerName customer name which want to get booked
     * @param roomId room ID to which customer is trying to be assigned
     * @param startDate starting date of booking
     * @param endDate ending date of booking
     */
    public void bookRoom(String customerName, int roomId, LocalDate startDate, LocalDate endDate) {
        Room room = findRoomById(roomId);
        Customer customer = findCustomerByName(customerName);

        if (room == null || customer == null) {
            System.out.println("Invalid customer or room");
            return;
        }

        if (!room.isAvailable()) {
            System.out.println("\nThe room with ID: " + room.getRoomId() + " is not available\n");
            return;
        }

        if (isCustomerAlreadyBooked(customer)) {
            System.out.println("\nThe customer " + customer.getName() + " is already booked\n");
            return;
        }

        Booking booking = new Booking(room, customer, startDate, endDate);
        bookings.add(booking);
        room.occupied();




        double pricePerDay = 0;
        if (room instanceof SingleRoom) {
            pricePerDay = SingleRoom.PRICE_PER_DAY;
        } else if (room instanceof DoubleRoom) {
            pricePerDay = DoubleRoom.PRICE_PER_DAY;
        } else if (room instanceof DeluxeRoom) {
            pricePerDay = DeluxeRoom.PRICE_PER_DAY;
        } else {
            System.out.println("There is something wrong with rooms");
        }
        double totalAmount = calculateTotalAmount(pricePerDay, startDate, endDate);
        double taxes = calculateTaxes(totalAmount);
        double serviceFee = calculateServiceFee(totalAmount);
        double finalAmount = totalAmount + taxes + serviceFee;

        System.out.println("\nBooking successful! Here is your bill:");
        System.out.println("Room ID: " + room.getRoomId());
        System.out.println("Room Type: " + room.getRoomType());
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Customer Email: " + customer.getEmail());
        System.out.println("Booking Period: " + startDate + " to " + endDate);
        System.out.println("Price per day: $" + pricePerDay);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Taxes (20%): $" + taxes);
        System.out.println("Service Fee (10%): $" + serviceFee);
        System.out.println("Final Amount: $" + finalAmount + '\n');

        generateRoomReport(booking);
        addToFile(room, customer, startDate, endDate, finalAmount);
    }

    /**
     * Registering a customer
     */
    public void registerCustomer(Customer customer) {
        registeredCustomers.add(customer);
    }

    /**
     * Registering Single room
     */
    public void registerSingleRoom() {
        registeredRooms.add(new SingleRoom());
    }

    /**
     * Registering Double room
     */
    public void registerDoubleRoom() {
        registeredRooms.add(new DoubleRoom());
    }

    /**
     * Registering Deluxe room
     */
    public void registerDeluxeRoom() {
        registeredRooms.add(new DeluxeRoom());
    }

    public List<Customer> getRegisteredCustomers() {
        return registeredCustomers;
    }

    public List<Room> getRegisteredRooms() {
        return registeredRooms;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    private Room findRoomById(int id) {
        for (Room room : registeredRooms) {
            if (room.getRoomId() == id) {
                return room;
            }
        }
        return null;
    }

    private Customer findCustomerByName(String name) {
        for (Customer customer : registeredCustomers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    private boolean isCustomerAlreadyBooked(Customer customer) {
        for (Booking booking : bookings) {
            if (booking.getCustomer().getName().equals(customer.getName())) {
                return true;
            }
        }
        return false;
    }

    private double calculateTotalAmount(double pricePerDay, LocalDate startDate, LocalDate endDate) {
        long days = endDate.toEpochDay() - startDate.toEpochDay();
        return pricePerDay * days;
    }

    private double calculateTaxes(double totalAmount) {
        //taxes 20%
        return totalAmount * 0.20;
    }

    private double calculateServiceFee(double totalAmount) {
        //service fee 10%
        return totalAmount * 0.10;
    }

    private void addToFile(Room room, Customer customer, LocalDate startDate, LocalDate endDate, double finalAmount) {
        try (var bw = new BufferedWriter(new FileWriter(bookingInfo, true))) {
            bw.write("room id -> :" + room.getRoomId() + ": room type -> :" + room.getRoomType() +
                    ": customer name -> :" + customer.getName() + ": customer email -> :" + customer.getEmail() +
                    ": customer id :" + customer.getCustomerId() +
                    ": start from -> :" + startDate + ": to -> :" + endDate + ": final Amount -> :$" + finalAmount + '\n');
            bw.flush();
        } catch (IOException e) {
            System.out.println("file not found");
        }
    }

    private void checkFileForPreviousBookings() {
        try (var scanner = new Scanner(new FileReader(bookingInfo))) {
            List<String> strings = new ArrayList<>();
            while (scanner.hasNextLine()) {
                strings.add(scanner.nextLine());
            }
            for (String str : strings) {
                String[] arr = str.split(":");
                setAllBookingsFromFile(arr);
            }
        } catch (IOException e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    private void setAllBookingsFromFile(String[] arr) {

        final int idIndex = 1;
        final int roomTypeIndex = 3;
        final int customerNameIndex = 5;
        final int customerEmailIndex = 7;
        final int customerIdIndex = 9;
        final int startFromIndex = 11;
        final int endingIndex = 13;

        if (arr[0].equals("")) {
            return;
        }

        int id = Integer.parseInt(arr[idIndex]);
        String name = arr[customerNameIndex];
        String email = arr[customerEmailIndex];
        int customerId = Integer.parseInt(arr[customerIdIndex]);
        LocalDate start = LocalDate.parse(arr[startFromIndex]);
        LocalDate end = LocalDate.parse(arr[endingIndex]);

        switch (arr[roomTypeIndex]) {
            case "Single room" -> {
                SingleRoom singleRoom = new SingleRoom();
                singleRoom.setRoomId(id);
                registeredRooms.add(singleRoom);

                Customer customer = new Customer(name, email);
                customer.setCustomerId(customerId);
                registeredCustomers.add(customer);

                bookings.add(new Booking(singleRoom, customer, start, end));
                singleRoom.occupied();
            }
            case "Double room" -> {
                DoubleRoom doubleRoom = new DoubleRoom();
                doubleRoom.setRoomId(id);
                registeredRooms.add(doubleRoom);

                Customer customer = new Customer(name, email);
                customer.setCustomerId(customerId);
                registeredCustomers.add(customer);

                bookings.add(new Booking(doubleRoom, customer, start, end));
                doubleRoom.occupied();
            }
            case "Deluxe room" -> {
                DeluxeRoom deluxeRoom = new DeluxeRoom();
                deluxeRoom.setRoomId(id);
                registeredRooms.add(deluxeRoom);

                Customer customer = new Customer(name, email);
                customer.setCustomerId(customerId);
                registeredCustomers.add(customer);

                bookings.add(new Booking(deluxeRoom, customer, start, end));
                deluxeRoom.occupied();
            }
        }

    }

    private void generateRoomReport(Booking booking) {
        try (var bw = new BufferedWriter(new FileWriter(booking.getBookingHistoryFile(), true))) {
            bw.write("Customer :" + booking.getCustomer().getName() +
                    ": stayed from :" + booking.getStartDate() + " to " + booking.getEndDate() + '\n');
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
