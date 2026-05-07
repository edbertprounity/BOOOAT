import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextFormatter;
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
        // Declarations
        Label panelLabel;
        HBox header;
        Label addTitle;
        Label nameLabel;
        TextField nameField;
        Label priceLabel;
        TextField priceField;
        Label capLabel;
        TextField capField;
        HBox addFieldRow;
        Label typeLabel;
        ToggleGroup typeGroup;
        HBox typeBox;
        HBox typeRow;
        Label addErrorLabel;
        Button addBtn;
        Label fleetLabel;
        Label removeErrorLabel;
        Button removeBtn;
        Label rentalLabel;
        Label filterLabel;
        ToggleGroup filterGroup;
        RadioButton allBtn;
        RadioButton activeBtn;
        RadioButton completedBtn;
        HBox filterRow;
        Label statsLabel;
        Label revenueLabel;
        Label revenueValue;
        Label totalRentalsLabel;
        Label totalValue;
        Label activeRentalsLabel;
        Label activeValue;
        HBox revBox;
        HBox totalBox;
        HBox activeBox;
        HBox statsRow;

        // Initializations
        this.root = new VBox(15);
        this.logoutBtn = new Button("Logout");
        panelLabel = new Label("Admin Control Panel");
        header = new HBox(10, panelLabel, logoutBtn);
        
        addTitle = new Label("Add New Boat");
        nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Boat name");
        priceLabel = new Label("Price:");
        priceField = new TextField();
        priceField.setPromptText("Price per day");
        capLabel = new Label("Capacity:");
        capField = new TextField();
        capField.setPromptText("Capacity");
        configDoubleField(priceField);
        configIntField(capField);
        addFieldRow = new HBox(10, nameLabel, nameField, priceLabel, priceField, capLabel, capField);

        typeLabel = new Label("Type:");
        typeGroup = new ToggleGroup();
        typeBox = new HBox(10);
        for (BoatType bt : BoatType.values()) {
            RadioButton rb = new RadioButton(bt.name());
            rb.setToggleGroup(typeGroup);
            rb.setUserData(bt);
            typeBox.getChildren().add(rb);
        }
        typeRow = new HBox(10, typeLabel, typeBox);

        addErrorLabel = new Label("");
        addBtn = new Button("Add Boat");

        fleetLabel = new Label("Fleet Management");
        this.boatTable = createBoatTable();
        removeErrorLabel = new Label("");
        removeBtn = new Button("Remove Selected Boat");

        rentalLabel = new Label("Rentals");
        filterLabel = new Label("Filter:");
        filterGroup = new ToggleGroup();
        allBtn = new RadioButton("All");
        activeBtn = new RadioButton("Active");
        completedBtn = new RadioButton("Completed");
        allBtn.setToggleGroup(filterGroup);
        activeBtn.setToggleGroup(filterGroup);
        completedBtn.setToggleGroup(filterGroup);
        filterRow = new HBox(10, filterLabel, allBtn, activeBtn, completedBtn);

        statsLabel = new Label("Sales Performance");
        revenueLabel = new Label("Revenue:");
        revenueValue = new Label();
        totalRentalsLabel = new Label("Total Rentals:");
        totalValue = new Label();
        activeRentalsLabel = new Label("Active:");
        activeValue = new Label();
        revBox = new HBox(5, revenueLabel, revenueValue);
        totalBox = new HBox(5, totalRentalsLabel, totalValue);
        activeBox = new HBox(5, activeRentalsLabel, activeValue);
        statsRow = new HBox(20, revBox, totalBox, activeBox);

        // Alignment and Binding
        root.setAlignment(Pos.TOP_LEFT);
        header.setAlignment(Pos.CENTER_LEFT);
        addFieldRow.setAlignment(Pos.CENTER_LEFT);
        typeBox.setAlignment(Pos.CENTER_LEFT);
        typeRow.setAlignment(Pos.CENTER_LEFT);
        filterRow.setAlignment(Pos.CENTER_LEFT);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        this.boatTable.setItems(model.getAllBoats());
        this.rentalTable = createRentalTable();
        this.rentalTable.setItems(model.getAllRentals());

        revenueValue.textProperty().bind(model.totalRevenueProperty());
        totalValue.textProperty().bind(model.totalRentalsCountProperty());
        activeValue.textProperty().bind(model.activeRentalsCountProperty());

        // Event Handlers
        addBtn.setOnAction(e -> {
            BoatType selected = null;
            if (typeGroup.getSelectedToggle() != null) {
                selected = (BoatType) typeGroup.getSelectedToggle().getUserData();
            }

            boolean filled = selected != null
                && !nameField.getText().trim().isEmpty()
                && !priceField.getText().trim().isEmpty()
                && !capField.getText().trim().isEmpty();

            if (!filled) {
                addErrorLabel.setText("Please fill in all fields and select a type.");
                return;
            }

            controller.addBoat(nameField.getText(), priceField.getText(), selected, capField.getText());
            nameField.setText("");
            priceField.setText("");
            capField.setText("");
            typeGroup.selectToggle(null);
            addErrorLabel.setText("");
        });
        
        removeBtn.setOnAction(e -> {
            Boat selected = boatTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                return;
            }
            boolean removed = controller.removeBoat(selected);
            if (removed) {
                removeErrorLabel.setText("");
            } else {
                removeErrorLabel.setText("Cannot remove: boat has an active rental.");
            }
        });
        
        filterGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                return;
            }
            controller.applyRentalFilter(((RadioButton) newVal).getText());
        });

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

        TableColumn<Boat, BoatType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(c -> c.getValue().typeProperty());

        TableColumn<Boat, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty());

        TableColumn<Boat, Number> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(c -> c.getValue().capacityProperty());

        table.getColumns().addAll(nameCol, typeCol, priceCol, capCol);
        return table;
    }

    private TableView<RentRecord> createRentalTable() {
        TableView<RentRecord> table = new TableView<>();

        TableColumn<RentRecord, String> memberCol = new TableColumn<>("Member");
        memberCol.setCellValueFactory(c -> c.getValue().getMember().usernameProperty());

        TableColumn<RentRecord, String> boatCol = new TableColumn<>("Boat");
        boatCol.setCellValueFactory(c -> c.getValue().getBoat().nameProperty());

        TableColumn<RentRecord, Number> priceCol = new TableColumn<>("Paid");
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty());

        table.getColumns().addAll(memberCol, boatCol, priceCol);
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