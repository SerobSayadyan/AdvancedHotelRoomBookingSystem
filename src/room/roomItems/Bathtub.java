package room.roomItems;

import java.io.Serial;
import java.io.Serializable;

public class Bathtub implements Serializable {
    @Serial
    private static final long serialVersionUID = 12755065143222L;

    private boolean isFull = false;

    public void fillTheBathtub() {
        isFull = true;
    }

    public void emptyTheBathtub() {
        isFull = false;
    }

    public boolean isBathtubFull() {
        return isFull;
    }

}
