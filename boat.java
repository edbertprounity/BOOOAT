import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Boat {
    // JavaFX Properties for UI Binding
    private final SimpleStringProperty nameProperty = new SimpleStringProperty();
    private final SimpleDoubleProperty priceProperty = new SimpleDoubleProperty();
    private final SimpleBooleanProperty availabilityProperty = new SimpleBooleanProperty();
    private final SimpleIntegerProperty capacityProperty = new SimpleIntegerProperty();
    private final SimpleObjectProperty<BoatType> typeProperty = new SimpleObjectProperty<>();

    public Boat(String boatName, double price, BoatType boatType, int capacity){
        this.nameProperty.set(boatName);
        this.priceProperty.set(price);
        this.availabilityProperty.set(true);
        this.capacityProperty.set(capacity);
        this.typeProperty.set(boatType);
    }

    String getName() {
        return nameProperty.get();
    }

    public SimpleStringProperty nameProperty() {
        return nameProperty;
    }

    double getPrice() {
        return priceProperty.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return priceProperty;
    }

    BoatType getType() {
        return typeProperty.get();
    }

    public SimpleObjectProperty<BoatType> typeProperty() {
        return typeProperty;
    }

    int getCapacity() {
        return capacityProperty.get();
    }

    public SimpleIntegerProperty capacityProperty() {
        return capacityProperty;
    }

    boolean isAvailable() {
        return availabilityProperty.get();
    }

    public SimpleBooleanProperty availabilityProperty() {
        return availabilityProperty;
    }

    void setAvailability(boolean availability) {
        this.availabilityProperty.set(availability);
    }

    @Override
    public String toString() {
        return "Boat Details: \n  Name: " + getName() + 
               "\n  Type: " + getType() + 
               "\n  Price: $" + getPrice() + "/day" +
               "\n  Capacity: " + getCapacity() + " persons";
    }
}
