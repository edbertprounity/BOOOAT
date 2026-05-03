import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;;

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

        Scene scene = new Scene(loginView.asParent(), 800, 600);

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
