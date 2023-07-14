import room.Room;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var bookingSystem = new HotelRoomBookingSystem();

        // creating customers
        for (int i = 0; i < 10; i++) {
            bookingSystem.registerCustomer(new Customer("customer" + i, "customer" + i + "@mail.com"));
        }

    //registering rooms
        //single rooms
        bookingSystem.registerSingleRoom();
        bookingSystem.registerSingleRoom();
        bookingSystem.registerSingleRoom();
        bookingSystem.registerSingleRoom();
        bookingSystem.registerSingleRoom();
        //double rooms
        bookingSystem.registerDoubleRoom();
        bookingSystem.registerDoubleRoom();
        bookingSystem.registerDoubleRoom();
        bookingSystem.registerDoubleRoom();
        //deluxe room
        bookingSystem.registerDeluxeRoom();

        //booking customers
        final int SIZE = bookingSystem.getRegisteredRooms().size();
        for (int i = 0; i < SIZE; i++) {
            Customer customer = bookingSystem.getRegisteredCustomers().get(i);
            Room room = bookingSystem.getRegisteredRooms().get(i);
            bookingSystem.bookRoom(customer.getName(), room.getRoomId(),
                    //from
                    LocalDate.ofYearDay(2023, 90 + i),
                    //to
                    LocalDate.ofYearDay(2023, 96 + i));

        }


    }
}