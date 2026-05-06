import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegisterView {
    private VBox root;
    private RegisterController controller;
    private RegisterModel model;
    private Button registerButton;
    private Button backButton;
    private Label errorLabel;

    public RegisterView(RegisterController controller, RegisterModel model) {
        this.controller = controller;
        this.model = model;
        initView();
    }

    private void initView() {
        this.root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Join BOOOAT");

        TextField nameField = new TextField();
        TextField usernameField = new TextField();
        TextField passwordField = new TextField();

        HBox nameRow = new HBox(10, new Label("Full Name:"), nameField);
        nameRow.setAlignment(Pos.CENTER);

        HBox usernameRow = new HBox(10, new Label("Username:"), usernameField);
        usernameRow.setAlignment(Pos.CENTER);

        HBox passwordRow = new HBox(10, new Label("Password:"), passwordField);
        passwordRow.setAlignment(Pos.CENTER);

        nameField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateFullName(newVal);
        });
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateUsername(newVal);
        });
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updatePassword(newVal);
        });

        this.errorLabel = new Label("");

        this.registerButton = new Button("Create Account");
        this.backButton = new Button("Back to Login");

        registerButton.setOnAction(e -> {
            boolean success = controller.processRegistration();
            if (success) {
                errorLabel.setText("");
            } else {
                errorLabel.setText("Registration failed. Check all fields or username already exists.");
            }
        });

        HBox buttonRow = new HBox(10, registerButton, backButton);
        buttonRow.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
            title,
            nameRow,
            usernameRow,
            passwordRow,
            errorLabel,
            buttonRow
        );
    }

    public Button getBackToLoginButton() {
        return backButton;
    }

    public Parent asParent() {
        return root;
    }
}
