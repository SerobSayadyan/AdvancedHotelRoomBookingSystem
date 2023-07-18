package room.roomItems;

import java.io.Serial;
import java.io.Serializable;

public class Closet implements Serializable {

    @Serial
    private static final long serialVersionUID = 12755065143224L;

    private final int capacityOfCloset = 30;
    private int totalAmountOfClothes;

    public void addClothe() {
        if (totalAmountOfClothes + 1 <= capacityOfCloset) {
            totalAmountOfClothes++;
        } else {
            System.out.println("The closet is full");
        }
    }

    public void removeClothe() {
        if (totalAmountOfClothes - 1 >= 0) {
            totalAmountOfClothes--;
        } else {
            System.out.println("The closet is empty");
        }
    }

}
