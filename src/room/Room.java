package room;

import room.roomItems.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information about room
 * <p>-room type, room ID, bed type</p>
 */
public abstract class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1275506514322L;

    private boolean isAvailable = true;

    private static int idGeneration = 0;

    private int roomId;
    private final String roomType;
    public final BedType BED_TYPE;
    private final Bathroom bathroom;
    private final TV tv;
    private final Closet closet;
    private final List<Period> reservedPeriods;

    public Room(BedType bedType, String roomType) {
        bathroom = new Bathroom();
        tv = new TV();
        closet = new Closet();
        roomId = idGeneration++;
        BED_TYPE = bedType;
        this.roomType = roomType;
        reservedPeriods = new ArrayList<>();
    }

    /**
     * changes the available room to occupied
     */
    public void occupy(LocalDate startDate, LocalDate endDate) {
        reservedPeriods.add(Period.between(startDate, endDate));
    }

    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        for (Period period : reservedPeriods) {
            if (period.equals(Period.between(startDate, endDate))) {
                System.out.println("The room is not available form '" + startDate + "' to '" + endDate + "'");
                isAvailable = false;
                return false;
            }
        }
        return isAvailable;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getRoomId() {
        return roomId;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public TV getTv() {
        return tv;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Closet getCloset() {
        return closet;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}
