package room;

import room.roomItems.*;

import java.io.File;

/**
 * This class contains information about room
 * <p>-room type, room ID, bed type</p>
 */
public abstract class Room {

    private boolean isAvailable = true;

    private static int idGeneration = 0;

    private int roomId;
    private final String roomType;
    public final BedType BED_TYPE;
    private final Bathroom bathroom;

    private final TV tv;

    private final Closet closet;
    public Room(BedType bedType, String roomType) {
        bathroom = new Bathroom();
        tv = new TV();
        closet = new Closet();
        roomId = idGeneration++;
        BED_TYPE = bedType;
        this.roomType = roomType;
    }
    /**
     * changes the available room to occupied
     */
    public void occupied() {
        isAvailable = false;
    }

    public boolean isAvailable() {
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
