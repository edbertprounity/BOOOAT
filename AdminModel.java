import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel {
    private final ObservableList<Boat> allBoats = FXCollections.observableArrayList();
    private final ObservableList<RentRecord> allRentals = FXCollections.observableArrayList();

    private final SimpleStringProperty totalRevenue = new SimpleStringProperty("$0.00");
    private final SimpleStringProperty totalRentalsCount = new SimpleStringProperty("0");
    private final SimpleStringProperty activeRentalsCount = new SimpleStringProperty("0");

    public ObservableList<Boat> getAllBoats() {
        return allBoats;
    }

    public ObservableList<RentRecord> getAllRentals() {
        return allRentals;
    }

    public SimpleStringProperty totalRevenueProperty() {
        return totalRevenue;
    }

    public SimpleStringProperty totalRentalsCountProperty() {
        return totalRentalsCount;
    }

    public SimpleStringProperty activeRentalsCountProperty() {
        return activeRentalsCount;
    }

    public void setBoats(java.util.List<Boat> boats) {
        this.allBoats.setAll(boats);
    }

    public void setRentals(java.util.List<RentRecord> rentals) {
        this.allRentals.setAll(rentals);
    }

    public void updateStats(double revenue, int total, int active) {
        this.totalRevenue.set(String.format("$%.2f", revenue));
        this.totalRentalsCount.set(String.valueOf(total));
        this.activeRentalsCount.set(String.valueOf(active));
    }
}
