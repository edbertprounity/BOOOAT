import java.util.List;

public class MemberController {
    private MemberModel model;

    public MemberController(MemberModel model) {
        this.model = model;
        applyFilters();
    }

    public void updateSearch(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.searchKeywordProperty().set(keyword);
        } else {
            model.searchKeywordProperty().set(null);
        }
        applyFilters();
    }

    public void updateMinCapacity(String value) {
        int val = convertStringToInt(value);
        if (val == 0) {
            val = -1;
        }
        model.minCapacityProperty().set(val);
        applyFilters();
    }

    public void updateMaxCapacity(String value) {
        int val = convertStringToInt(value);
        if (val == 0) {
            val = -1;
        }
        model.maxCapacityProperty().set(val);
        applyFilters();
    }

    public void updateMinPrice(String value) {
        double val = convertStringToDouble(value);
        if (val == 0.0) {
            val = -1.0;
        }
        model.minPriceProperty().set(val);
        applyFilters();
    }

    public void updateMaxPrice(String value) {
        double val = convertStringToDouble(value);
        if (val == 0.0) {
            val = -1.0;
        }
        model.maxPriceProperty().set(val);
        applyFilters();
    }

    public void updateFilterType(BoatType type) {
        model.filterTypeProperty().set(type);
        applyFilters();
    }

    public void clearFilters() {
        model.clearFilters();
        applyFilters();
    }

    public void applyFilters() {
        List<Boat> filtered = model.getBoatManager().search(
            model.getSearchKeyword(),
            model.getMinCapacity(), model.getMaxCapacity(),
            model.getFilterType(),
            model.getMinPrice(), model.getMaxPrice()
        );
        model.setAvailableBoats(filtered);
    }

    // ─────────────Step 3────────────────────────
    // CONTROLLER receives the call:
    // MemberController.java — updateRentalDuration()
    public void updateRentalDuration(Boat boat, String durationStr) {
        // CONTROLLER: parse and validate — Views never do this
        int duration = convertStringToInt(durationStr);
        if (duration <= 0) {
            duration = 1;
        }
        // CONTROLLER → MODEL: write validated value to Model property
        model.rentalDurationProperty().set(duration);
        // CONTROLLER: trigger price recalculation
        calculateTotal(boat);
    }

    // ───────────────Step 4───────────────────────
    // CONTROLLER receives the call:
    // MemberController.java — applyDiscountCode()
    public void applyDiscountCode(Boat boat, String code) {
        // CONTROLLER => MODEL: store the code string
        model.appliedDiscountCodeProperty().set(code);
        calculateTotal(boat);
    }

    private void calculateTotal(Boat boat) {
        int duration = model.rentalDurationProperty().get();
        String code = model.appliedDiscountCodeProperty().get();
        // CONTROLLER calls into the Domain layer (RentalManager)
        double price = model.getRentalManager().calculatePrice(boat, duration, model.getMember(), code);
        // CONTROLLER => MODEL: store result; bound label auto-updates
        model.rentalTotalPriceProperty().set(price);
    }
    // ────────────────Step 5─────────────────
    // CONTROLLER: MemberController.java — executeRental()
    public void executeRental(Boat boat) {
        
        // CONTROLLER: read confirmed values from Model state
        int duration = model.rentalDurationProperty().get();
        String discountCode = model.appliedDiscountCodeProperty().get();

        // CONTROLLER => DOMAIN: call the correct overload
        if (discountCode == null || discountCode.isEmpty()) {
            model.getRentalManager().rentBoat(model.getMember(), boat, duration);
        } else {
            model.getRentalManager().rentBoat(model.getMember(), boat, duration, discountCode);
        }

        // DOMAIN: Member.addRental() — sets boat availability = false,
        // adds to currentRental ObservableList, adds 3000 points
        // CONTROLLER: recalculate membership tier after rental
        model.getMember().confirmMembership();

        model.setMemberData(model.getMember());
        
        // CONTROLLER: re-run search filter so rented boat
        // disappears from the available boats TableView
        applyFilters();
        
        // CONTROLLER: reset dialog state for next rental
        model.appliedDiscountCodeProperty().set("");
        model.rentalDurationProperty().set(1);
    }

    public void returnBoat(Boat boat) {
        if (boat == null) {
            return;
        }
        model.getRentalManager().returnBoat(model.getMember(), boat);
        model.setMemberData(model.getMember());
        applyFilters();
    }

    public void updateProfileName(String newName) {
        model.getMember().setName(newName);
        model.setMemberData(model.getMember());
    }

    public void updatePassword(String password, String confirmPassword) {
        if (password == null || password.isEmpty() || !password.equals(confirmPassword)) {
            model.passwordErrorProperty().set("Passwords do not match or are empty.");
            return;
        }

        model.getMember().updatePassword(password);
        model.passwordErrorProperty().set("");
    }

    private int convertStringToInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if ("-".equals(s)) {
            return 0;
        }
        return Integer.parseInt(s); // Convert string into integer
    }

    private double convertStringToDouble(String s) {
        if (s == null || s.isEmpty()) {
            return 0.0;
        }
        if ("-".equals(s)) {
            return 0.0;
        }
        return Double.parseDouble(s);
    }
}
