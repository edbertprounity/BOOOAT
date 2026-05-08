public class Admin extends User {

    public Admin(String adminName, String username, String password) {
        super(adminName, username, password);
    }

    @Override
    public String toString() {
        return "Admin[name=" + getName() + ", username=" + getUsername() + "]";
    }
}