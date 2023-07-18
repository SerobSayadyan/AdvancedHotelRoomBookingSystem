import room.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * This class is for registering Hotel rooms and customers and booking customers to rooms.
 * <p>The booking information is saved for every individual customer</p>
 * <p>that helps us to save information even after program shut down</p>
 * @author Serob
 */
public class HotelRoomBookingSystem implements Serializable {

    @Serial
    private static final long serialVersionUID = 557311153845756080L;

    private final File bookingInfo = new File("bookingInfo.ser");
    private final File registeredRoomsInfo = new File("registeredRoomsInfo.ser");
    private final File registeredCustomersInfo = new File("registeredCustomersInfo.ser");
    
    private List<Room> registeredRooms = new ArrayList<>();
    private List<Customer> registeredCustomers = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public HotelRoomBookingSystem() {
        //checks are there any registered Rooms, Customers and Bookings
        assignSavedObjectsIfNecacery();
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

        if (isCustomerAlreadyBooked(customer)) {
            System.out.println("\nThe customer " + customer.getName() + " is already booked\n");
            return;
        }

        if (room.isAvailable(startDate, endDate)) {
            Booking booking = new Booking(room, customer, startDate, endDate);
            bookings.add(booking);
            room.occupy(startDate, endDate);

            generateReport(room, customer, startDate, endDate);
            saveBookingInfo();
        }

    }


    /**
     * Registering a customer
     */
    public void registerCustomer(Customer customer) {
        registeredCustomers.add(customer);
        saveRegisteredCustomers();
    }

    /**
     * Registering Single room
     */
    public void registerSingleRoom() {
        registeredRooms.add(new SingleRoom());
        saveRegisteredRooms();
    }

    /**
     * Registering Double room
     */
    public void registerDoubleRoom() {
        registeredRooms.add(new DoubleRoom());
        saveRegisteredRooms();
    }

    /**
     * Registering Deluxe room
     */
    public void registerDeluxeRoom() {
        registeredRooms.add(new DeluxeRoom());
        saveRegisteredRooms();
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

    private double detectRoomPricePerDay(Room room) {
        if (room instanceof SingleRoom) {
            return SingleRoom.PRICE_PER_DAY;
        } else if (room instanceof DoubleRoom) {
            return DoubleRoom.PRICE_PER_DAY;
        } else if (room instanceof DeluxeRoom) {
            return DeluxeRoom.PRICE_PER_DAY;
        } else {
            System.out.println("There is something wrong with rooms");
            return 0;
        }
    }

    private void generateReport(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {

        double pricePerDay = detectRoomPricePerDay(room);

        double totalAmount = calculateTotalAmount(pricePerDay, startDate, endDate);
        double taxes = calculateTaxes(totalAmount);
        double serviceFee = calculateServiceFee(totalAmount);
        double finalAmount = totalAmount + taxes + serviceFee;

        String sb = "\nBooking successful! Here is your bill:\n" +
                "Room ID: " + room.getRoomId() + "\n" +
                "Room Type: " + room.getRoomType() + "\n" +
                "Customer Name: " + customer.getName() + "\n" +
                "Customer Email: " + customer.getEmail() + "\n" +
                "Booking Period: " + startDate + " to " + endDate + "\n" +
                "Price per day: $" + pricePerDay + "\n" +
                "Total Amount: $" + totalAmount + "\n" +
                "Taxes (20%): $" + taxes + "\n" +
                "Service Fee (10%): $" + serviceFee + "\n" +
                "Final Amount: $" + finalAmount + "\n";

        System.out.println(sb);

        addToFile(room, customer, startDate, endDate, finalAmount);
    }

    public void saveTheStateOfSystem(String path) {
        File file = new File(path);
        try (var oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
            oos.flush();
        } catch (IOException e) {
            System.out.println("\nFile not found!\n");
        }
    }

    public void loadTheStateOfSystem(String path) {
        File file = new File(path);
        try (var oos = new ObjectInputStream(new FileInputStream(file))) {
            var hotelSystem = (HotelRoomBookingSystem) oos.readObject();
            this.registeredRooms = hotelSystem.registeredRooms;
            this.registeredCustomers = hotelSystem.registeredCustomers;
            this.bookings = hotelSystem.bookings;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nFile not found or is not correct\n");
        }
    }

    public void showRegisteredRoomsAndRoomIDs() {
        System.out.print("Room IDs - ");
        for (Room room : registeredRooms) {
            System.out.print("[" + room.getRoomId() + "] ");
        }
        System.out.println();

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

    private void assignSavedObjectsIfNecacery() {
        //bookings
        try (var os = new ObjectInputStream(new FileInputStream(bookingInfo))) {
            bookings = (ArrayList<Booking>) os.readObject();
        } catch (IOException | ClassNotFoundException e) {}

        //customers
        try (var os = new ObjectInputStream(new FileInputStream(registeredCustomersInfo))) {
            registeredCustomers = (ArrayList<Customer>) os.readObject();
        } catch (IOException | ClassNotFoundException e) {}

        //rooms
        try (var os = new ObjectInputStream(new FileInputStream(registeredRoomsInfo))) {
            registeredRooms = (ArrayList<Room>) os.readObject();
        } catch (IOException | ClassNotFoundException e) {}

    }

    private void serializeAndSaveObjects() {
        saveBookingInfo();
        saveRegisteredCustomers();
        saveRegisteredRooms();
    }

    private void saveBookingInfo() {
        //Bookings
        try (var oos = new ObjectOutputStream(new FileOutputStream(bookingInfo))) {
            oos.writeObject(bookings);
            oos.flush();
        } catch (IOException e) {}
    }

    private void saveRegisteredCustomers() {
        //Customers
        try (var oos = new ObjectOutputStream(new FileOutputStream(registeredCustomersInfo))) {
            oos.writeObject(registeredCustomers);
            oos.flush();
        } catch (IOException e) {}
    }

    private void saveRegisteredRooms() {
        //Rooms
        try (var oos = new ObjectOutputStream(new FileOutputStream(registeredRoomsInfo))) {
            oos.writeObject(registeredRooms);
            oos.flush();
        } catch (IOException e) {}
    }
}

