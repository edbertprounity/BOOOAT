import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private BoatManager boatManager;
    private RentalManager rentalManager;
    private AdminModel model;

    public AdminController(BoatManager boatManager, RentalManager rentalManager, AdminModel model) {
        this.boatManager = boatManager;
        this.rentalManager = rentalManager;
        this.model = model;
        refreshData();
    }

    public void addBoat(String name, String priceText, BoatType type, String capacityText) {
        if (name == null || name.trim().isEmpty()) return;
        if (type == null) return;

        double price;
        int capacity;
        try {
            price = Double.parseDouble(priceText);
            capacity = Integer.parseInt(capacityText);
        } catch (NumberFormatException e) {
            return;
        }

        if (price <= 0 || capacity <= 0) return;

        boatManager.addBoat(new Boat(name, price, type, capacity));
        refreshData();
    }

    public boolean removeBoat(Boat boat) {
        if (boat == null) return false;

        for (RentRecord record : rentalManager.getAllRecords()) {
            if (record.isActive() && record.getBoat() == boat) {
                return false;
            }
        }

        boatManager.removeBoat(boat);
        refreshData();
        return true;
    }

    public List<RentRecord> filterRentals(String type) {
        if (type.equals("Active")) {
            return rentalManager.getActiveRecords();
        } else if (type.equals("Completed")) {
            List<RentRecord> completed = new ArrayList<>();
            for (RentRecord r : rentalManager.getAllRecords()) {
                if (!r.isActive()) completed.add(r);
            }
            return completed;
        }
        return rentalManager.getAllRecords();
    }

    public void applyRentalFilter(String type) {
        List<RentRecord> filtered = filterRentals(type);
        model.setRentals(filtered);
    }

    public void refreshData() {
        List<Boat> boats = boatManager.getAllBoats();
        List<RentRecord> records = rentalManager.getAllRecords();

        model.setBoats(boats);
        model.setRentals(records);

        double revenue = 0;
        int active = 0;
        for (RentRecord r : records) {
            revenue += r.getPrice();
            if (r.isActive()) active++;
        }
        model.updateStats(revenue, records.size(), active);
    }
}
