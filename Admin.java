public class Admin extends User {

    public Admin(String adminName, String username, String password) {
        super(adminName, username, password);
    }

    public void addBoat(BoatManager manager, Boat boat) {
        manager.addBoat(boat);;
    }

    public void removeBoat(BoatManager manager, Boat boat) {
        manager.removeBoat(boat);
    }
}