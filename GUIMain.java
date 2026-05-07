import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        UserManager userManager = new UserManager();
        BoatManager boatManager = new BoatManager();
        RentalManager rentalManager = new RentalManager();

        // Seed Data
        userManager.addUser(new Admin("System Admin", "admin", "123456"));
        userManager.addUser(new Member("John Doe", "john", "password123"));
        boatManager.addBoat(new Boat("Speedster", 150.0, BoatType.SPEED_BOAT, 4));
        boatManager.addBoat(new Boat("Fisher Pro", 100.0, BoatType.FISHING_BOAT, 6));
        boatManager.addBoat(new Boat("Wave Rider", 80.0, BoatType.KAYAK, 2));
        boatManager.addBoat(new Boat("Sailor's Dream", 200.0, BoatType.SAIL_BOAT, 8));
        boatManager.addBoat(new Boat("Jet Express 2000", 250.0, BoatType.JET_SKI, 3));
        boatManager.addBoat(new Boat("Odyssey 555", 300.0, BoatType.SAIL_BOAT, 10));
        boatManager.addBoat(new Boat("Vagabond", 150.0, BoatType.FISHING_BOAT, 8));

        // --- 2. Shared Models/Views/Controllers (Login & Register) ---
        LoginModel loginModel = new LoginModel();
        loginModel.setUserManager(userManager);
        LoginController loginController = new LoginController(loginModel);
        LoginView loginView = new LoginView(loginController, loginModel);

        RegisterModel registerModel = new RegisterModel();
        registerModel.setUserManager(userManager);
        RegisterController registerController = new RegisterController(registerModel);
        RegisterView registerView = new RegisterView(registerController, registerModel);

        // --- 3. Scene Initialization ---
        Scene mainScene = new Scene(loginView.asParent(), 800, 700);

        // --- 4. Navigation Logic ---
        loginView.getRegisterButton().setOnAction(e -> mainScene.setRoot(registerView.asParent()));
        registerView.getBackToLoginButton().setOnAction(e -> mainScene.setRoot(loginView.asParent()));

        loginView.getLoginButton().setOnAction(e -> {
            User user = loginController.processLogin();
            if (user instanceof Member) {
                MemberModel memModel = new MemberModel();
                memModel.setBoatManager(boatManager);
                memModel.setRentalManager(rentalManager);
                memModel.setMember((Member) user);
                
                MemberController memCtrl = new MemberController(memModel);
                MemberView memView = new MemberView(memCtrl, memModel);
                
                memView.getLogoutButton().setOnAction(logoutEvt -> mainScene.setRoot(loginView.asParent()));
                mainScene.setRoot(memView.asParent());
            } else if (user instanceof Admin) {
                AdminModel adminModel = new AdminModel();
                adminModel.setBoatManager(boatManager);
                adminModel.setRentalManager(rentalManager);
                
                AdminController adminCtrl = new AdminController(adminModel);
                AdminView adminView = new AdminView(adminCtrl, adminModel);
                
                adminView.getLogoutButton().setOnAction(logoutEvt -> mainScene.setRoot(loginView.asParent()));
                mainScene.setRoot(adminView.asParent());
            }
        });

        primaryStage.setTitle("BOOOAT - Rental System");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
