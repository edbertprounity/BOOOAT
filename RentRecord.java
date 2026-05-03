import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RentRecord {

    private Member member;
    private Boat boat;
    private double price;
    private boolean isActive;
    private final DoubleProperty priceProperty;

    public RentRecord(Member member, Boat boat, double price) {
        this.member = member;
        this.boat = boat;
        this.price = price;
        this.isActive = true;
        this.priceProperty = new SimpleDoubleProperty(price);
    }

    public Member getMember() {
        return member;
    }

    public Boat getBoat() {
        return boat;
    }

    public double getPrice() {
        return price;
    }

    public DoubleProperty priceProperty() { return priceProperty; }

    public boolean isActive() {
        return isActive;
    }

    public void close() {
        this.isActive = false;
        this.member.returnBoat(this.boat);
    }

    @Override
    public String toString() {
        return "Member: " + member.getUsername() + " \nBoat: " + boat.getName() +" \nPrice: " + price +"\nActive: " + isActive;
    }
}