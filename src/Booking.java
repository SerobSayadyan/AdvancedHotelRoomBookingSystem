import room.Room;

import java.io.File;
import java.time.LocalDate;

/**
 * This class contains information about booked room and customer accordingly, also the period of booking
 */
public class Booking {

    private File bookingHistoryFile;
    private Room room;
    private Customer customer;
    private LocalDate startDate;

    private LocalDate endDate;

    public Booking(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        bookingHistoryFile = new File("room N" + room.getRoomId() + ".txt");
    }

    public File getBookingHistoryFile() {
        return bookingHistoryFile;
    }

    public void printBookingHistoryFileAbsolutePath() {
        System.out.println(bookingHistoryFile.getAbsoluteFile());
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "room=" + room +
                ", customer=" + customer +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
