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
        this.root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("BOOOAT Login");

        TextField usernameField = new TextField();

        TextField passwordField = new TextField();

        HBox usernameRow = new HBox(10, new Label("Username:"), usernameField);
        usernameRow.setAlignment(Pos.CENTER);

        HBox passwordRow = new HBox(10, new Label("Password:"), passwordField);
        passwordRow.setAlignment(Pos.CENTER);

        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateUsername(newVal);
        });
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updatePassword(newVal);
        });

        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");

        HBox buttonRow = new HBox(10, loginButton, registerButton);
        buttonRow.setAlignment(Pos.CENTER);

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
