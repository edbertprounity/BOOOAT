import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BoatManager {
    private List<Boat> boats = new ArrayList<>();

    public void addBoat(Boat boat) {
        boats.add(boat);
    }

    public void removeBoat(Boat boat){
        boats.remove(boat);
    }

    public List<Boat> getAllBoats() {
        return boats;
    }

    public Boat findByName(String name) {
        for (Boat b : boats) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    public List<Boat> search(String keyword, Integer minCapacity, Integer maxCapacity, BoatType type, Double minPrice, Double maxPrice, String sortBy, String sortOrder) {

        List<Boat> result = new ArrayList<>();

        for (Boat b : this.getAllBoats()) {

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

            if (minPrice != null && b.getPrice() < minPrice) {
                continue;
            }

            if (maxPrice != null && b.getPrice() > maxPrice) {
                continue;
            }

            result.add(b);
        }

        if (sortBy != null) {
            if (sortBy.equals("price")) {
                Collections.sort(result, Comparator.comparingDouble(Boat::getPrice));
            } else if (sortBy.equals("capacity")) {
                Collections.sort(result, Comparator.comparingInt(Boat::getCapacity));
            } else if (sortBy.equals("type")) {
                Collections.sort(result, Comparator.comparing(BoatManager::getBoatTypeName));
            } else {
                Collections.sort(result, Comparator.comparing(Boat::getName));
            }

            if (sortOrder != null && sortOrder.equals("desc")) {
                Collections.reverse(result);
            }
        }
        
        return result;
    }

    private static String getBoatTypeName(Boat boat) {
        return boat.getType().name();
    }

    public List<Boat> findAvailableBoat() {
        List<Boat> availableBoats = new ArrayList<>();
        for (Boat b : this.getAllBoats()) {
            if (b.isAvailable()) {
                availableBoats.add(b);
            }
        }
        return availableBoats;
    }
}