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
        // Declarations
        Label title;
        Label nameLabel;
        TextField nameField;
        HBox nameRow;
        Label usernameLabel;
        TextField usernameField;
        HBox usernameRow;
        Label passwordLabel;
        TextField passwordField;
        HBox passwordRow;
        HBox buttonRow;

        // Initializations
        this.root = new VBox(15);
        title = new Label("Join BOOOAT");
        
        nameLabel = new Label("Full Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter full name");
        nameRow = new HBox(10, nameLabel, nameField);

        usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameRow = new HBox(10, usernameLabel, usernameField);

        passwordLabel = new Label("Password:");
        passwordField = new TextField();
        passwordField.setPromptText("Enter password");
        passwordRow = new HBox(10, passwordLabel, passwordField);

        this.errorLabel = new Label("");
        this.registerButton = new Button("Create Account");
        this.backButton = new Button("Back to Login");
        buttonRow = new HBox(10, registerButton, backButton);

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
