import java.util.*;

public class Main {
    static UserManager userManager = new UserManager();
    static RentalManager rentalManager = new RentalManager();
    static BoatManager boatManager = new BoatManager();
    
    public static void main(String[] args) {
        AuthPage authPage = new AuthPage(userManager);
        Page currentPage = authPage;
        User currentUser = null;
        int userInput;

        Admin myAdmin = new Admin("admin#1", "admin", "123456");
        userManager.addUser(myAdmin);

        while (true){
            currentPage.display();
            userInput = In.nextInt();
            
            currentPage.handleInput(userInput);
            
            // Check if user logged in
            if (currentPage instanceof AuthPage && userInput == 1) {
                currentUser = authPage.getCurrentUser();
                if (currentUser instanceof Member) {
                    currentPage = new MemberPage();
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