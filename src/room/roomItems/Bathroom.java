package room.roomItems;

import java.io.Serial;
import java.io.Serializable;

public class Bathroom implements Serializable {
    @Serial
    private static final long serialVersionUID = 12755065143221L;

    private boolean isShowerOn = false;

    public void showerOn() {
        isShowerOn = true;
    }
    public void showerOff() {
        isShowerOn = false;
    }

}