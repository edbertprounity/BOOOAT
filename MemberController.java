import java.util.List;

public class MemberController {
    private BoatManager boatManager;
    private RentalManager rentalManager;
    private Member member;
    private MemberModel model;

    public MemberController(BoatManager boatManager, RentalManager rentalManager, Member member, MemberModel model) {
        this.boatManager = boatManager;
        this.rentalManager = rentalManager;
        this.member = member;
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

    public void updateMinCapacity(Integer value) {
        model.minCapacityProperty().set(value);
        applyFilters();
    }

    public void updateMaxCapacity(Integer value) {
        model.maxCapacityProperty().set(value);
        applyFilters();
    }

    public void updateMinPrice(Double value) {
        model.minPriceProperty().set(value);
        applyFilters();
    }

    public void updateMaxPrice(Double value) {
        model.maxPriceProperty().set(value);
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
        List<Boat> filtered = boatManager.search(
            model.getSearchKeyword(),
            model.getMinCapacity(), model.getMaxCapacity(),
            model.getFilterType(),
            model.getMinPrice(), model.getMaxPrice(),
            null, null
        );
        model.setAvailableBoats(filtered);
    }

    public double getCalculatedPrice(Boat boat, int duration, String discountCode) {
        if (discountCode == null || discountCode.isEmpty()) {
            return rentalManager.calculatePrice(boat, duration, member);
        }
        return rentalManager.calculatePrice(boat, duration, member, discountCode);
    }

    public void executeRental(Boat boat, int duration, String discountCode) {
        if (discountCode == null || discountCode.isEmpty()) {
            rentalManager.rentBoat(member, boat, duration);
        } else {
            rentalManager.rentBoat(member, boat, duration, discountCode);
        }
        member.confirmMembership();
        refreshData();
    }

    public void returnBoat(Boat boat) {
        if (boat == null) return;
        rentalManager.returnBoat(member, boat);
        refreshData();
    }

    public void updateProfileName(String newName) {
        member.setName(newName);
        refreshData();
    }

    public void updatePassword(String newPassword) {
        member.updatePassword(newPassword);
    }

    public void refreshData() {
        model.setMemberData(member);
        model.setAvailableBoats(boatManager.findAvailableBoat());
    }
}
