import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginView {
    private VBox root;
    private LoginController controller;
    private LoginModel model;
    private Button loginButton;
    private Button registerButton;

    public LoginView(LoginController controller, LoginModel model) {
        this.controller = controller;
        this.model = model;
        initView();
    }

    private void initView() {
        // Declarations
        Label title;
        Label usernameLabel;
        TextField usernameField;
        HBox usernameRow;
        Label passwordLabel;
        TextField passwordField;
        HBox passwordRow;
        HBox buttonRow;

        // Initializations
        this.root = new VBox(15);
        title = new Label("BOOOAT Login");
        usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameRow = new HBox(10, usernameLabel, usernameField);

        passwordLabel = new Label("Password:");
        passwordField = new TextField();
        passwordField.setPromptText("Enter your password");
        passwordRow = new HBox(10, passwordLabel, passwordField);

        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");
        buttonRow = new HBox(10, loginButton, registerButton);

        // Layout Configuration
        root.setAlignment(Pos.CENTER);
        usernameRow.setAlignment(Pos.CENTER);
        passwordRow.setAlignment(Pos.CENTER);
        buttonRow.setAlignment(Pos.CENTER);

        // Listeners
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateUsername(newVal);
        });
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updatePassword(newVal);
        });

        root.getChildren().addAll(
            title,
            usernameRow,
            passwordRow,
            buttonRow
        );
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Parent asParent() {
        return root;
    }
}
