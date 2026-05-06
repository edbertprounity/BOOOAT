import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Boat {
    private String boatName;
    private double price;
    private boolean availability;
    private int capacity;
    private final BoatType BOAT_TYPE;

    // JavaFX Properties for UI Binding
    private final SimpleStringProperty nameProperty;
    private final SimpleDoubleProperty priceProperty;
    private final SimpleBooleanProperty availabilityProperty;
    private final SimpleIntegerProperty capacityProperty;
    private final SimpleObjectProperty<BoatType> typeProperty;

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

    public SimpleStringProperty nameProperty() {
        return nameProperty;
    }

    double getPrice() {
        return this.price;
    }

    public SimpleDoubleProperty priceProperty() {
        return priceProperty;
    }

    BoatType getType() {
        return this.BOAT_TYPE;
    }

    public SimpleObjectProperty<BoatType> typeProperty() {
        return typeProperty;
    }

    int getCapacity() {
        return this.capacity;
    }

    public SimpleIntegerProperty capacityProperty() {
        return capacityProperty;
    }

    boolean isAvailable() {
        return this.availability;
    }

    public SimpleBooleanProperty availabilityProperty() {
        return availabilityProperty;
    }

    void setAvailability(boolean availability) {
        this.availability = availability;
        this.availabilityProperty.set(availability);
    }

    @Override
    public String toString() {
        String status = "Not Available";
        if (this.availability) {
            status = "Available";
        }

        return "Boat Details: \n  Name: " + this.boatName + 
               "\n  Type: " + this.BOAT_TYPE + 
               "\n  Price: $" + this.price + "/day" +
               "\n  Capacity: " + this.capacity + " persons" +
               "\n  Status: " + status;
    }
}
