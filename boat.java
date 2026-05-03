import javafx.beans.property.*;

public class Boat {
    private String boatName;
    private double price;
    private boolean availability;
    private int capacity;
    private final BoatType BOAT_TYPE;

    // JavaFX Properties for UI Binding
    private final StringProperty nameProperty;
    private final DoubleProperty priceProperty;
    private final BooleanProperty availabilityProperty;
    private final IntegerProperty capacityProperty;
    private final ObjectProperty<BoatType> typeProperty;

    public Boat(String boatName, double price, BoatType boatType, int capacity){
        this.boatName = boatName;
        this.price = price;
        this.availability = true;
        this.BOAT_TYPE = boatType;
        this.capacity = capacity;

        this.nameProperty = new SimpleStringProperty(boatName);
        this.priceProperty = new SimpleDoubleProperty(price);
        this.availabilityProperty = new SimpleBooleanProperty(true);
        this.capacityProperty = new SimpleIntegerProperty(capacity);
        this.typeProperty = new SimpleObjectProperty<>(boatType);
    }

    String getName() {
        return this.boatName;
    }

    public StringProperty nameProperty() { return nameProperty; }

    double getPrice() {
        return this.price;
    }

    public DoubleProperty priceProperty() { return priceProperty; }

    BoatType getType() {
        return this.BOAT_TYPE;
    }

    public ObjectProperty<BoatType> typeProperty() { return typeProperty; }

    int getCapacity() {
        return this.capacity;
    }

    public IntegerProperty capacityProperty() { return capacityProperty; }

    boolean isAvailable() {
        return this.availability;
    }

    public BooleanProperty availabilityProperty() { return availabilityProperty; }

    void setAvailability(boolean availability) {
        this.availability = availability;
        this.availabilityProperty.set(availability);
    }

    @Override
    public String toString(){
        return "Boat Details: \n  Name: " + this.boatName + 
               "\n  Type: " + this.BOAT_TYPE + 
               "\n  Price: $" + this.price + "/day" +
               "\n  Capacity: " + this.capacity + " persons" +
               "\n  Status: " + (this.availability ? "Available" : "Not Available");
    }
}
