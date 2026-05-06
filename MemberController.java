import java.util.List;

public class MemberController {
    private MemberModel model;

    public MemberController(MemberModel model) {
        this.model = model;
        refreshData();
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

    public void updateRentalDuration(Boat boat, String durationStr) {
        int duration = convertStringToInt(durationStr);
        if (duration <= 0) {
            duration = 1;
        }
        model.rentalDurationProperty().set(duration);
        calculateTotal(boat);
    }

    public void applyDiscountCode(Boat boat, String code) {
        model.appliedDiscountCodeProperty().set(code);
        calculateTotal(boat);
    }

    private void calculateTotal(Boat boat) {
        int duration = model.rentalDurationProperty().get();
        String code = model.appliedDiscountCodeProperty().get();
        double price = model.getRentalManager().calculatePrice(boat, duration, model.getMember(), code);
        model.rentalTotalPriceProperty().set(price);
    }

    public void executeRental(Boat boat) {
        int duration = model.rentalDurationProperty().get();
        String discountCode = model.appliedDiscountCodeProperty().get();

        if (discountCode == null || discountCode.isEmpty()) {
            model.getRentalManager().rentBoat(model.getMember(), boat, duration);
        } else {
            model.getRentalManager().rentBoat(model.getMember(), boat, duration, discountCode);
        }

        model.getMember().confirmMembership();
        refreshData();
        model.appliedDiscountCodeProperty().set("");
        model.rentalDurationProperty().set(1);
    }

    public void returnBoat(Boat boat) {
        if (boat == null) {
            return;
        }
        model.getRentalManager().returnBoat(model.getMember(), boat);
        refreshData();
    }

    public void updateProfileName(String newName) {
        model.getMember().setName(newName);
        refreshData();
    }

    public void updatePassword(String p1, String p2) {
        if (p1 == null || p1.isEmpty() || !p1.equals(p2)) {
            model.passwordErrorProperty().set("Passwords do not match or are empty.");
            return;
        }

        model.getMember().updatePassword(p1);
        model.passwordErrorProperty().set("");
    }

    public void refreshData() {
        model.setMemberData(model.getMember());
        model.setAvailableBoats(model.getBoatManager().findAvailableBoat());
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
