package room.roomItems;

public class Bathtub {

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
