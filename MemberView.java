import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
        root.setAlignment(Pos.TOP_CENTER);

        // --- 1. Member details header (HBox) ---
        Label welcomeLabel = new Label();
        welcomeLabel.textProperty().bind(model.welcomeTextProperty());

        this.detailsBtn = new Button("Member Details");
        this.logoutBtn = new Button("Logout");
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, welcomeLabel, spacer, detailsBtn, logoutBtn);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- 2. Main content area (HBox) ---
        HBox contentHBox = new HBox(20);
        VBox.setVgrow(contentHBox, Priority.ALWAYS);

        // Left VBox: Search and Filters
        VBox filterVBox = new VBox(10);
        filterVBox.setAlignment(Pos.TOP_LEFT);

        TextField searchField = new TextField();
        searchField.textProperty().addListener((obs, oldVal, newVal) -> controller.updateSearch(newVal));

        TextField minCapField = new TextField();
        TextField maxCapField = new TextField();
        minCapField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) { minCapField.setText(oldVal); return; }
            controller.updateMinCapacity(newVal.isEmpty() ? null : Integer.parseInt(newVal));
        });
        maxCapField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) { maxCapField.setText(oldVal); return; }
            controller.updateMaxCapacity(newVal.isEmpty() ? null : Integer.parseInt(newVal));
        });

        TextField minPriceField = new TextField();
        TextField maxPriceField = new TextField();
        minPriceField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) { minPriceField.setText(oldVal); return; }
            controller.updateMinPrice(newVal.isEmpty() ? null : Double.parseDouble(newVal));
        });
        maxPriceField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) { maxPriceField.setText(oldVal); return; }
            controller.updateMaxPrice(newVal.isEmpty() ? null : Double.parseDouble(newVal));
        });

        ToggleGroup typeGroup = new ToggleGroup();
        VBox typeBox = new VBox(5);
        for (BoatType bt : BoatType.values()) {
            RadioButton rb = new RadioButton(bt.name());
            rb.setToggleGroup(typeGroup);
            rb.setUserData(bt);
            typeBox.getChildren().add(rb);
        }
        typeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) ->
            controller.updateFilterType(newVal == null ? null : (BoatType) newVal.getUserData())
        );

        Button clearBtn = new Button("Clear Filters");
        clearBtn.setOnAction(e -> {
            searchField.clear(); minCapField.clear(); maxCapField.clear();
            minPriceField.clear(); maxPriceField.clear(); typeGroup.selectToggle(null);
            controller.clearFilters();
        });

        filterVBox.getChildren().addAll(
            new Label("Search Name:"), searchField,
            new Label("Capacity (Min/Max):"), new HBox(5, minCapField, maxCapField),
            new Label("Price (Min/Max):"), new HBox(5, minPriceField, maxPriceField),
            new Label("Boat Type:"), typeBox,
            clearBtn
        );

        // Right VBox: Table area
        VBox tableVBox = new VBox(10);
        tableVBox.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(tableVBox, Priority.ALWAYS);

        TableView<Boat> availTable = createBoatTable();
        availTable.setItems(model.getAvailableBoats());
        VBox.setVgrow(availTable, Priority.ALWAYS);

        Button rentBtn = new Button("Rent Selected Boat");
        rentBtn.setOnAction(e -> {
            Boat selected = availTable.getSelectionModel().getSelectedItem();
            if (selected != null) showRentDialog(selected);
        });

        tableVBox.getChildren().addAll(new Label("Available Boats:"), availTable, rentBtn);

        contentHBox.getChildren().addAll(filterVBox, tableVBox);
        detailsBtn.setOnAction(e -> showDetailsWindow());

        root.getChildren().addAll(header, contentHBox);
    }

    private void showRentDialog(Boat boat) {
        Stage dialog = new Stage();
        dialog.initOwner(root.getScene().getWindow());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Confirm Booking");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);

        HBox boatRow = new HBox(5, new Label("Boat:"), new Label(boat.getName()));
        boatRow.setAlignment(Pos.CENTER_LEFT);

        HBox detailRow = new HBox(5, new Label("Type:"), new Label(boat.getType().toString()), new Label("Capacity:"), new Label(String.valueOf(boat.getCapacity())));
        detailRow.setAlignment(Pos.CENTER_LEFT);

        TextField durationField = new TextField("1");
        HBox durationRow = new HBox(10, new Label("Duration (days):"), durationField);
        durationRow.setAlignment(Pos.CENTER_LEFT);

        TextField codeField = new TextField();
        Button applyCodeBtn = new Button("Apply Code");
        HBox codeRow = new HBox(10, new Label("Discount Code:"), codeField, applyCodeBtn);
        codeRow.setAlignment(Pos.CENTER_LEFT);

        SimpleStringProperty appliedCode = new SimpleStringProperty("");
        Label totalLabel = new Label("Total: $0.00");

        Runnable updatePrice = () -> {
            int days = durationField.getText().isEmpty() ? 0 : Integer.parseInt(durationField.getText());
            double total = controller.getCalculatedPrice(boat, days, appliedCode.get());
            totalLabel.setText(String.format("Total: $%.2f", total));
        };

        durationField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[1-9][0-9]*") && !newVal.isEmpty()) { durationField.setText(oldVal); return; }
            updatePrice.run();
        });

        applyCodeBtn.setOnAction(e -> {
            appliedCode.set(codeField.getText());
            updatePrice.run();
        });

        Button confirmBtn = new Button("Confirm Booking");
        confirmBtn.setOnAction(e -> {
            int days = durationField.getText().isEmpty() ? 0 : Integer.parseInt(durationField.getText());
            controller.executeRental(boat, days, appliedCode.get());
            dialog.close();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> dialog.close());

        HBox btnRow = new HBox(10, confirmBtn, cancelBtn);
        btnRow.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(boatRow, detailRow, durationRow, codeRow, totalLabel, btnRow);

        updatePrice.run();
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
        pointsLabel.textProperty().bind(model.pointsProperty());

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

        Button savePassBtn = new Button("Save Password");
        savePassBtn.setOnAction(e -> {
            String p1 = newPassField.getText();
            String p2 = confirmPassField.getText();
            if (!p1.isEmpty() && p1.equals(p2)) {
                controller.updatePassword(p1);
                newPassField.clear();
                confirmPassField.clear();
                passErrorLabel.setText("");
            } else {
                passErrorLabel.setText("Passwords do not match or are empty.");
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

        TableColumn<Boat, Double> priceCol = new TableColumn<>("Price/Day");
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty().asObject());

        TableColumn<Boat, Integer> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(c -> c.getValue().capacityProperty().asObject());

        table.getColumns().addAll(nameCol, typeCol, priceCol, capCol);
        return table;
    }

    public Button getLogoutButton() {
        return logoutBtn;
    }

    public Parent asParent() {
        return root;
    }
}
