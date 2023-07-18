package room;

import room.roomItems.BedType;

import java.io.Serial;
import java.io.Serializable;

public class DoubleRoom extends Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 12755065143222L;

    public static final double PRICE_PER_DAY = 35;

    public DoubleRoom() {
        super(BedType.DOUBLE_BED, "Double room");
    }
}
