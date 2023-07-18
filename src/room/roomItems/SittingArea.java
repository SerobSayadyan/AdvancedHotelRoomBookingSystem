package room.roomItems;

import java.io.Serial;
import java.io.Serializable;

public class SittingArea implements Serializable {

    @Serial
    private static final long serialVersionUID = 12755065143226L;

    private final int NUMBER_OF_COUCHES = 2;
    private final int NUMBER_OF_CHAIRS = 6;

    public int getNumberOfCouches() {
        return NUMBER_OF_COUCHES;
    }

    public int getNumberOfChairs() {
        return NUMBER_OF_CHAIRS;
    }
}
