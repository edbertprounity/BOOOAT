import java.util.List;
import java.util.ArrayList;

public class Member extends User {
    private List<Boat> rentalHistory;
    private List<Boat> currentRental;
    private MemberType memberShip;
    private int point;

    public Member(String memberName, String username, String password) {
        super(memberName, username, password);
        this.rentalHistory = new ArrayList<>();
        this.currentRental = new ArrayList<>();
        this.memberShip = MemberType.SILVER;
        this.point = 0;
    }

    public void addRental(Boat boat) {
        currentRental.add(boat);
        this.point += 3000;
        boat.setAvailability(false);
    }

    public void returnBoat(Boat boat) {
        currentRental.remove(boat);
        rentalHistory.add(boat);
        boat.setAvailability(true);
    }

    public List<Boat> getRentalHistory() {
        return rentalHistory;
    }

    public List<Boat> getCurrentRental() {
        return currentRental;
    }

    public MemberType getMemberShip(){
        return this.memberShip;
    }

    public double discount(){
        if(this.memberShip == MemberType.PLATINUM){
            return 0.2;
        }else if (this.memberShip == MemberType.GOLD){
            return 0.12;
        } 

        return 0;
    }

    public void confirmMemberShip(){
        if (this.point > 40000){
            this.memberShip = MemberType.PLATINUM;
        }else if (this.point > 15000){
            this.memberShip = MemberType.GOLD;
        }else {
            this.memberShip = MemberType.SILVER;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member[name=" + name + ", username=" + username + ", membership=" + memberShip + ", points=" + point + "]";
    }
} 