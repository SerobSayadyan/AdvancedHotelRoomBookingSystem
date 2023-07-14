package room.roomItems;

public class TV {
    private final int totalChannels = 100;
    private int currentChannel;

    public int getTotalChannels() {
        return totalChannels;
    }
    public void changeCurrentChannel(int channel) {
        if (channel >= 0 && channel <= 100) {
            currentChannel = channel;
        } else {
            System.out.println("there is no such channel");
        }
    }
}
