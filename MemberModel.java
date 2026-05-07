import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MemberModel {
    private BoatManager boatManager;
    private RentalManager rentalManager;
    private Member member;

    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty username = new SimpleStringProperty("");
    private final SimpleStringProperty points = new SimpleStringProperty("0");
    private final SimpleStringProperty tier = new SimpleStringProperty("");
    private final SimpleStringProperty discount = new SimpleStringProperty("0%");
    private final SimpleStringProperty welcomeText = new SimpleStringProperty("");

    // UI State for Dialogs
    private final SimpleStringProperty passwordError = new SimpleStringProperty("");
    private final SimpleIntegerProperty rentalDuration = new SimpleIntegerProperty(1);
    private final SimpleStringProperty appliedDiscountCode = new SimpleStringProperty("");
    private final SimpleDoubleProperty rentalTotalPrice = new SimpleDoubleProperty(0.0);

    private final SimpleStringProperty searchKeyword = new SimpleStringProperty(null);
    private final SimpleIntegerProperty minCapacity = new SimpleIntegerProperty(-1);
    private final SimpleIntegerProperty maxCapacity = new SimpleIntegerProperty(-1);
    private final SimpleDoubleProperty minPrice = new SimpleDoubleProperty(-1.0);
    private final SimpleDoubleProperty maxPrice = new SimpleDoubleProperty(-1.0);
    private final SimpleObjectProperty<BoatType> filterType = new SimpleObjectProperty<>(null);

    private final ObservableList<Boat> availableBoats = FXCollections.observableArrayList();
    private final ObservableList<Boat> currentRentals = FXCollections.observableArrayList();
    private final ObservableList<Boat> rentalHistory = FXCollections.observableArrayList();

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

    public void setMember(Member member) {
        this.member = member;
        setMemberData(member);
    }

    public Member getMember() {
        return member;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty pointsProperty() {
        return points;
    }

    public SimpleStringProperty tierProperty() {
        return tier;
    }

    public SimpleStringProperty discountProperty() {
        return discount;
    }

    public SimpleStringProperty welcomeTextProperty() {
        return welcomeText;
    }

    public SimpleStringProperty passwordErrorProperty() {
        return passwordError;
    }

    public SimpleIntegerProperty rentalDurationProperty() {
        return rentalDuration;
    }

    public SimpleStringProperty appliedDiscountCodeProperty() {
        return appliedDiscountCode;
    }

    public SimpleDoubleProperty rentalTotalPriceProperty() {
        return rentalTotalPrice;
    }

    public SimpleStringProperty searchKeywordProperty() {
        return searchKeyword;
    }

    public SimpleIntegerProperty minCapacityProperty() {
        return minCapacity;
    }

    public SimpleIntegerProperty maxCapacityProperty() {
        return maxCapacity;
    }

    public SimpleDoubleProperty minPriceProperty() {
        return minPrice;
    }

    public SimpleDoubleProperty maxPriceProperty() {
        return maxPrice;
    }

    public SimpleObjectProperty<BoatType> filterTypeProperty() {
        return filterType;
    }

    public String getSearchKeyword() {
        return searchKeyword.get();
    }

    public int getMinCapacity() {
        return minCapacity.get();
    }

    public int getMaxCapacity() {
        return maxCapacity.get();
    }

    public double getMinPrice() {
        return minPrice.get();
    }

    public double getMaxPrice() {
        return maxPrice.get();
    }

    public BoatType getFilterType() {
        return filterType.get();
    }

    public ObservableList<Boat> getAvailableBoats() {
        return availableBoats;
    }

    public ObservableList<Boat> getCurrentRentals() {
        return currentRentals;
    }

    public ObservableList<Boat> getRentalHistory() {
        return rentalHistory;
    }

    public void setMemberData(Member member) {
        this.name.set(member.getName());
        this.username.set(member.getUsername());
        this.points.set("" + member.getPoint());
        this.tier.set(member.getMembership().toString());
        this.discount.set(String.format("%.0f%%", member.discount() * 100));
        this.currentRentals.setAll(member.getCurrentRental());
        this.rentalHistory.setAll(member.getRentalHistory());
        this.welcomeText.set(member.getName() + " [" + member.getMembership() + "]");
    }

    public void setAvailableBoats(java.util.List<Boat> boats) {
        this.availableBoats.setAll(boats);
    }

    public void clearFilters() {
        searchKeyword.set(null);
        minCapacity.set(-1);
        maxCapacity.set(-1);
        minPrice.set(-1.0);
        maxPrice.set(-1.0);
        filterType.set(null);
    }
}
