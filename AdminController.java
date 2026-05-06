import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private AdminModel model;

    public AdminController(AdminModel model) {
        this.model = model;
        refreshData();
    }

    public void addBoat(String name, String priceStr, BoatType type, String capacityStr) {
        double price = convertStringToDouble(priceStr);
        int capacity = convertStringToInt(capacityStr);

        if (name == null || name.trim().isEmpty()) {
            return;
        }
        if (type == null) {
            return;
        }

        if (price <= 0 || capacity <= 0) {
            return;
        }

        model.getBoatManager().addBoat(new Boat(name, price, type, capacity));
        refreshData();
    }

    private int convertStringToInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if ("-".equals(s)) {
            return 0;
        }
        return Integer.parseInt(s); // Convert string into integer
    }

    private double convertStringToDouble(String s) {
        if (s == null || s.isEmpty()) {
            return 0.0;
        }
        if ("-".equals(s)) {
            return 0.0;
        }
        return Double.parseDouble(s);
    }

    public boolean removeBoat(Boat boat) {
        if (boat == null) {
            return false;
        }

        for (RentRecord record : model.getRentalManager().getAllRecords()) {
            if (record.isActive() && record.getBoat() == boat) {
                return false;
            }
        }

        model.getBoatManager().removeBoat(boat);
        refreshData();
        return true;
    }

    public List<RentRecord> filterRentals(String type) {
        if (type.equals("Active")) {
            return model.getRentalManager().getActiveRecords();
        } 
        if (type.equals("Completed")) {
            List<RentRecord> completed = new ArrayList<>();
            for (RentRecord r : model.getRentalManager().getAllRecords()) {
                if (!r.isActive()) {
                    completed.add(r);
                }
            }
            return completed;
        }
        return model.getRentalManager().getAllRecords();
    }

    public void applyRentalFilter(String type) {
        List<RentRecord> filtered = filterRentals(type);
        model.setRentals(filtered);
    }

    public void refreshData() {
        List<Boat> boats = model.getBoatManager().getAllBoats();
        List<RentRecord> records = model.getRentalManager().getAllRecords();

        model.setBoats(boats);
        model.setRentals(records);

        double revenue = 0;
        int active = 0;
        for (RentRecord r : records) {
            revenue += r.getPrice();
            if (r.isActive()) {
                active++;
            }
        }
        model.updateStats(revenue, records.size(), active);
    }
}
