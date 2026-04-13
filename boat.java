public class Boat {
    private String boatName;
    private double price;
    private boolean availability;
    private int capacity;
    private final BoatType BOAT_TYPE;

    public Boat(String boatName, double price, BoatType boatType){
        this.boatName = boatName;
        this.price = price;
        this.availability = true;
        this.BOAT_TYPE = boatType;
    }

    String getName(){
        return this.boatName;
    }

    double getPrice(){
        return this.price;
    }

    BoatType getType(){
        return this.BOAT_TYPE;
    }

    int getCapacity(){
        return this.capacity;
    }

    boolean isAvailable(){
        return this.availability;
    }

    void setAvailability(boolean availability){
        this.availability = availability;
    }

    @Override
    public String toString(){
        return "Boat Details: \nBoat Name: " + this.boatName + " (" + this.BOAT_TYPE + ")";
    }
}

