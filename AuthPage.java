public class AuthPage implements Page {
    private UserManager userManager;
    private User currentUser;

    public AuthPage(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void display() {
        System.out.println("========== SYSTEM ==========");
        System.out.println("1. Login");
        System.out.println("2. Register (Member)");
        System.out.println("0. Exit");
        System.out.println("============================");
        System.out.print("Choose: ");
    }

    @Override
    public void handleInput(int userInput) {
        if (userInput == 1) {
            login();
        } else if (userInput == 2) {
            register();
        } else if (userInput == 0) {
            System.out.println("Exiting system...");
        }
    }

    public User login() {
        System.out.println("========== LOGIN ==========");
        System.out.println("Please enter your credential:");
        System.out.print("Username: ");
        String username = In.nextLine();
        System.out.print("Password: ");
        String password = In.nextLine();
        
        User user = userManager.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!");
            this.currentUser = user;
            return user;
        } else {
            System.out.println("Invalid username or password!");
            this.currentUser = null;
            return null;
        }
    }

    public void register() {
        System.out.println("========== REGISTER ==========");
        System.out.print("Full Name: ");
        String name = In.nextLine();
        System.out.print("Username: ");
        String username = In.nextLine();
        
        if (userManager.findByUsername(username) != null) {
            System.out.println("Username already exists! Please choose another.");
            return;
        }
        
        System.out.print("Password: ");
        String password = In.nextLine();
        
        Member newMember = new Member(name, username, password);
        userManager.addUser(newMember);
        System.out.println("Registration successful! You are now a Member.");
    }

    public User getCurrentUser() {
        return currentUser;
    }
}