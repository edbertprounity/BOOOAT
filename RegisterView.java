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
    private Button registerButton;
    private Button backButton;
    private Label errorLabel;

    public RegisterView(RegisterController controller) {
        this.controller = controller;
        initView();
    }

    private void initView() {
        this.root = new VBox(15);
        
        TextField nameField = new TextField();
        nameField.setPromptText("Enter full name");
        HBox nameRow = new HBox(10, new Label("Full Name:"), nameField);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        HBox usernameRow = new HBox(10, new Label("Username:"), usernameField);

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter password");
        HBox passwordRow = new HBox(10, new Label("Password:"), passwordField);

        this.errorLabel = new Label("");
        this.registerButton = new Button("Create Account");
        this.backButton = new Button("Back to Login");
        HBox buttonRow = new HBox(10, registerButton, backButton);

        // Layout Configuration
        root.setAlignment(Pos.CENTER);
        nameRow.setAlignment(Pos.CENTER);
        usernameRow.setAlignment(Pos.CENTER);
        passwordRow.setAlignment(Pos.CENTER);
        buttonRow.setAlignment(Pos.CENTER);

        // Listeners
        nameField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateFullName(newVal);
        });
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateUsername(newVal);
        });
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updatePassword(newVal);
        });
        registerButton.setOnAction(e -> {
            boolean success = controller.processRegistration();
            if (success) {
                errorLabel.setText("");
            } else {
                errorLabel.setText("Registration failed. Check all fields or username already exists.");
            }
        });

        root.getChildren().addAll(
            new Label("Join BOOOAT"),
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
