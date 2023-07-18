package room;

import room.roomItems.BedType;

import java.io.Serial;
import java.io.Serializable;

public class SingleRoom extends Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 12755065143221L;

    public static final double PRICE_PER_DAY = 20;

    public SingleRoom() {
        super(BedType.SINGLE_BED, "Single room");
    }
}
