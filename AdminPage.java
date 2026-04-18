import java.util.List;

public class AdminPage implements Page {
    private BoatManager boatManager;
    private RentalManager rentalManager;

    public AdminPage(BoatManager boatManager, RentalManager rentalManager) {
        this.boatManager = boatManager;
        this.rentalManager = rentalManager;
    }

    @Override
    public void display() {
        System.out.println("\n========== ADMIN MENU ==========");
        System.out.println("1. Add Boat");
        System.out.println("2. Remove Boat");
        System.out.println("3. View All Boats");
        System.out.println("4. View All Rentals");
        System.out.println("5. Sales Report");
        System.out.println("0. Logout");
        System.out.println("================================");
        System.out.print("Choose: ");
    }

    @Override
    public void handleInput(int userInput) {
        if (userInput == 1) {
            addBoatMenu();
        } else if (userInput == 2) {
            removeBoatMenu();
        } else if (userInput == 3) {
            viewAllBoats();
        } else if (userInput == 4) {
            viewAllRentals();
        } else if (userInput == 5) {
            viewSalesReport();
        } else if (userInput == 0) {
            System.out.println("Logged out successfully!");
        }
    }

    public void addBoatMenu() {
        System.out.println("\n========== ADD BOAT ==========");
        
        System.out.print("Boat name: ");
        String name = In.nextLine();
        
        System.out.print("Boat price per day: ");
        double price = In.nextDouble();
        
        System.out.print("Boat capacity (number of persons): ");
        int capacity = In.nextInt();
        
        System.out.println("Boat types:");
        System.out.println("1. SPEED_BOAT");
        System.out.println("2. FISHING_BOAT");
        System.out.println("3. KAYAK");
        System.out.println("4. SAIL_BOAT");
        System.out.println("5. JET_SKI");
        System.out.print("Choose boat type: ");
        
        int typeChoice = In.nextInt();
        BoatType type = null;
        
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
        } else {
            System.out.println("Invalid boat type.");
            return;
        }
        
        Boat newBoat = new Boat(name, price, type, capacity);
        boatManager.addBoat(newBoat);
        System.out.println("Boat added successfully!");
    }

    public void removeBoatMenu() {
        System.out.println("\n========== REMOVE BOAT ==========");
        
        List<Boat> boats = boatManager.getAllBoats();
        
        if (boats.isEmpty()) {
            System.out.println("No boats available.");
            return;
        }
        
        System.out.println("Available boats:");
        for (int i = 0; i < boats.size(); i++) {
            System.out.println((i + 1) + ". " + boats.get(i).getName() + " - $" + boats.get(i).getPrice() + "/day - Capacity: " + boats.get(i).getCapacity());
        }
        System.out.println("0. Cancel");
        System.out.print("Choose boat to remove (or 0): ");
        
        int choice = In.nextInt();
        
        if (choice > 0 && choice <= boats.size()) {
            Boat boatToRemove = boats.get(choice - 1);
            
            // Check if boat has active rentals
            if (hasActiveRentals(boatToRemove)) {
                System.out.println("Cannot remove boat! This boat has active rentals.");
                return;
            }
            
            boatManager.removeBoat(boatToRemove);
            System.out.println("Boat removed successfully!");
        } else if (choice == 0) {
            System.out.println("Removal cancelled.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    private boolean hasActiveRentals(Boat boat) {
        List<RentRecord> records = rentalManager.getAllRecords();
        for (RentRecord record : records) {
            if (record.isActive() && record.getBoat() == boat) {
                return true;
            }
        }
        return false;
    }

    public void viewAllBoats() {
        System.out.println("\n========== ALL BOATS ==========");
        List<Boat> boats = boatManager.getAllBoats();
        
        if (boats.isEmpty()) {
            System.out.println("No boats in the system.");
            return;
        }
        
        for (Boat boat : boats) {
            System.out.println(boat);
            System.out.println();
        }
    }

    public void viewAllRentals() {
        System.out.println("\n========== VIEW RENTALS ==========");
        System.out.println("1. Active Rentals");
        System.out.println("2. Completed Rentals");
        System.out.println("3. All Rentals");
        System.out.println("0. Cancel");
        System.out.print("Choose: ");
        
        int choice = In.nextInt();
        
        if (choice == 1) {
            displayActiveRentals();
        } else if (choice == 2) {
            displayCompletedRentals();
        } else if (choice == 3) {
            displayAllRentals();
        } else if (choice == 0) {
            System.out.println("Cancelled.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void displayActiveRentals() {
        System.out.println("\n========== ACTIVE RENTALS ==========");
        List<RentRecord> records = rentalManager.getAllRecords();
        
        int count = 0;
        for (RentRecord record : records) {
            if (record.isActive()) {
                System.out.println(record);
                System.out.println("---");
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("No active rentals.");
        }
    }

    private void displayCompletedRentals() {
        System.out.println("\n========== COMPLETED RENTALS ==========");
        List<RentRecord> records = rentalManager.getAllRecords();
        
        int count = 0;
        for (RentRecord record : records) {
            if (!record.isActive()) {
                System.out.println(record);
                System.out.println("---");
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("No completed rentals.");
        }
    }

    private void displayAllRentals() {
        System.out.println("\n========== ALL RENTALS ==========");
        List<RentRecord> records = rentalManager.getAllRecords();
        
        if (records.isEmpty()) {
            System.out.println("No rental records.");
            return;
        }
        
        for (RentRecord record : records) {
            System.out.println(record);
            System.out.println("---");
        }
    }

    public void viewSalesReport() {
        System.out.println("\n========== SALES REPORT ==========");
        List<RentRecord> records = rentalManager.getAllRecords();
        
        if (records.isEmpty()) {
            System.out.println("No sales data available.");
            return;
        }
        
        double totalRevenue = 0;
        int totalRentals = records.size();
        int activeRentals = 0;
        int completedRentals = 0;
        
        for (RentRecord record : records) {
            totalRevenue += record.getPrice();
            if (record.isActive()) {
                activeRentals++;
            } else {
                completedRentals++;
            }
        }
        
        System.out.println("Total Rentals: " + totalRentals);
        System.out.println("Active Rentals: " + activeRentals);
        System.out.println("Completed Rentals: " + completedRentals);
        System.out.println("Total Revenue: $" + (totalRevenue));
        System.out.println("Average Revenue per Rental: $" + (totalRevenue / totalRentals));
        System.out.println("================================");
    }
}