package room.roomItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Minibar {

    private List<String> minibarDrinks;

    public Minibar() {
        minibarDrinks = new ArrayList<>();
    }

    public void addMinibarDrinks(String... drinks) {
        minibarDrinks.addAll(Arrays.stream(drinks).toList());
    }

    public String takeFromMinibar(String drink) {
        for (String d : minibarDrinks) {
            if (d.equals(drink)) {
                return d;
            }
        }
        System.out.println("There is no such drink in minibar");
        return "";
    }

}
