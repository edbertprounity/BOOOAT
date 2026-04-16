import java.util.ArrayList;
import java.util.List;

public class RentalManager {

    private List<RentRecord> records = new ArrayList<>();

    public void rentBoat(Member member, Boat boat, int duration) {

        if (member == null || boat == null) {
            System.out.println("Invalid input.");
            return;
        }

        if (!boat.isAvailable()) {
            System.out.println("Boat already rented.");
            return;
        }

        member.addRental(boat);

        double price = boat.getPrice() * duration * (1 - member.discount());

        RentRecord record = new RentRecord(member, boat, price);
        records.add(record);

        System.out.println("Boat rented successfully.");
    }

    public void returnBoat(Member member, Boat boat) {

        for (RentRecord r : records) {

            if (r.getMember() == member && r.getBoat() == boat && r.isActive()) {
                r.close();
                System.out.println("Boat returned successfully.");
                return;
            }
        }

        System.out.println("No active rental found.");
    }

    public List<RentRecord> getAllRecords() {
        return records;
    }

    public List<RentRecord> getActiveRecords() {

        List<RentRecord> result = new ArrayList<>();

        for (RentRecord r : records) {
            if (r.isActive()) {
                result.add(r);
            }
        }

        return result;
    }    
}