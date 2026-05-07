import java.util.ArrayList;
import java.util.List;

public class BoatManager {
    private List<Boat> boats = new ArrayList<>();

    public BoatManager() {
    }

    public void addBoat(Boat boat) {
        boats.add(boat);
    }

    public void removeBoat(Boat boat) {
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

    public List<Boat> search(String keyword, int minCapacity, int maxCapacity, BoatType type, double minPrice,
            double maxPrice) {

        List<Boat> result = new ArrayList<>();

        for (Boat b : this.getAllBoats()) {

            if (!b.isAvailable()) {
                continue;
            }

            if (keyword != null && !b.getName().contains(keyword)) {
                continue;
            }

            if (minCapacity != -1 && b.getCapacity() < minCapacity) {
                continue;
            }

            if (maxCapacity != -1 && b.getCapacity() > maxCapacity) {
                continue;
            }

            if (type != null && b.getType() != type) {
                continue;
            }

            if (minPrice != -1.0 && b.getPrice() < minPrice) {
                continue;
            }

            if (maxPrice != -1.0 && b.getPrice() > maxPrice) {
                continue;
            }

            result.add(b);
        }

        return result;
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