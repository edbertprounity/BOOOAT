import java.util.List;
import java.util.ArrayList;

public class Member extends User {
    private List<Boat> rentalHistory;
    private List<Boat> currentRental;

    public Member(String username, String password) {
        super(username, password);
        this.rentalHistory = new ArrayList<>();
        this.currentRental = new ArrayList<>();
    }

    public void addRental(Boat boat) {
        currentRental.add(boat);
    }

    public void returnBoat(Boat boat) {
        currentRental.remove(boat);
        rentalHistory.add(boat);
    }

    public String getRentalHistory() {
        return getListItem(rentalHistory);
    }

    public String getCurrentRental() {
        return getListItem(currentRental);
    }
}
