public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public void addBoat(Boat boat) {
        BoatManager.addBoat(boat);;
    }

    public void removeBoat(Boat boat) {
        BoatManager.removeBoat(boat);
    }
}