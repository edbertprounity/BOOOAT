public class Boat {
    private String boatName;
    private double price;
    private boolean availability;
    private int capacity;
    private final BoatType BOAT_TYPE;

<<<<<<< HEAD
    public Boat(String boatName, double price, BoatType boatType, int capacity){
=======
    public Boat(String boatName, double price, BoatType boatType) {
>>>>>>> 1e62c708298f94d6cb6e20464f90b4547dc89ac3
        this.boatName = boatName;
        this.price = price;
        this.availability = true;
        this.BOAT_TYPE = boatType;
        this.capacity = capacity;
    }

    String getName() {
        return this.boatName;
    }

    double getPrice() {
        return this.price;
    }

    BoatType getType() {
        return this.BOAT_TYPE;
    }

    int getCapacity() {
        return this.capacity;
    }

    boolean isAvailable() {
        return this.availability;
    }

    void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
<<<<<<< HEAD
    public String toString(){
        return "Boat Details: \n  Name: " + this.boatName + 
               "\n  Type: " + this.BOAT_TYPE + 
               "\n  Price: $" + this.price + "/day" +
               "\n  Capacity: " + this.capacity + " persons" +
               "\n  Status: " + (this.availability ? "Available" : "Not Available");
=======
    public String toString() {
        return "Boat Details: \nBoat Name: " + this.boatName + " (" + this.BOAT_TYPE + ")";
>>>>>>> 1e62c708298f94d6cb6e20464f90b4547dc89ac3
    }
}
