public class AdminPage implements Page {

    @Override
    public void display() {
        System.out.println("\n========== ADMIN MENU ==========");
        System.out.println("1. Add Boat");
        System.out.println("2. Remove Boat");
        System.out.println("3. View All Boats");
        System.out.println("4. View All Rentals");
        System.out.println("0. Logout");
        System.out.println("================================");
        System.out.println("Choose: ");
    }

    @Override
    public void handleInput(int userInput) {
        if (userInput == 1) {
            System.out.println("Add Boat");
            addBoatMenu();
        } else if (userInput == 2) {
            System.out.println("Remove Boat");
            removeBoatMenu();
        } else if (userInput == 3) {
            System.out.println("View All Boats");
        } else if (userInput == 4) {
            System.out.println("View All Rentals");
        } else if (userInput == 0) {
            System.out.println("Logged out successfully!");
        }
    }

    public void addBoatMenu() {
        System.out.println("\n--- Add Boat ---");
    }

    public void removeBoatMenu() {
        System.out.println("\n--- Remove Boat ---");
    }
}