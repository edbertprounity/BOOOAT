import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Member extends User {
    private ObservableList<Boat> rentalHistory;
    private ObservableList<Boat> currentRental;
    private MemberType membership;
    private int point;

    public Member(String memberName, String username, String password) {
        super(memberName, username, password);
        this.rentalHistory = FXCollections.observableArrayList();
        this.currentRental = FXCollections.observableArrayList();
        this.membership = MemberType.SILVER;
        this.point = 0;
    }

    // Member.java — addRental() is identical to Project A
    public void addRental(Boat boat) {
        currentRental.add(boat);    // ObservableList in B, ArrayList in A
        this.point += 3000;
        boat.setAvailability(false);    // triggers Boat's SimpleBooleanProperty
    }

    public void returnBoat(Boat boat) {
        currentRental.remove(boat);
        rentalHistory.add(boat);
        boat.setAvailability(true);
    }

    public ObservableList<Boat> getRentalHistory() {
        return rentalHistory;
    }

    public ObservableList<Boat> getCurrentRental() {
        return currentRental;
    }

    public MemberType getMembership() {
        return this.membership;
    }

    public int getPoint() {
        return this.point;
    }

    public double discount() {
        if (this.membership == MemberType.PLATINUM) {
            return 0.2;
        } else if (this.membership == MemberType.GOLD) {
            return 0.12;
        }

        return 0;
    }

    public void confirmMembership() {
        if (this.point > 40000) {
            this.membership = MemberType.PLATINUM;
        } else if (this.point > 15000) {
            this.membership = MemberType.GOLD;
        } else {
            this.membership = MemberType.SILVER;
        }
    }

    public void setName(String name) {
        this.nameProperty().set(name);
    }

    @Override
    public String toString() {
        return "Member[name=" + getName() + ", username=" + getUsername() + ", membership=" + membership + ", points=" + point
                + "]";
    }
}