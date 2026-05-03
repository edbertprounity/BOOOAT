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
        usernameField.setPromptText("Username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        usernameField.textProperty().addListener((obs, oldVal, newVal) -> controller.updateUsername(newVal));
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> controller.updatePassword(newVal));

        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");

        HBox buttonRow = new HBox(10, loginButton, registerButton);
        buttonRow.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
            title,
            new Label("Username:"), usernameField,
            new Label("Password:"), passwordField,
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
