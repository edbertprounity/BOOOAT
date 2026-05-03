import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MemberModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty points = new SimpleStringProperty("0");
    private final StringProperty tier = new SimpleStringProperty("");
    private final StringProperty discount = new SimpleStringProperty("0%");

    private final StringProperty searchKeyword = new SimpleStringProperty(null);
    private final ObjectProperty<Integer> minCapacity = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Integer> maxCapacity = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Double> minPrice = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Double> maxPrice = new SimpleObjectProperty<>(null);
    private final ObjectProperty<BoatType> filterType = new SimpleObjectProperty<>(null);

    private final ObservableList<Boat> availableBoats = FXCollections.observableArrayList();
    private final ObservableList<Boat> currentRentals = FXCollections.observableArrayList();
    private final ObservableList<Boat> rentalHistory = FXCollections.observableArrayList();

    public StringProperty nameProperty() { return name; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty pointsProperty() { return points; }
    public StringProperty tierProperty() { return tier; }
    public StringProperty discountProperty() { return discount; }

    public StringProperty searchKeywordProperty() { return searchKeyword; }
    public ObjectProperty<Integer> minCapacityProperty() { return minCapacity; }
    public ObjectProperty<Integer> maxCapacityProperty() { return maxCapacity; }
    public ObjectProperty<Double> minPriceProperty() { return minPrice; }
    public ObjectProperty<Double> maxPriceProperty() { return maxPrice; }
    public ObjectProperty<BoatType> filterTypeProperty() { return filterType; }

    public String getSearchKeyword() { return searchKeyword.get(); }
    public Integer getMinCapacity() { return minCapacity.get(); }
    public Integer getMaxCapacity() { return maxCapacity.get(); }
    public Double getMinPrice() { return minPrice.get(); }
    public Double getMaxPrice() { return maxPrice.get(); }
    public BoatType getFilterType() { return filterType.get(); }

    public ObservableList<Boat> getAvailableBoats() { return availableBoats; }
    public ObservableList<Boat> getCurrentRentals() { return currentRentals; }
    public ObservableList<Boat> getRentalHistory() { return rentalHistory; }

    public void setMemberData(Member member) {
        this.name.set(member.getName());
        this.username.set(member.getUsername());
        this.points.set(String.valueOf(member.getPoint()));
        this.tier.set(member.getMembership().toString());
        this.discount.set(String.format("%.0f%%", member.discount() * 100));
        this.currentRentals.setAll(member.getCurrentRental());
        this.rentalHistory.setAll(member.getRentalHistory());
    }

    public void setAvailableBoats(java.util.List<Boat> boats) {
        this.availableBoats.setAll(boats);
    }

    public void clearFilters() {
        searchKeyword.set(null);
        minCapacity.set(null);
        maxCapacity.set(null);
        minPrice.set(null);
        maxPrice.set(null);
        filterType.set(null);
    }
}
