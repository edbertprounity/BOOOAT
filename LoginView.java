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
    private Button loginButton;
    private Button registerButton;

    public LoginView(LoginController controller) {
        this.controller = controller;
        initView();
    }

    private void initView() {
        this.root = new VBox(15);
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        HBox usernameRow = new HBox(10, new Label("Username:"), usernameField);

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter your password");
        HBox passwordRow = new HBox(10, new Label("Password:"), passwordField);

        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");
        HBox buttonRow = new HBox(10, loginButton, registerButton);

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
            new Label("BOOOAT Login"),
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
