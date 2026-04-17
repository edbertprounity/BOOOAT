import java.util.List;

public class MemberPage implements Page {
    private BoatManager boatManager;
    private RentalManager rentalManager;
    private Member member;

    public MemberPage(BoatManager boatManager, RentalManager rentalManager, Member member) {
        this.boatManager = boatManager;
        this.rentalManager = rentalManager;
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
        System.out.println("6. Update Profile");
        System.out.println("7. View Profile");
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
        } else if (userInput == 6) {
            updateProfile();
        } else if (userInput == 7) {
            viewProfile();
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
                System.out.println();
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
        Double minPrice = null;
        Double maxPrice = null;

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

        System.out.print("Minimum price (or 0): ");
        double minPr = In.nextDouble();
        if (minPr != 0) {
            minPrice = minPr;
        }

        System.out.print("Maximum price (or 0): ");
        double maxPr = In.nextDouble();
        if (maxPr != 0) {
            maxPrice = maxPr;
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

        List<Boat> results = boatManager.search(nameKeyword, minCapacity, maxCapacity, type, minPrice, maxPrice);
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
            System.out.println();
        }
    }

    public void rentMenu() {
        System.out.println("\n========== RENT BOAT ==========");
        List<Boat> availableBoats = boatManager.findAvailableBoat();

        if (availableBoats.isEmpty()) {
            System.out.println("No boats available for rent.");
            return;
        }

        System.out.println("Available boats:");
        for (int i = 0; i < availableBoats.size(); i++) {
            System.out.println((i + 1) + ". " + availableBoats.get(i).getName() + " - $"
                    + availableBoats.get(i).getPrice() + "/day - Capacity: " + availableBoats.get(i).getCapacity());
        }
        System.out.println("0. Cancel");
        System.out.print("Choose boat (or 0): ");

        int choice = In.nextInt();
        In.nextLine(); // Consume newline

        if (choice > 0 && choice <= availableBoats.size()) {
            Boat selectedBoat = availableBoats.get(choice - 1);

            System.out.print("Enter rental duration (days): ");
            int duration = In.nextInt();
            In.nextLine(); // Consume newline

            if (duration <= 0) {
                System.out.println("Invalid duration.");
                return;
            }

            System.out.println("\nAvailable discount codes:");
            System.out.println("- SAVE10 (10% off)");
            System.out.println("- SAVE20 (20% off)");
            System.out.println("- SUMMER (15% off)");
            System.out.println("- WINTER (5% off)");

            System.out.print("Do you have a discount code? (yes/no): ");
            String hasCode = In.nextLine();

            String discountCode = null;
            double finalPrice = 0;

            if (hasCode.equals("yes")) {
                System.out.print("Enter discount code: ");
                discountCode = In.nextLine();
                finalPrice = rentalManager.calculatePrice(selectedBoat, duration, member, discountCode);
            } else {
                finalPrice = rentalManager.calculatePrice(selectedBoat, duration, member);
            }

            System.out.println("\n========== RENTAL CONFIRMATION ==========");
            System.out.println("Boat: " + selectedBoat.getName());
            System.out.println("Duration: " + duration + " day(s)");
            System.out.println("Base Price: $" + (selectedBoat.getPrice() * duration));
            System.out.println("Membership Discount: " + (member.discount() * 100) + "%");
            if (discountCode != null && !discountCode.isEmpty()) {
                System.out.println("Discount Code: " + discountCode);
            }
            System.out.println("Final Price: $" + finalPrice);
            System.out.println("======================================");
            System.out.print("Proceed with rental? (y/n): ");

            String confirm = In.nextLine();

            if (confirm.equals("y")) {
                if (discountCode != null && !discountCode.isEmpty()) {
                    rentalManager.rentBoat(member, selectedBoat, duration, discountCode);
                    this.member.confirmMembership();
                } else {
                    rentalManager.rentBoat(member, selectedBoat, duration);
                    this.member.confirmMembership();
                }
            } else {
                System.out.println("Rental cancelled.");
            }
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
            rentalManager.returnBoat(member, boatToReturn);
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

    public void updateProfile() {
        System.out.println("\n========== UPDATE PROFILE ==========");
        System.out.println("What would you like to update?");
        System.out.println("1. Full Name");
        System.out.println("2. Password");
        System.out.println("3. Both");
        System.out.println("0. Cancel");
        System.out.print("Choose: ");

        int choice = In.nextInt();
        In.nextLine(); // Consume newline

        if (choice == 1) {
            updateName();
        } else if (choice == 2) {
            updatePassword();
        } else if (choice == 3) {
            updateName();
            updatePassword();
        } else if (choice == 0) {
            System.out.println("Profile update cancelled.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void updateName() {
        System.out.print("Enter new full name: ");
        String newName = In.nextLine();
        member.setName(newName);
        System.out.println("Name updated successfully.");
    }

    private void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = In.nextLine();
        member.updatePassword(newPassword);
        System.out.println("Password updated successfully.");
    }

    public void viewProfile() {
        System.out.println("\n======== PROFILE DETAILS ========");
        System.out.println("Name: " + this.member.getName());
        System.out.println("Username: " + this.member.getUsername());
        System.out.println("Membership: " + this.member.getMembership());
        System.out.println("Membership Point: " + this.member.getPoint());
    }

}