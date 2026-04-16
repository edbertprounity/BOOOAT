import java.util.List;

public class MemberPage implements Page {
    private BoatManager boatManager;
    private Member member;

    public MemberPage(BoatManager boatManager, Member member) {
        this.boatManager = boatManager;
        this.member = member;
    }

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
            viewAllBoats();
        } else if (userInput == 2) {
            searchBoat();
        } else if (userInput == 3) {
            rentMenu();
        } else if (userInput == 4) {
            returnMenu();
        } else if (userInput == 5) {
            viewRentalHistory();
        } else if (userInput == 0) {
            System.out.println("Logged out successfully!");
        }
    }

    public void viewAllBoats() {
        System.out.println("\n========== AVAILABLE BOATS ==========");
        List<Boat> boats = boatManager.getAllBoats();
        
        if (boats.isEmpty()) {
            System.out.println("No boats available.");
            return;
        }
        
        boolean hasAvailable = false;
        for (Boat boat : boats) {
            if (boat.isAvailable()) {
                System.out.println(boat);
                System.out.println("Price: " + boat.getPrice());
                System.out.println("---");
                hasAvailable = true;
            }
        }
        
        if (!hasAvailable) {
            System.out.println("No boats available at the moment.");
        }
    }

    public void searchBoat() {
        System.out.println("\n========== SEARCH BOATS ==========");
        
        String nameKeyword = null;
        Integer minCapacity = null;
        Integer maxCapacity = null;
        BoatType type = null;
        
        System.out.println("Enter search filters (press Enter to skip strings, 0 to skip numbers):");
        
        System.out.print("Boat name (or press Enter to skip): ");
        String name = In.nextLine();
        if (!name.isEmpty()) {
            nameKeyword = name;
        }
        
        System.out.print("Minimum capacity (or 0): ");
        int minCap = In.nextInt();
        if (minCap != 0) {
            minCapacity = minCap;
        }
        
        System.out.print("Maximum capacity (or 0): ");
        int maxCap = In.nextInt();
        if (maxCap != 0) {
            maxCapacity = maxCap;
        }
        
        System.out.println("Boat types:");
        System.out.println("1. SPEED_BOAT");
        System.out.println("2. FISHING_BOAT");
        System.out.println("3. KAYAK");
        System.out.println("4. SAIL_BOAT");
        System.out.println("5. JET_SKI");
        System.out.println("0. Skip boat type filter");
        System.out.print("Choose boat type (or 0): ");
        
        int typeChoice = In.nextInt();
        
        if (typeChoice == 1) {
            type = BoatType.SPEED_BOAT;
        } else if (typeChoice == 2) {
            type = BoatType.FISHING_BOAT;
        } else if (typeChoice == 3) {
            type = BoatType.KAYAK;
        } else if (typeChoice == 4) {
            type = BoatType.SAIL_BOAT;
        } else if (typeChoice == 5) {
            type = BoatType.JET_SKI;
        }
        
        List<Boat> results = boatManager.search(nameKeyword, minCapacity, maxCapacity, type);
        displaySearchResults(results);
    }

    private void displaySearchResults(List<Boat> boats) {
        if (boats.isEmpty()) {
            System.out.println("No boats found matching your criteria.");
            return;
        }
        
        System.out.println("\n========== SEARCH RESULTS ==========");
        for (Boat boat : boats) {
            System.out.println(boat);
            System.out.println("Price: " + boat.getPrice());
            System.out.println("---");
        }
    }

    public void rentMenu() {
        System.out.println("\n========== RENT BOAT ==========");
        List<Boat> availableBoats = boatManager.getAllBoats();
        
        List<Boat> available = new java.util.ArrayList<>();
        for (Boat boat : availableBoats) {
            if (boat.isAvailable()) {
                available.add(boat);
            }
        }
        
        if (available.isEmpty()) {
            System.out.println("No boats available for rent.");
            return;
        }
        
        System.out.println("Available boats:");
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i).getName() + " - Price: " + available.get(i).getPrice());
        }
        System.out.println("0. Cancel");
        System.out.print("Choose boat (or 0): ");
        
        int choice = In.nextInt();
        
        if (choice > 0 && choice <= available.size()) {
            Boat selectedBoat = available.get(choice - 1);
            member.addRental(selectedBoat);
            System.out.println("You have rented: " + selectedBoat.getName());
        } else if (choice == 0) {
            System.out.println("Rental cancelled.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void returnMenu() {
        System.out.println("\n========== RETURN BOAT ==========");
        List<Boat> currentRentals = member.getCurrentRental();
        
        if (currentRentals.isEmpty()) {
            System.out.println("You have no active rentals.");
            return;
        }
        
        System.out.println("Your active rentals:");
        for (int i = 0; i < currentRentals.size(); i++) {
            System.out.println((i + 1) + ". " + currentRentals.get(i).getName());
        }
        System.out.println("0. Cancel");
        System.out.print("Choose boat to return (or 0): ");
        
        int choice = In.nextInt();
        
        if (choice > 0 && choice <= currentRentals.size()) {
            Boat boatToReturn = currentRentals.get(choice - 1);
            member.returnBoat(boatToReturn);
            System.out.println("You have returned: " + boatToReturn.getName());
        } else if (choice == 0) {
            System.out.println("Return cancelled.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    public void viewRentalHistory() {
        System.out.println("\n========== MY RENTALS ==========");
        
        List<Boat> currentRentals = member.getCurrentRental();
        List<Boat> rentalHistory = member.getRentalHistory();
        
        if (!currentRentals.isEmpty()) {
            System.out.println("Current Active Rentals:");
            for (Boat boat : currentRentals) {
                System.out.println("- " + boat.getName());
            }
        } else {
            System.out.println("No active rentals.");
        }
        
        System.out.println();
        
        if (!rentalHistory.isEmpty()) {
            System.out.println("Rental History:");
            for (Boat boat : rentalHistory) {
                System.out.println("- " + boat.getName());
            }
        } else {
            System.out.println("No rental history.");
        }
    }
}