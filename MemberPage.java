public class MemberPage implements Page {

    @Override
    public void display() {
        System.out.println("\n========== MEMBER MENU ==========");
        System.out.println("1. View All Boats");
        System.out.println("2. Search Boats");
        System.out.println("3. Rent Boat");
        System.out.println("4. Return Boat");
        System.out.println("5. View My Rentals");
        System.out.println("0. Logout");
        System.out.println("=================================");
        System.out.print("Choose: ");
    }

    @Override
    public void handleInput(int userInput) {
        if (userInput == 1) {
            System.out.println("View All Boats");
        } else if (userInput == 2) {
            System.out.println("Search Boats");
            searchMenu();
        } else if (userInput == 3) {
            System.out.println("Rent Boat");
            rentMenu();
        } else if (userInput == 4) {
            System.out.println("Return Boat");
            returnMenu();
        } else if (userInput == 5) {
            System.out.println("View My Rentals");
        } else if (userInput == 0) {
            System.out.println("Logged out successfully!");
        }
    }

    public void searchMenu() {
        System.out.println("\n--- Search Boats ---");
    }

    public void rentMenu() {
        System.out.println("\n--- Rent Boat ---");
    }

    public void returnMenu() {
        System.out.println("\n--- Return Boat ---");
    }
}