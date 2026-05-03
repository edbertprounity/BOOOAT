import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminView {
    private VBox root;
    private AdminController controller;
    private AdminModel model;
    private Button logoutBtn;
    private TableView<Boat> boatTable;
    private TableView<RentRecord> rentalTable;

    public AdminView(AdminController controller, AdminModel model) {
        this.controller = controller;
        this.model = model;
        initView();
    }

    private void initView() {
        this.root = new VBox(10);
        root.setAlignment(Pos.CENTER_LEFT);

        // --- Header ---
        this.logoutBtn = new Button("Logout");
        HBox header = new HBox(10, new Label("Admin Control Panel"), logoutBtn);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- Add Boat ---
        Label addTitle = new Label("Add New Boat");

        TextField nameField = new TextField();
        nameField.setPromptText("Boat name");

        TextField priceField = new TextField();
        priceField.setPromptText("Price per day");

        TextField capField = new TextField();
        capField.setPromptText("Capacity");

        HBox addFieldRow = new HBox(10, new Label("Name:"), nameField, new Label("Price:"), priceField, new Label("Capacity:"), capField);
        addFieldRow.setAlignment(Pos.CENTER_LEFT);

        ToggleGroup typeGroup = new ToggleGroup();
        HBox typeBox = new HBox(10);
        typeBox.setAlignment(Pos.CENTER_LEFT);
        for (BoatType bt : BoatType.values()) {
            RadioButton rb = new RadioButton(bt.name());
            rb.setToggleGroup(typeGroup);
            rb.setUserData(bt);
            typeBox.getChildren().add(rb);
        }

        HBox typeRow = new HBox(10, new Label("Type:"), typeBox);
        typeRow.setAlignment(Pos.CENTER_LEFT);

        Label addErrorLabel = new Label("");

        Button addBtn = new Button("Add Boat");
        addBtn.setOnAction(e -> {
            BoatType selected = typeGroup.getSelectedToggle() == null
                ? null : (BoatType) typeGroup.getSelectedToggle().getUserData();

            boolean valid = selected != null
                && !nameField.getText().trim().isEmpty()
                && !priceField.getText().trim().isEmpty()
                && !capField.getText().trim().isEmpty();

            if (!valid) {
                addErrorLabel.setText("Please fill in all fields and select a type.");
                return;
            }

            controller.addBoat(nameField.getText(), priceField.getText(), selected, capField.getText());
            nameField.clear();
            priceField.clear();
            capField.clear();
            typeGroup.selectToggle(null);
            addErrorLabel.setText("");
        });

        // --- Fleet Management ---
        Label fleetLabel = new Label("Fleet Management");
        this.boatTable = createBoatTable();
        this.boatTable.setItems(model.getAllBoats());

        Label removeErrorLabel = new Label("");

        Button removeBtn = new Button("Remove Selected Boat");
        removeBtn.setOnAction(e -> {
            Boat selected = boatTable.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            boolean removed = controller.removeBoat(selected);
            removeErrorLabel.setText(removed ? "" : "Cannot remove: boat has an active rental.");
        });

        // --- Rentals ---
        Label rentalLabel = new Label("Rentals");

        ToggleGroup filterGroup = new ToggleGroup();
        RadioButton allBtn = new RadioButton("All");
        RadioButton activeBtn = new RadioButton("Active");
        RadioButton completedBtn = new RadioButton("Completed");
        allBtn.setToggleGroup(filterGroup);
        activeBtn.setToggleGroup(filterGroup);
        completedBtn.setToggleGroup(filterGroup);
        allBtn.setSelected(true);

        HBox filterRow = new HBox(10, new Label("Filter:"), allBtn, activeBtn, completedBtn);
        filterRow.setAlignment(Pos.CENTER_LEFT);

        filterGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            controller.applyRentalFilter(((RadioButton) newVal).getText());
        });

        this.rentalTable = createRentalTable();
        this.rentalTable.setItems(model.getAllRentals());

        // --- Stats ---
        Label statsLabel = new Label("Sales Performance");

        Label revenueLabel = new Label();
        revenueLabel.textProperty().bind(model.totalRevenueProperty());

        Label totalCountLabel = new Label();
        totalCountLabel.textProperty().bind(model.totalRentalsCountProperty());

        Label activeCountLabel = new Label();
        activeCountLabel.textProperty().bind(model.activeRentalsCountProperty());

        HBox statsRow = new HBox(20,
            new HBox(5, new Label("Revenue:"), revenueLabel),
            new HBox(5, new Label("Total Rentals:"), totalCountLabel),
            new HBox(5, new Label("Active:"), activeCountLabel)
        );
        statsRow.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(
            header,
            addTitle, addFieldRow, typeRow, addErrorLabel, addBtn,
            fleetLabel, boatTable, removeErrorLabel, removeBtn,
            rentalLabel, filterRow, rentalTable,
            statsLabel, statsRow
        );
    }

    private TableView<Boat> createBoatTable() {
        TableView<Boat> table = new TableView<>();

        TableColumn<Boat, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());

        TableColumn<Boat, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty().asObject());

        TableColumn<Boat, Integer> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(c -> c.getValue().capacityProperty().asObject());

        table.getColumns().addAll(nameCol, priceCol, capCol);
        return table;
    }

    private TableView<RentRecord> createRentalTable() {
        TableView<RentRecord> table = new TableView<>();

        TableColumn<RentRecord, String> memberCol = new TableColumn<>("Member");
        memberCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMember().getUsername()));

        TableColumn<RentRecord, String> boatCol = new TableColumn<>("Boat");
        boatCol.setCellValueFactory(c -> c.getValue().getBoat().nameProperty());

        TableColumn<RentRecord, Double> priceCol = new TableColumn<>("Paid");
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty().asObject());

        table.getColumns().addAll(memberCol, boatCol, priceCol);
        return table;
    }

    public Button getLogoutButton() {
        return logoutBtn;
    }

    public Parent asParent() {
        return root;
    }
}
