package room;

import room.roomItems.*;

import java.io.Serial;
import java.io.Serializable;

public class DeluxeRoom extends Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 12755065143223L;

    public static final double PRICE_PER_DAY = 55;
    private final Bathtub bathtub;
    private final Minibar minibar;
    private final SittingArea sittingArea;


    public DeluxeRoom() {
        super(BedType.KING_SIZE_BED, "Deluxe room");
        bathtub = new Bathtub();
        minibar = new Minibar();
        sittingArea = new SittingArea();
    }

    public BedType getBED_TYPE() {
        return BED_TYPE;
    }

    public Bathtub getBathtub() {
        return bathtub;
    }


    public Minibar getMinibar() {
        return minibar;
    }

    public SittingArea getSittingArea() {
        return sittingArea;
    }
}
