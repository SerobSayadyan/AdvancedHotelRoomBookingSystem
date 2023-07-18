package room.roomItems;

import java.io.Serial;
import java.io.Serializable;

public class TV implements Serializable {

    @Serial
    private static final long serialVersionUID = 12755065143227L;

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
