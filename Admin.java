public class Admin extends User {

    public Admin(String adminName, String username, String password) {
        super(adminName, username, password);
    }

    public void addBoat(Boat boat) {
        BoatManager.addBoat(boat);;
    }

    public void removeBoat(Boat boat) {
        BoatManager.removeBoat(boat);
    }
}