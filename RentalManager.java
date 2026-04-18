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

        double price = calculatePrice(boat, duration, member);

        RentRecord record = new RentRecord(member, boat, price);
        records.add(record);

        System.out.println("Boat rented successfully. Total price: $" + price);
    }

    public void rentBoat(Member member, Boat boat, int duration, String discountCode) {

        if (member == null || boat == null) {
            System.out.println("Invalid input.");
            return;
        }

        if (!boat.isAvailable()) {
            System.out.println("Boat already rented.");
            return;
        }

        member.addRental(boat);

        double price = calculatePrice(boat, duration, member, discountCode);

        RentRecord record = new RentRecord(member, boat, price);
        records.add(record);

        System.out.println("Boat rented successfully. Total price: $" + price);
    }

    // Method overloading for price calculation
    // Version 1: Only membership discount
    public double calculatePrice(Boat boat, int duration, Member member) {
        return boat.getPrice() * duration * (1 - member.discount());
    }

    // Version 2: Both membership and discount code
    public double calculatePrice(Boat boat, int duration, Member member, String discountCode) {
        double membershipDiscount = member.discount();
        double codeDiscount = applyDiscountCode(discountCode);
        double totalDiscount = membershipDiscount + codeDiscount;
        
        if (totalDiscount > 0.5) {
            totalDiscount = 0.5; // Cap total discount at 50%
        }
        
        return boat.getPrice() * duration * (1 - totalDiscount);
    }

    private double applyDiscountCode(String code) {
        if (code == null || code.isEmpty()) {
            return 0;
        }
        
        if (code.equals("SAVE10")) {
            return 0.10;
        } else if (code.equals("SAVE20")) {
            return 0.20;
        } else if (code.equals("SUMMER")) {
            return 0.15;
        } else if (code.equals("WINTER")) {
            return 0.05;
        } else {
            System.out.println("Invalid discount code. No discount applied.");
            return 0;
        }
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