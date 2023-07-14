package room;

import room.roomItems.BedType;

public class DoubleRoom extends Room {
    public static final double PRICE_PER_DAY = 35;

    public DoubleRoom() {
        super(BedType.DOUBLE_BED, "Double room");
    }
}
