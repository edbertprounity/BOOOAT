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
        nameField.setPromptText("Full Name");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        nameField.textProperty().addListener((obs, oldVal, newVal) -> controller.updateFullName(newVal));
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> controller.updateUsername(newVal));
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> controller.updatePassword(newVal));

        this.errorLabel = new Label("");

        this.registerButton = new Button("Create Account");
        this.backButton = new Button("Back to Login");

        registerButton.setOnAction(e -> {
            boolean success = controller.processRegistration();
            errorLabel.setText(success ? "" : "Registration failed. Check all fields or username already exists.");
        });

        HBox buttonRow = new HBox(10, registerButton, backButton);
        buttonRow.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
            title,
            new Label("Full Name:"), nameField,
            new Label("Username:"), usernameField,
            new Label("Password:"), passwordField,
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
