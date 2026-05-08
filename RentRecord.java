import javafx.beans.property.SimpleDoubleProperty;

public class RentRecord {

    private Member member;
    private Boat boat;
    private boolean isActive;
    private final SimpleDoubleProperty priceProperty;

    public RentRecord(Member member, Boat boat, double price) {
        this.member = member;
        this.boat = boat;
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
        return priceProperty.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return priceProperty;
    }

    public boolean isActive() {
        return isActive;
    }

    public void close() {
        this.isActive = false;
        this.member.returnBoat(this.boat);
    }

    @Override
    public String toString() {
        return "Member: " + member.getUsername() + " \nBoat: " + boat.getName() +" \nPrice: " + getPrice() +"\nActive: " + isActive;
    }
}