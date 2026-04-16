public class Main {
    
    public static void main(String[] args) {
        
        UserManager userManager = new UserManager();
        RentalManager rentalManager = new RentalManager();
        BoatManager boatManager = new BoatManager();

        AuthPage authPage = new AuthPage(userManager);
        Page currentPage = authPage;
        User currentUser = null;
        int userInput;

    

        Admin myAdmin = new Admin("admin#1", "admin", "123456");
        userManager.addUser(myAdmin);

        // Add sample boats
        Boat boat1 = new Boat("Speedster", 150.0, BoatType.SPEED_BOAT);
        Boat boat2 = new Boat("Fisher Pro", 100.0, BoatType.FISHING_BOAT);
        Boat boat3 = new Boat("Wave Rider", 80.0, BoatType.KAYAK);
        Boat boat4 = new Boat("Sailor's Dream", 200.0, BoatType.SAIL_BOAT);
        Boat boat5 = new Boat("Jet Express", 250.0, BoatType.JET_SKI);
        
        boatManager.addBoat(boat1);
        boatManager.addBoat(boat2);
        boatManager.addBoat(boat3);
        boatManager.addBoat(boat4);
        boatManager.addBoat(boat5);

        // Add sample members
        Member member1 = new Member("John Doe", "john", "password123");
        Member member2 = new Member("Jane Smith", "jane", "jane2024");
        
        userManager.addUser(member1);
        userManager.addUser(member2);

        while (true){
            currentPage.display();
            userInput = In.nextInt();
            
            currentPage.handleInput(userInput);
            
            // Check if user logged in
            if (currentPage instanceof AuthPage && userInput == 1) {
                currentUser = authPage.getCurrentUser();
                if (currentUser instanceof Member) {
                    currentPage = new MemberPage(boatManager, rentalManager, (Member) currentUser);
                } else if (currentUser instanceof Admin) {
                    currentPage = new AdminPage();
                }
            }
            
            // Check if user logged out
            if ((currentPage instanceof MemberPage || currentPage instanceof AdminPage) && userInput == 0) {
                currentPage = authPage;
                currentUser = null;
            }
        }
        
    }
}