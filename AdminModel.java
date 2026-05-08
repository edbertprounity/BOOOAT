import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel {
    private BoatManager boatManager;
    private RentalManager rentalManager;

    private final ObservableList<Boat> allBoats = FXCollections.observableArrayList();
    private final ObservableList<RentRecord> allRentals = FXCollections.observableArrayList();

    private final SimpleStringProperty totalRevenue = new SimpleStringProperty("$0.00");
    private final SimpleStringProperty totalRentalsCount = new SimpleStringProperty("0");
    private final SimpleStringProperty activeRentalsCount = new SimpleStringProperty("0");

    public AdminModel() {
    }

    public void setBoatManager(BoatManager boatManager) {
        this.boatManager = boatManager;
    }

    public BoatManager getBoatManager() {
        return boatManager;
    }

    public void setRentalManager(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
    }

    public RentalManager getRentalManager() {
        return rentalManager;
    }

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

    public void setRentals(java.util.List<RentRecord> rentals) {
        this.allRentals.setAll(rentals);
    }
}
