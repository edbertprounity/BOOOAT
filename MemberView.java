import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MemberView {
    private VBox root;
    private MemberController controller;
    private MemberModel model;
    private Button logoutBtn;
    private Button detailsBtn;

    public MemberView(MemberController controller, MemberModel model) {
        this.controller = controller;
        this.model = model;
        initView();
    }

    private void initView() {
        this.root = new VBox(20);
        Label welcomeLabel = new Label();
        this.detailsBtn = new Button("Member Details");
        this.logoutBtn = new Button("Logout");
        HBox header = new HBox(10, welcomeLabel, detailsBtn, logoutBtn);

        VBox filterVBox = new VBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Enter boat name or keyword");
        HBox searchRow = new HBox(5, new Label("Search Name:"), searchField);

        TextField minCapField = new TextField();
        TextField maxCapField = new TextField();
        minCapField.setPromptText("Min");
        maxCapField.setPromptText("Max");
        HBox capRow = new HBox(5, minCapField, maxCapField);

        TextField minPriceField = new TextField();
        TextField maxPriceField = new TextField();
        minPriceField.setPromptText("Min");
        maxPriceField.setPromptText("Max");
        HBox priceRow = new HBox(5, minPriceField, maxPriceField);

        ToggleGroup typeGroup = new ToggleGroup();
        HBox typeBox = new HBox(5);
        for (BoatType bt : BoatType.values()) {
            RadioButton rb = new RadioButton(bt.name());
            rb.setToggleGroup(typeGroup);
            rb.setUserData(bt);
            typeBox.getChildren().add(rb);
        }
        Button clearBtn = new Button("Clear Filters");

        VBox tableVBox = new VBox(10);
        TableView<Boat> availableBoatsTable = createBoatTable();
        Button rentBtn = new Button("Rent Selected Boat");

        // Configuration and Layout
        root.setAlignment(Pos.TOP_LEFT);
        header.setAlignment(Pos.CENTER_LEFT);
        filterVBox.setAlignment(Pos.TOP_LEFT);
        searchRow.setAlignment(Pos.CENTER_LEFT);
        tableVBox.setAlignment(Pos.TOP_LEFT);

        welcomeLabel.textProperty().bind(model.welcomeTextProperty());
        configIntField(minCapField);
        configIntField(maxCapField);
        configDoubleField(minPriceField);
        configDoubleField(maxPriceField);
        availableBoatsTable.setItems(model.getAvailableBoats());

        // Event Listeners
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateSearch(newVal);
        });
        minCapField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateMinCapacity(newVal);
        });
        maxCapField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateMaxCapacity(newVal);
        });
        minPriceField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateMinPrice(newVal);
        });
        maxPriceField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateMaxPrice(newVal);
        });
        typeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            BoatType type = null;
            if (newVal != null) {
                type = (BoatType) newVal.getUserData();
            }
            controller.updateFilterType(type);
        });
        clearBtn.setOnAction(e -> {
            searchField.setText("");
            minCapField.setText("");
            maxCapField.setText("");
            minPriceField.setText("");
            maxPriceField.setText("");
            typeGroup.selectToggle(null);
            controller.clearFilters();
        });

        filterVBox.getChildren().addAll(
            searchRow,
            new Label("Capacity (Min/Max):"), capRow,
            new Label("Price (Min/Max):"), priceRow,
            new Label("Boat Type:"), typeBox,
            clearBtn
        );

        // MVC STEP 1 — VIEW CAPTURES USER EVENT
        // The View's only job: detect the click and pass the
        // selected Boat object to the Controller. No logic here.

        rentBtn.setOnAction(e -> {
            // VIEW: read selection from TableView (UI state only)
            Boat selected = availableBoatsTable.getSelectionModel().getSelectedItem();

            // VIEW: guard — if nothing selected, do nothing
            if (selected != null) {
                // VIEW: delegate immediately to a View helper method
                // that builds the dialog — still inside the View layer
                showRentDialog(selected);
            }
        });
        tableVBox.getChildren().addAll(new Label("Available Boats:"), availableBoatsTable, rentBtn);
        detailsBtn.setOnAction(e -> showDetailsWindow());
        root.getChildren().addAll(header, filterVBox, tableVBox);
    }


    // MVC STEP 2 — VIEW BUILDS SECONDARY WINDOW
    // Still purely View. Creating a Stage + Scene is UI work.
    // The View builds the layout; it does NOT calculate prices.
    private void showRentDialog(Boat boat) {
        Stage dialog = new Stage();
        dialog.initOwner(root.getScene().getWindow());
        // Modality blocks the main window while dialog is open
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Confirm Booking");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);

        // VIEW: bind boat name label directly to the property —
        // if the name ever changes, the label auto-updates
        Label boatNameLabel = new Label();
        boatNameLabel.textProperty().bind(boat.nameProperty());
        HBox boatRow = new HBox(5, new Label("Boat:"), boatNameLabel);
        boatRow.setAlignment(Pos.CENTER_LEFT);

        Label typeValue = new Label();
        typeValue.textProperty().bind(boat.typeProperty().asString());

        Label capacityValue = new Label();
        capacityValue.textProperty().bind(boat.capacityProperty().asString());

        HBox detailRow = new HBox(5, new Label("Type:"), typeValue, new Label("Capacity:"), capacityValue);
        detailRow.setAlignment(Pos.CENTER_LEFT);

        TextField durationField = new TextField("1");
        HBox durationRow = new HBox(10, new Label("Duration (days):"), durationField);
        durationRow.setAlignment(Pos.CENTER_LEFT);

        TextField codeField = new TextField();
        codeField.setPromptText("Enter discount code");
        configIntField(durationField);
        durationField.setPromptText("Duration in days"); // Added prompt text for durationField

        Button applyCodeBtn = new Button("Apply Code"); // This button is fine as is
        HBox codeRow = new HBox(10, new Label("Discount Code:"), codeField, applyCodeBtn);
        codeRow.setAlignment(Pos.CENTER_LEFT);
   
        // VIEW: bind the running total to the Model property.
        // The Controller will update the Model; the View just displays it.
        Label totalLabel = new Label("Total: $0.00");
        totalLabel.textProperty().bind(model.rentalTotalPriceProperty().asString("Total: $%.2f"));


        // MVC STEP 3 — VIEW DELEGATES INPUT CHANGE TO CONTROLLER
        // The View detects every keystroke in the duration field
        // and passes the raw String to the Controller.
        // The View does NOT parse the String — that is Controller work.
        durationField.textProperty().addListener((obs, oldVal, newVal) -> {
            controller.updateRentalDuration(boat, newVal);
        });

        // MVC STEP 4 — DISCRETE ACTION (button, not a live listener)
        // "Apply Code" is a deliberate user action, not a live keystroke.
        // That is why it uses setOnAction instead of a textProperty listener.

        // VIEW: button wires directly to controller method
        applyCodeBtn.setOnAction(e -> controller.applyDiscountCode(boat, codeField.getText()));

        Button confirmBtn = new Button("Confirm Booking");
        // MVC STEP 5 — THE CORE ACTION
        // Confirm triggers the full rental transaction.

        // VIEW: button fires the controller method, then closes dialog
        confirmBtn.setOnAction(e -> {
            controller.executeRental(boat);
            dialog.close();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> dialog.close());

        HBox btnRow = new HBox(10, confirmBtn, cancelBtn);
        btnRow.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(boatRow, detailRow, durationRow, codeRow, totalLabel, btnRow);

            
        // VIEW: seed the controller with an initial duration of 1
        // so the total shows a price immediately on open
        controller.updateRentalDuration(boat, "1");
        dialog.setScene(new Scene(layout, 350, 250));
        dialog.show();
    }

    private void showDetailsWindow() {
        Stage stage = new Stage();
        stage.initOwner(root.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Member Details");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);

        // --- Info (labels bound to model properties directly) ---
        Label nameLabel = new Label();
        nameLabel.textProperty().bind(model.nameProperty());

        Label usernameLabel = new Label();
        usernameLabel.textProperty().bind(model.usernameProperty());

        Label tierLabel = new Label();
        tierLabel.textProperty().bind(model.tierProperty());

        Label pointsLabel = new Label();
        pointsLabel.textProperty().bind(model.pointsProperty().asString());

        Label discountLabel = new Label();
        discountLabel.textProperty().bind(model.discountProperty());

        HBox nameRow = new HBox(5, new Label("Full Name:"), nameLabel);
        nameRow.setAlignment(Pos.CENTER_LEFT);
        HBox usernameRow = new HBox(5, new Label("Username:"), usernameLabel);
        usernameRow.setAlignment(Pos.CENTER_LEFT);
        HBox tierRow = new HBox(5, new Label("Tier:"), tierLabel);
        tierRow.setAlignment(Pos.CENTER_LEFT);
        HBox pointsRow = new HBox(5, new Label("Points:"), pointsLabel);
        pointsRow.setAlignment(Pos.CENTER_LEFT);
        HBox discountRow = new HBox(5, new Label("Discount:"), discountLabel);
        discountRow.setAlignment(Pos.CENTER_LEFT);

        // --- Update name ---
        TextField nameField = new TextField(model.nameProperty().get());
        nameField.setPromptText("Enter new name");
        Button saveNameBtn = new Button("Save Name");
        saveNameBtn.setOnAction(e -> controller.updateProfileName(nameField.getText()));

        HBox updateNameRow = new HBox(10, new Label("New Name:"), nameField, saveNameBtn);
        updateNameRow.setAlignment(Pos.CENTER_LEFT);

        // --- Update password ---
        TextField newPassField = new TextField();
        newPassField.setPromptText("New password");
        TextField confirmPassField = new TextField();
        confirmPassField.setPromptText("Confirm password");
        Label passErrorLabel = new Label("");
        passErrorLabel.textProperty().bind(model.passwordErrorProperty());

        Button savePassBtn = new Button("Save Password");
        savePassBtn.setOnAction(e -> {
            controller.updatePassword(newPassField.getText(), confirmPassField.getText());
            if (model.passwordErrorProperty().get().isEmpty()) {
                newPassField.setText(""); 
                confirmPassField.setText("");
            }
        });

        HBox passRow = new HBox(10, new Label("New Password:"), newPassField, new Label("Confirm:"), confirmPassField, savePassBtn);
        passRow.setAlignment(Pos.CENTER_LEFT);

        // --- Active rentals ---
        TableView<Boat> activeTable = createBoatTable();
        activeTable.setItems(model.getCurrentRentals());

        Button returnBtn = new Button("Return Selected Boat");
        returnBtn.setOnAction(e -> controller.returnBoat(activeTable.getSelectionModel().getSelectedItem()));

        // --- History ---
        TableView<Boat> historyTable = createBoatTable();
        historyTable.setItems(model.getRentalHistory());

        layout.getChildren().addAll(
            new Label("Member Information"),
            nameRow, usernameRow, tierRow, pointsRow, discountRow,
            new Label("Update Profile"),
            updateNameRow,
            new Label("Change Password"),
            passRow, passErrorLabel, savePassBtn,
            new Label("Current Active Rentals:"), activeTable, returnBtn,
            new Label("Rental History:"), historyTable
        );
        
        stage.setScene(new Scene(layout, 550, 650));
        stage.show();
    }

    private TableView<Boat> createBoatTable() {
        TableView<Boat> table = new TableView<>();

        TableColumn<Boat, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());

        TableColumn<Boat, BoatType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(c -> c.getValue().typeProperty());

        TableColumn<Boat, Number> priceCol = new TableColumn<>("Price/Day");
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty());

        TableColumn<Boat, Number> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(c -> c.getValue().capacityProperty());

        table.getColumns().addAll(nameCol, typeCol, priceCol, capCol);
        return table;
    }

    public Button getLogoutButton() {
        return logoutBtn;
    }

    public Parent asParent() {
        return root;
    }

    private void configIntField(TextField field) {
        field.setTextFormatter(new TextFormatter<>((TextFormatter.Change c) -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            }
            return null;
        }));
    }

    private void configDoubleField(TextField field) {
        field.setTextFormatter(new TextFormatter<>((TextFormatter.Change c) -> {
            if (c.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return c;
            }
            return null;
        }));
    }
}
