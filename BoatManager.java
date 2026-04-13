import java.util.ArrayList;
import java.util.List;

public class BoatManager {
    private static List<Boat> boats = new ArrayList<>();

    public static void addBoat(Boat boat) {
        boats.add(boat);
    }

    public static void removeBoat(Boat boat){
        boats.remove(boat);
    }

    public static List<Boat> getAllBoats() {
        return boats;
    }

    public static Boat findByName(String name) {
        for (Boat b : boats) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    public static List<Boat> search(String keyword, Integer minCapacity, Integer maxCapacity, BoatType type) {

        List<Boat> result = new ArrayList<>();

        for (Boat b : BoatManager.getAllBoats()) {

            if (!b.isAvailable()){
                continue;
            } 

            if (keyword != null && !b.getName().equals(keyword)) {
                continue;
            }

            if (minCapacity != null && b.getCapacity() < minCapacity) {
                continue;
            }

            if (maxCapacity != null && b.getCapacity() > maxCapacity) {
                continue;
            }

            if (type != null && b.getType() != type) {
                continue;
            }

            result.add(b);
        }
        return result;
    }
}