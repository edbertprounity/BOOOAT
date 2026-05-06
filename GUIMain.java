import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
public class GUIMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize Model
        UserManager userManager = new UserManager();
        userManager.addUser(new Admin("System Admin", "admin", "123456"));
        userManager.addUser(new Member("John Doe", "john", "password123"));

        RentalManager rentalManager = new RentalManager();
        BoatManager boatManager = new BoatManager();
        boatManager.addBoat(new Boat("Speedster", 150.0, BoatType.SPEED_BOAT, 4));
        Boat boat1 = new Boat("Speedster", 150.0, BoatType.SPEED_BOAT, 4);
        Boat boat2 = new Boat("Fisher Pro", 100.0, BoatType.FISHING_BOAT, 6);
        Boat boat3 = new Boat("Wave Rider", 80.0, BoatType.KAYAK, 2);
        Boat boat4 = new Boat("Sailor's Dream", 200.0, BoatType.SAIL_BOAT, 8);
        Boat boat5 = new Boat("Jet Express 2000", 250.0, BoatType.JET_SKI, 3);
        Boat boat6 = new Boat("Odyssey 555", 300.0, BoatType.SAIL_BOAT, 10);
        Boat boat7 = new Boat("Vagabond", 150.0, BoatType.FISHING_BOAT, 8);

        boatManager.addBoat(boat1);
        boatManager.addBoat(boat2);
        boatManager.addBoat(boat3);
        boatManager.addBoat(boat4);
        boatManager.addBoat(boat5);
        boatManager.addBoat(boat6);
        boatManager.addBoat(boat7);

        // Initialize Model (Data layer for UI)
        LoginModel loginModel = new LoginModel();
        RegisterModel registerModel = new RegisterModel();

        // Initialize Controllers
        LoginController loginController = new LoginController(userManager, loginModel);
        RegisterController registerController = new RegisterController(userManager, registerModel);

        // Initialize Views
        LoginView loginView = new LoginView(loginController, loginModel);
        RegisterView registerView = new RegisterView(registerController, registerModel);

        Scene scene = new Scene(loginView.asParent(), 800, 1600);

        // Basic navigation logic using button getters
        loginView.getRegisterButton().setOnAction(e -> scene.setRoot(registerView.asParent()));
        registerView.getBackToLoginButton().setOnAction(e -> scene.setRoot(loginView.asParent()));

        // Login navigation logic
        loginView.getLoginButton().setOnAction(e -> {
            User user = loginController.processLogin();
            if (user instanceof Member) {
                MemberModel memModel = new MemberModel();
                MemberController memCtrl = new MemberController(boatManager, rentalManager, (Member)user, memModel);
                MemberView memView = new MemberView(memCtrl, memModel);
                
                memView.getLogoutButton().setOnAction(logoutEvt -> scene.setRoot(loginView.asParent()));
                scene.setRoot(memView.asParent());
            } else if (user instanceof Admin) {
                AdminModel adminModel = new AdminModel();
                AdminController adminCtrl = new AdminController(boatManager, rentalManager, adminModel);
                AdminView adminView = new AdminView(adminCtrl, adminModel);

                adminView.getLogoutButton().setOnAction(logoutEvt -> scene.setRoot(loginView.asParent()));
                scene.setRoot(adminView.asParent());
            } 
        });

        primaryStage.setTitle("BOOOAT - Rental System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/

public class GUIMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        // --- Data layer ---
        UserManager userManager = new UserManager();
        userManager.addUser(new Admin("System Admin", "admin", "123456"));
        userManager.addUser(new Member("John Doe", "john", "password123"));

        RentalManager rentalManager = new RentalManager();
        BoatManager boatManager = new BoatManager();

        Boat boat1 = new Boat("Speedster", 150.0, BoatType.SPEED_BOAT, 4);
        Boat boat2 = new Boat("Fisher Pro", 100.0, BoatType.FISHING_BOAT, 6);
        Boat boat3 = new Boat("Wave Rider", 80.0, BoatType.KAYAK, 2);
        Boat boat4 = new Boat("Sailor's Dream", 200.0, BoatType.SAIL_BOAT, 8);
        Boat boat5 = new Boat("Jet Express 2000", 250.0, BoatType.JET_SKI, 3);
        Boat boat6 = new Boat("Odyssey 555", 300.0, BoatType.SAIL_BOAT, 10);
        Boat boat7 = new Boat("Vagabond", 150.0, BoatType.FISHING_BOAT, 8);

        boatManager.addBoat(boat1);
        boatManager.addBoat(boat2);
        boatManager.addBoat(boat3);
        boatManager.addBoat(boat4);
        boatManager.addBoat(boat5);
        boatManager.addBoat(boat6);
        boatManager.addBoat(boat7);

        openLoginStage(userManager, boatManager, rentalManager);
    }

    private void openLoginStage(UserManager userManager, BoatManager boatManager, RentalManager rentalManager) {
        LoginModel loginModel = new LoginModel();
        loginModel.setUserManager(userManager);
        LoginController loginController = new LoginController(loginModel);
        LoginView loginView = new LoginView(loginController, loginModel);

        Stage loginStage = new Stage();
        loginStage.setTitle("BOOOAT - Login");
        loginStage.setScene(new Scene(loginView.asParent(), 350, 280));
        loginStage.show();

        // Register navigation
        loginView.getRegisterButton().setOnAction(e -> {
            openRegisterStage(userManager, boatManager, rentalManager, loginStage);
        });

        // Login navigation
        loginView.getLoginButton().setOnAction(e -> {
            User user = loginController.processLogin();
            if (user instanceof Member) {
                openMemberStage(userManager, boatManager, rentalManager, (Member) user, loginStage);
            } 
            if (user instanceof Admin) {
                openAdminStage(userManager, boatManager, rentalManager, loginStage);
            }
        });
    }

    private void openRegisterStage(UserManager userManager, BoatManager boatManager, RentalManager rentalManager, Stage previous) {
        RegisterModel registerModel = new RegisterModel();
        registerModel.setUserManager(userManager);
        RegisterController registerController = new RegisterController(registerModel);
        RegisterView registerView = new RegisterView(registerController, registerModel);

        Stage registerStage = new Stage();
        registerStage.setTitle("BOOOAT - Register");
        registerStage.setScene(new Scene(registerView.asParent(), 350, 320));
        registerStage.show();
        previous.close();

        registerView.getBackToLoginButton().setOnAction(e -> {
            openLoginStage(userManager, boatManager, rentalManager);
            registerStage.close();
        });
    }

    private void openMemberStage(UserManager userManager, BoatManager boatManager, RentalManager rentalManager, Member member, Stage previous) {
        MemberModel memModel = new MemberModel();
        memModel.setBoatManager(boatManager);
        memModel.setRentalManager(rentalManager);
        memModel.setMember(member);
        MemberController memCtrl = new MemberController(memModel);
        MemberView memView = new MemberView(memCtrl, memModel);

        Stage memberStage = new Stage();
        memberStage.setTitle("BOOOAT - Member: " + member.getUsername());
        memberStage.setScene(new Scene(memView.asParent(), 700, 600));
        memberStage.show();
        previous.close();

        memView.getLogoutButton().setOnAction(e -> {
            openLoginStage(userManager, boatManager, rentalManager);
            memberStage.close();
        });
    }

    private void openAdminStage(UserManager userManager, BoatManager boatManager, RentalManager rentalManager, Stage previous) {
        AdminModel adminModel = new AdminModel();
        adminModel.setBoatManager(boatManager);
        adminModel.setRentalManager(rentalManager);
        AdminController adminCtrl = new AdminController(adminModel);
        AdminView adminView = new AdminView(adminCtrl, adminModel);

        Stage adminStage = new Stage();
        adminStage.setTitle("BOOOAT - Admin Panel");
        adminStage.setScene(new Scene(adminView.asParent(), 900, 700));
        adminStage.show();
        previous.close();

        adminView.getLogoutButton().setOnAction(e -> {
            openLoginStage(userManager, boatManager, rentalManager);
            adminStage.close();
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}