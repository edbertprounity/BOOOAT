import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class AdminModel {
    private BoatManager boatManager;
    private RentalManager rentalManager;

    private final ObservableList<Boat> allBoats = FXCollections.observableArrayList();
    private final ObservableList<RentRecord> allRentals = FXCollections.observableArrayList();

    private final SimpleDoubleProperty totalRevenue = new SimpleDoubleProperty(0.0);
    private final SimpleIntegerProperty totalRentalsCount = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty activeRentalsCount = new SimpleIntegerProperty(0);
    
    private final SimpleStringProperty addError = new SimpleStringProperty("");
    private final SimpleStringProperty removeError = new SimpleStringProperty("");

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

    public SimpleDoubleProperty totalRevenueProperty() {
        return totalRevenue;
    }

    public SimpleIntegerProperty totalRentalsCountProperty() {
        return totalRentalsCount;
    }

    public SimpleIntegerProperty activeRentalsCountProperty() {
        return activeRentalsCount;
    }

    public SimpleStringProperty addErrorProperty() {
        return addError;
    }

    public SimpleStringProperty removeErrorProperty() {
        return removeError;
    }

    public void setRentals(List<RentRecord> rentals) {
        this.allRentals.setAll(rentals);
    }

    public void setBoats(List<Boat> boats) {
        this.allBoats.setAll(boats);
    }

    public void updateStats() {
        if (rentalManager == null) {
            return;
        }

        double totalRev = 0;
        int activeCount = 0;
        List<RentRecord> all = rentalManager.getAllRecords();

        for (RentRecord r : all) {
            totalRev += r.getPrice();
            if (r.isActive()) {
                activeCount++;
            }
        }

        totalRevenue.set(totalRev);
        totalRentalsCount.set(all.size());
        activeRentalsCount.set(activeCount);
    }
}
