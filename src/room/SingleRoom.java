package room;

import room.roomItems.BedType;

public class SingleRoom extends Room {

    public static final double PRICE_PER_DAY = 20;

    public SingleRoom() {
        super(BedType.SINGLE_BED, "Single room");
    }
}
