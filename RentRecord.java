public class RentRecord {

    private Member member;
    private Boat boat;
    private double price;
    private boolean active;

    public RentRecord(Member member, Boat boat, double price) {
        this.member = member;
        this.boat = boat;
        this.price = price;
        this.active = true;
    }

    public Member getMember() {
        return member;
    }

    public Boat getBoat() {
        return boat;
    }

    public boolean isActive() {
        return active;
    }

    public void close() {
        this.active = false;
    }

    @Override
    public String toString() {
        return "Member: " + member.getUsername() + " \nBoat: " + boat.getName() +" \nPrice: " + price +"\nActive: " + active;
    }
}