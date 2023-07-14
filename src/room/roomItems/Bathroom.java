package room.roomItems;

public class Bathroom {
    private boolean isShowerOn = false;
    private final Toilet toilet = new Toilet();

    public Toilet useToilet() {
        return toilet;
    }

    public void showerOn() {
        isShowerOn = true;
    }
    public void showerOff() {
        isShowerOn = false;
    }

    static class Toilet {

    }
}
